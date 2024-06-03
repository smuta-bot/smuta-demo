package ru.yan.database.service.impl

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.yan.database.model.*
import ru.yan.database.model.nsi.types.*
import ru.yan.database.model.operation.*
import ru.yan.database.model.smuta.Country
import ru.yan.database.model.smuta.CountryResourceEffect
import ru.yan.database.model.smuta.dto.DtoCountry
import ru.yan.database.model.smuta.dto.DtoUser
import ru.yan.database.model.smuta.dto.toDto
import ru.yan.database.model.task.Task
import ru.yan.database.model.task.types.TaskType
import ru.yan.database.repository.nsi.NsiBuildingRepository
import ru.yan.database.repository.nsi.NsiResourceEffectRepository
import ru.yan.database.repository.nsi.ResourceRepository
import ru.yan.database.repository.operation.*
import ru.yan.database.repository.smuta.CountryRepository
import ru.yan.database.repository.smuta.CountryResourceEffectRepository
import ru.yan.database.repository.smuta.UserRepository
import ru.yan.database.repository.task.*
import ru.yan.database.service.CountryService
import java.util.*
import kotlin.random.Random

@Service
class CountryServiceImpl(
    private val userRepository: UserRepository,
    private val populationOperationRepository: PopulationOperationRepository,
    private val countryStateOperationRepository: CountryStateOperationRepository,
    private val storageResourceOperationRepository: StorageResourceOperationRepository,
    private val happinessOperationRepository: HappinessOperationRepository,
    private val nsiResourceEffectRepository: NsiResourceEffectRepository,
    private val countryResourceEffectRepository: CountryResourceEffectRepository,
    private val buildingOperationRepository: BuildingOperationRepository,
    private val nsiBuildingRepository: NsiBuildingRepository,
    private val countryRepository: CountryRepository,
    private val taskRepository: TaskRepository,
    private val resourceRepository: ResourceRepository
): CountryService {

    @Transactional
    override fun create(user: DtoUser, name: String): DtoCountry {
        val dbUser = userRepository.findById(user.id).get()

        val country = countryRepository.save(
            Country(
                name,
                0.0,
                0.0,
                0.0,
                false,
                dbUser
            )
        )

        countryStateOperationRepository.saveAll(
            listOf(
                CountryStateOperation(
                    CountryStateOperationType.AreaType,
                    1000.0,
                    country
                ),
                CountryStateOperation(
                    CountryStateOperationType.MorbidityType,
                    Random.nextDouble(0.15, 0.30),
                    country
                ),
                CountryStateOperation(
                    CountryStateOperationType.MortalityType,
                    Random.nextDouble(0.20, 0.40),
                    country
                )
            )
        )

        populationOperationRepository.saveAll(
            listOf(
                PopulationOperation(
                    PopulationOperationType.Initialize,
                    null,
                    0L,
                    country,
                    Gender.Male,
                    AgeGroup.Child,
                    HealthStatus.Healthy
                ),
                PopulationOperation(
                    PopulationOperationType.Initialize,
                    null,
                    5L,
                    country,
                    Gender.Male,
                    AgeGroup.MiddleAge,
                    HealthStatus.Healthy
                ),
                PopulationOperation(
                    PopulationOperationType.Initialize,
                    null,
                    0L,
                    country,
                    Gender.Male,
                    AgeGroup.Old,
                    HealthStatus.Healthy
                ),
                PopulationOperation(
                    PopulationOperationType.Initialize,
                    null,
                    0L,
                    country,
                    Gender.Female,
                    AgeGroup.Child,
                    HealthStatus.Healthy
                ),
                PopulationOperation(
                    PopulationOperationType.Initialize,
                    null,
                    5L,
                    country,
                    Gender.Female,
                    AgeGroup.MiddleAge,
                    HealthStatus.Healthy
                ),
                PopulationOperation(
                    PopulationOperationType.Initialize,
                    null,
                    0L,
                    country,
                    Gender.Female,
                    AgeGroup.Old,
                    HealthStatus.Healthy
                )
            )
        )

        storageResourceOperationRepository.saveAll(
            listOf(
                StorageResourceOperation(
                    StorageResourceOperationType.Initialize,
                    1000.0,
                    country,
                    resourceRepository.getReferenceById(UUID.fromString(boardsUUID))
                ),
                StorageResourceOperation(
                    StorageResourceOperationType.Initialize,
                    1000.0,
                    country,
                    resourceRepository.getReferenceById(UUID.fromString(stoneUUID))
                ),
                StorageResourceOperation(
                    StorageResourceOperationType.Initialize,
                    1000.0,
                    country,
                    resourceRepository.getReferenceById(UUID.fromString(glassUUID))
                ),
                StorageResourceOperation(
                    StorageResourceOperationType.Initialize,
                    1000.0,
                    country,
                    resourceRepository.getReferenceById(UUID.fromString(moneyUUID))
                ),
                StorageResourceOperation(
                    StorageResourceOperationType.Initialize,
                    1000.0,
                    country,
                    resourceRepository.getReferenceById(UUID.fromString(medicamentUUID))
                ),
                StorageResourceOperation(
                    StorageResourceOperationType.Initialize,
                    1000.0,
                    country,
                    resourceRepository.getReferenceById(UUID.fromString(waterUUID))
                ),
                StorageResourceOperation(
                    StorageResourceOperationType.Initialize,
                    1000.0,
                    country,
                    resourceRepository.getReferenceById(UUID.fromString(beefUUID))
                ),
                StorageResourceOperation(
                    StorageResourceOperationType.Initialize,
                    1000.0,
                    country,
                    resourceRepository.getReferenceById(UUID.fromString(cucumberUUID))
                )
            )
        )

        buildingOperationRepository.save(
            BuildingOperation(
                BuildingStatus.Ready,
                2,
                1.0,
                country,
                nsiBuildingRepository.getReferenceById(UUID.fromString(houseUUID))
            )
        )

        happinessOperationRepository.saveAll(
            listOf(
                HappinessOperation(
                    HappinessOperationType.CountryCreated,
                    50.0,
                    country
                )
            )
        )

        taskRepository.saveAll(
            listOf(
                Task(
                    TaskType.Thirst,
                    60,
                    country
                ),
                Task(
                    TaskType.Pension,
                    300,
                    country
                ),
                Task(
                    TaskType.Infection,
                    300,
                    country
                ),
                Task(
                    TaskType.GrowUp,
                    60,
                    country
                ),
                Task(
                    TaskType.Food,
                    60,
                    country
                ),
                Task(
                    TaskType.Death,
                    300,
                    country
                ),
                Task(
                    TaskType.Born,
                    60,
                    country
                ),
                Task(
                    TaskType.Profit,
                    600,
                    country
                )
            )
        )

        val effects = nsiResourceEffectRepository.findAll()
        countryResourceEffectRepository.saveAll(
            List(Random.nextInt(effects.size)) { 1 }.map {
                CountryResourceEffect(
                    Random.nextDouble(0.01, 0.30),
                    country,
                    effects.removeAt(Random.nextInt(effects.size))
                )
            }
        )

        return country.toDto()
    }

    @Transactional
    override fun getByUser(user: DtoUser) =
        countryRepository.findByUserId(user.id)?.toDto()
}