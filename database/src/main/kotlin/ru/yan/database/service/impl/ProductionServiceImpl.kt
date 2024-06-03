package ru.yan.database.service.impl

import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.yan.database.components.ProductionCalculator
import ru.yan.database.exceptions.alreadyExistsWith
import ru.yan.database.exceptions.notFound
import ru.yan.database.exceptions.notFoundBy
import ru.yan.database.model.nsi.BuildingProduct
import ru.yan.database.model.nsi.NsiBuilding
import ru.yan.database.model.nsi.dto.toDto
import ru.yan.database.model.nsi.types.BuildingStatus
import ru.yan.database.model.nsi.types.ProductOperationType
import ru.yan.database.model.nsi.types.StorageResourceOperationType
import ru.yan.database.model.operation.BuildingOperation
import ru.yan.database.model.operation.ProductOperation
import ru.yan.database.model.operation.StorageResourceOperation
import ru.yan.database.model.operation.dto.DtoProductOperation
import ru.yan.database.model.operation.dto.toDto
import ru.yan.database.model.smuta.Country
import ru.yan.database.model.smuta.CountryBuilding
import ru.yan.database.model.smuta.User
import ru.yan.database.model.smuta.dto.DtoUser
import ru.yan.database.model.smuta.dto.toDto
import ru.yan.database.repository.nsi.BuildingProductRepository
import ru.yan.database.repository.nsi.NsiBuildingRepository
import ru.yan.database.repository.operation.BuildingOperationRepository
import ru.yan.database.repository.operation.ProductOperationRepository
import ru.yan.database.repository.operation.StorageResourceOperationRepository
import ru.yan.database.repository.smuta.CountryBuildingRepository
import ru.yan.database.repository.smuta.CountryRepository
import ru.yan.database.repository.smuta.ProductRepository
import ru.yan.database.repository.task.ProductionTaskRepository
import ru.yan.database.repository.task.TaskRepository
import ru.yan.database.service.ProductionService
import java.util.*
import kotlin.math.abs

@Service
class ProductionServiceImpl(
    private val countryRepository: CountryRepository,
    private val taskRepository: TaskRepository,
    private val productRepository: ProductRepository,
    private val productionTaskRepository: ProductionTaskRepository,
    private val productOperationRepository: ProductOperationRepository,
    private val buildingProductRepository: BuildingProductRepository,
    private val nsiBuildingRepository: NsiBuildingRepository,
    private val buildingOperationRepository: BuildingOperationRepository,
    private val countryBuildingRepository: CountryBuildingRepository,
    private val productionCalculator: ProductionCalculator,
    private val storageResourceOperationRepository: StorageResourceOperationRepository
): ProductionService {

    @Transactional
    override fun getProducts(user: DtoUser, nsiBuildingId: UUID, pageRequest: PageRequest) =
        productRepository.findByUserAndNsiBuilding(user.id, nsiBuildingId, pageRequest).map { it.toDto() }

    @Transactional
    override fun getBuildingProducts(buildingId: UUID, pageRequest: PageRequest) =
        buildingProductRepository.findBuildingProductByNsiBuilding(buildingId, pageRequest).map { it.toDto() }

    @Transactional
    override fun getBuildingProduct(buildingProductId: UUID) =
        buildingProductRepository.findByIdOrNull(buildingProductId)?.toDto()
            ?: notFound(BuildingProduct::class, buildingProductId)

    @Transactional
    override fun addProductToProduction(user: DtoUser, nsiBuildingId: UUID, buildingProductId: UUID): DtoProductOperation {
        countryRepository.findByUserId(user.id)?.let { country ->

            val buildingProduct = buildingProductRepository.findByIdOrNull(buildingProductId)
                ?: notFound(BuildingProduct:: class, buildingProductId)

            val nsiBuilding = nsiBuildingRepository.findByIdOrNull(nsiBuildingId)
                ?: notFound(NsiBuilding:: class, nsiBuildingId)

            return productOperationRepository.save(
                ProductOperation(ProductOperationType.Add, buildingProduct, country, nsiBuilding)
            ).toDto()

        } ?: notFoundBy(Country::class, User::class, user.id)
    }

    @Transactional
    override fun removeProductFromProduction(user: DtoUser, nsiBuildingId: UUID, buildingProductId: UUID): DtoProductOperation {
        countryRepository.findByUserId(user.id)?.let { country ->
            val buildingProduct = buildingProductRepository.findByIdOrNull(buildingProductId)
                ?: notFound(BuildingProduct:: class, buildingProductId)

            val nsiBuilding = nsiBuildingRepository.findByIdOrNull(nsiBuildingId)
                ?: notFound(NsiBuilding:: class, nsiBuildingId)

            return productOperationRepository.save(
                ProductOperation(ProductOperationType.Remove, buildingProduct, country, nsiBuilding)
            ).toDto()

        } ?: notFoundBy(Country::class, User::class, user.id)
    }

    @Transactional
    override fun startProduction(user: DtoUser, nsiBuildingId: UUID) {
        countryRepository.findByUserId(user.id)?.let { country ->
            val building = countryBuildingRepository.findByCountryAndNsiBuildingId(country, nsiBuildingId)
                ?: notFoundBy(CountryBuilding::class, NsiBuilding::class, nsiBuildingId)

            if (building.status == BuildingStatus.ProductionProcess) {
                alreadyExistsWith(CountryBuilding::class, CountryBuilding::status, BuildingStatus.ProductionProcess)
            } else {
                productionCalculator.calculate(country, building)

                buildingOperationRepository.save(
                    BuildingOperation(
                        BuildingStatus.ProductionProcess,
                        building.level,
                        building.efficiency,
                        country,
                        building.nsiBuilding
                    )
                )
            }
        } ?: notFoundBy(Country::class, User::class, user.id)
    }

    @Transactional
    override fun stopProduction(user: DtoUser, nsiBuildingId: UUID) {
        countryRepository.findByUserId(user.id)?.let { country ->
            val building = countryBuildingRepository.findByCountryAndNsiBuildingId(country, nsiBuildingId)
                ?: notFoundBy(CountryBuilding::class, NsiBuilding::class, nsiBuildingId)

            productionTaskRepository.findByCountryAndBuilding(country, building.nsiBuilding)?.let { productionTask ->

                storageResourceOperationRepository.saveAll(
                    productionTask.storageResourceOperation.map {
                        StorageResourceOperation(
                            StorageResourceOperationType.ProductionReturn,
                            abs(it.quantity),
                            country,
                            it.resource
                        )
                    }
                )

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
                    productionTask.task.apply { deletedAt = Date() }
                )
            } ?: alreadyExistsWith(CountryBuilding::class, CountryBuilding::status, building.status)

        } ?: notFoundBy(Country::class, User::class, user.id)
    }
}