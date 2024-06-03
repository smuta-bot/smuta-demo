package ru.yan.database.service.impl

import org.springframework.stereotype.Service
import ru.yan.database.components.TreatmentCalculator
import ru.yan.database.exceptions.alreadyExistsWith
import ru.yan.database.exceptions.notFoundBy
import ru.yan.database.model.hospitalUUID
import ru.yan.database.model.nsi.NsiBuilding
import ru.yan.database.model.nsi.types.BuildingStatus
import ru.yan.database.model.nsi.types.StorageResourceOperationType
import ru.yan.database.model.operation.BuildingOperation
import ru.yan.database.model.operation.StorageResourceOperation
import ru.yan.database.model.smuta.Country
import ru.yan.database.model.smuta.CountryBuilding
import ru.yan.database.model.smuta.User
import ru.yan.database.model.smuta.dto.DtoUser
import ru.yan.database.model.smuta.dto.toDto
import ru.yan.database.repository.operation.BuildingOperationRepository
import ru.yan.database.repository.operation.StorageResourceOperationRepository
import ru.yan.database.repository.smuta.CountryBuildingRepository
import ru.yan.database.repository.smuta.CountryRepository
import ru.yan.database.repository.task.TaskRepository
import ru.yan.database.repository.task.TreatmentTaskRepository
import ru.yan.database.service.HospitalService
import java.util.*
import kotlin.math.abs

@Service
class HospitalServiceImpl(
    private val countryRepository: CountryRepository,
    private val countryBuildingRepository: CountryBuildingRepository,
    private val buildingOperationRepository: BuildingOperationRepository,
    private val treatmentTaskRepository: TreatmentTaskRepository,
    private val taskRepository: TaskRepository,
    private val storageResourceOperationRepository: StorageResourceOperationRepository,
    private val treatmentCalculator: TreatmentCalculator
): HospitalService {

    override fun getCountryHospital(user: DtoUser) =
        countryBuildingRepository.findByUserIdAndNsiBuildingId(user.id, UUID.fromString(hospitalUUID))?.toDto()

    override fun startTreatment(user: DtoUser) {
        countryRepository.findByUserId(user.id)?.let { country ->
            val hospital = countryBuildingRepository.findByCountryAndNsiBuildingId(country, UUID.fromString(hospitalUUID))
                ?: notFoundBy(CountryBuilding::class, NsiBuilding::class, hospitalUUID)

            if (hospital.status == BuildingStatus.ProductionProcess) {
                alreadyExistsWith(CountryBuilding::class, CountryBuilding::status, BuildingStatus.ProductionProcess)
            } else {

                treatmentCalculator.calculate(country, hospital)

                buildingOperationRepository.save(
                    BuildingOperation(
                        BuildingStatus.ProductionProcess,
                        hospital.level,
                        hospital.efficiency,
                        country,
                        hospital.nsiBuilding
                    )
                )
            }
        } ?: notFoundBy(Country::class, User::class, user.id)
    }

    override fun stopTreatment(user: DtoUser) {
        countryRepository.findByUserId(user.id)?.let { country ->
            val building = countryBuildingRepository.findByCountryAndNsiBuildingId(country, UUID.fromString(hospitalUUID))
                ?: notFoundBy(CountryBuilding::class, NsiBuilding::class, hospitalUUID)

            treatmentTaskRepository.findByCountry(country)?.let { treatmentTask ->

                treatmentTask.storageResourceOperation?.let {
                    storageResourceOperationRepository.save(
                        StorageResourceOperation(
                            StorageResourceOperationType.ProductionReturn,
                            abs(it.quantity),
                            country,
                            it.resource
                        )
                    )
                }

                buildingOperationRepository.save(
                    BuildingOperation(
                        BuildingStatus.Ready,
                        building.level,
                        building.efficiency,
                        country,
                        building.nsiBuilding
                    )
                )

                taskRepository.save(
                    treatmentTask.task.apply { deletedAt = Date() }
                )
            } ?: alreadyExistsWith(CountryBuilding::class, CountryBuilding::status, building.status)

        } ?: notFoundBy(Country::class, User::class, user.id)
    }
}