package ru.yan.database.service.impl

import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.yan.database.exceptions.*
import ru.yan.database.exceptions.notFound
import ru.yan.database.model.moneyUUID
import ru.yan.database.model.nsi.NsiBuilding
import ru.yan.database.model.nsi.dto.toDto
import ru.yan.database.model.nsi.types.BuildingStatus
import ru.yan.database.model.nsi.types.StorageResourceOperationType
import ru.yan.database.model.operation.BuildingOperation
import ru.yan.database.model.operation.StorageResourceOperation
import ru.yan.database.model.operation.dto.DtoBuildingOperation
import ru.yan.database.model.operation.dto.toDto
import ru.yan.database.model.smuta.*
import ru.yan.database.model.task.ConstructionTask
import ru.yan.database.model.task.Task
import ru.yan.database.model.smuta.dto.*
import ru.yan.database.model.task.types.TaskType
import ru.yan.database.repository.nsi.NsiBuildingRepository
import ru.yan.database.repository.operation.BuildingOperationRepository
import ru.yan.database.repository.operation.StorageResourceOperationRepository
import ru.yan.database.repository.smuta.CountryBuildingRepository
import ru.yan.database.repository.smuta.CountryRepository
import ru.yan.database.repository.task.ConstructionTaskRepository
import ru.yan.database.repository.task.TaskRepository
import ru.yan.database.service.BuildingService
import java.util.*
import kotlin.math.abs

@Service
class BuildingServiceImpl(
    private val countryRepository: CountryRepository,
    private val countryBuildingRepository: CountryBuildingRepository,
    private val taskRepository: TaskRepository,
    private val constructionTaskRepository: ConstructionTaskRepository,
    private val nsiBuildingRepository: NsiBuildingRepository,
    private val storageResourceOperationRepository: StorageResourceOperationRepository,
    private val buildingOperationRepository: BuildingOperationRepository
): BuildingService {

    override fun getNsiBuildingsForBuild(user: DtoUser, pageRequest: PageRequest) =
        nsiBuildingRepository.findForBuild(user.id, pageRequest).map { it.toDto() }

    override fun getNsiBuilding(nsiBuildingId: UUID) =
        nsiBuildingRepository.findByIdOrNull(nsiBuildingId)?.toDto()

    override fun getCountryBuildings(user: DtoUser, pageRequest: PageRequest) =
        countryBuildingRepository.findByUserId(user.id, pageRequest).map { it.toDto() }

    override fun getCountryBuilding(user: DtoUser, nsiBuildingId: UUID) =
        countryBuildingRepository.findByUserIdAndNsiBuildingId(user.id, nsiBuildingId)?.toDto()

    @Transactional
    override fun buildBuilding(user: DtoUser, nsiBuildingId: UUID, level: Int): DtoBuildingOperation {
        countryRepository.findByUserId(user.id)?.let { country ->
            val countryBuilding = countryBuildingRepository.findByCountryAndNsiBuildingId(country, nsiBuildingId)

            if (countryBuilding != null) {
                alreadyExistsWith(CountryBuilding::class, NsiBuilding::class, nsiBuildingId)
            }

            val nsiBuilding = nsiBuildingRepository.findByIdOrNull(nsiBuildingId) ?: notFound(NsiBuilding::class, nsiBuildingId)

            val requiredArea = nsiBuilding.areaByLevel * level
            val freeArea = country.freeTerritory()
            if (freeArea < requiredArea) {
                notEnoughArea(requiredArea - freeArea)
            }

            val buildingOperation = buildingOperationRepository.save(
                BuildingOperation(
                    BuildingStatus.ConstructionProcess,
                    level,
                    1.0,
                    country,
                    nsiBuilding
                )
            )

            val task = constructionTaskRepository.save(
                ConstructionTask(
                    null,
                    buildingOperation,
                    taskRepository.save(
                        Task(TaskType.Construction, nsiBuilding.constructionTimeByLevel * level, country)
                    )
                )
            )

            val srOperationsForSave = mutableListOf<StorageResourceOperation>()

            val buildPrice = nsiBuilding.priceForLevel * level
            country.storageResources[UUID.fromString(moneyUUID)]?.let { budget ->
                if (budget.quantity < buildPrice) {
                    notEnoughMoney(buildPrice - budget.quantity)
                } else {
                    srOperationsForSave.add(
                        StorageResourceOperation(
                            StorageResourceOperationType.ConstructionTake,
                            -buildPrice,
                            country,
                            budget.resource
                        ).apply {
                            constructionTask = task
                        }
                    )
                }
            } ?: notEnoughMoney(buildPrice)

            nsiBuilding.buildingResources.forEach { buildingResource ->
                val cr = country.storageResources[buildingResource.resource.id]
                if (cr == null || cr.quantity < (buildingResource.quantityPerLevel * level)) {
                    notEnoughResource(
                        buildingResource.resource.toDto(),
                        abs((cr?.quantity ?: 0.0) - (buildingResource.quantityPerLevel * level))
                    )
                } else {
                    srOperationsForSave.add(
                        StorageResourceOperation(
                            StorageResourceOperationType.ConstructionTake,
                            -(buildingResource.quantityPerLevel * level),
                            country,
                            buildingResource.resource
                        ).apply {
                            constructionTask = task
                        }
                    )
                }
            }


            storageResourceOperationRepository.saveAll(srOperationsForSave)

            return buildingOperation.toDto()
        } ?: notFoundBy(Country::class, User::class, user.id)
    }

    @Transactional
    override fun changeBuildingLevel(user: DtoUser, nsiBuildingId: UUID, level: Int): DtoBuildingOperation {
        countryRepository.findByUserId(user.id)?.let { country ->
            val countryBuilding = countryBuildingRepository.findByCountryAndNsiBuildingId(country, nsiBuildingId)
                ?: notFoundBy(CountryBuilding::class, NsiBuilding::class, nsiBuildingId)

            if (countryBuilding.status == BuildingStatus.RenovationProcess) {
                alreadyExistsWith(CountryBuilding::class, CountryBuilding::status, BuildingStatus.RenovationProcess)
            } else {
                val numberLevels = level - countryBuilding.level

                val buildingOperation = buildingOperationRepository.save(
                    BuildingOperation(
                        BuildingStatus.RenovationProcess,
                        level,
                        countryBuilding.efficiency,
                        country,
                        countryBuilding.nsiBuilding
                    )
                )

                // Значит идет улучшение здания
                if (numberLevels > 0) {
                    val requiredArea = countryBuilding.nsiBuilding.areaByLevel * abs(numberLevels)
                    val freeArea = country.freeTerritory()
                    if (freeArea < requiredArea) {
                        notEnoughArea(requiredArea - freeArea)
                    }

                    val task = constructionTaskRepository.save(
                        ConstructionTask(
                            countryBuilding.buildingOperation,
                            buildingOperation,
                            taskRepository.save(
                                Task(TaskType.Construction, countryBuilding.nsiBuilding.constructionTimeByLevel *  abs(numberLevels), country)
                            )
                        )
                    )

                    val srOperationsForSave = mutableListOf<StorageResourceOperation>()

                    val price = countryBuilding.nsiBuilding.priceForLevel * abs(numberLevels)
                    country.storageResources[UUID.fromString(moneyUUID)]?.let { budget ->
                        if (budget.quantity < price) {
                            notEnoughMoney(price - budget.quantity)
                        } else {
                            srOperationsForSave.add(
                                StorageResourceOperation(
                                    StorageResourceOperationType.ConstructionTake,
                                    -price,
                                    country,
                                    budget.resource
                                ).apply {
                                    constructionTask = task
                                }
                            )
                        }
                    } ?: notEnoughMoney(price)

                    countryBuilding.nsiBuilding.buildingResources.forEach { buildingResource ->
                        val cr = country.storageResources[buildingResource.resource.id]
                        if (cr == null || cr.quantity < (buildingResource.quantityPerLevel * abs(numberLevels))) {
                            notEnoughResource(
                                buildingResource.resource.toDto(),
                                abs((cr?.quantity ?: 0.0) - (buildingResource.quantityPerLevel * abs(numberLevels)))
                            )
                        } else {
                            srOperationsForSave.add(
                                StorageResourceOperation(
                                    StorageResourceOperationType.ConstructionTake,
                                    -(buildingResource.quantityPerLevel * level),
                                    country,
                                    buildingResource.resource
                                ).apply {
                                    constructionTask = task
                                }
                            )
                        }
                    }

                    storageResourceOperationRepository.saveAll(srOperationsForSave)
                } else {
                    val task = constructionTaskRepository.save(
                        ConstructionTask(
                            countryBuilding.buildingOperation,
                            buildingOperation,
                            taskRepository.save(
                                Task(TaskType.Construction, countryBuilding.nsiBuilding.destroyTime, country)
                            )
                        )
                    )

                    val price = countryBuilding.nsiBuilding.destroyPriceForLevel * abs(numberLevels)
                    country.storageResources[UUID.fromString(moneyUUID)]?.let { budget ->
                        if (budget.quantity < price) {
                            notEnoughMoney(price - budget.quantity)
                        } else {
                            storageResourceOperationRepository.save(
                                StorageResourceOperation(
                                    StorageResourceOperationType.ConstructionTake,
                                    -price,
                                    country,
                                    budget.resource
                                ).apply {
                                    constructionTask = task
                                }
                            )
                        }
                    } ?: notEnoughMoney(price)
                }

                return buildingOperation.toDto()
            }
        } ?: notFoundBy(Country::class, User::class, user.id)
    }

    @Transactional
    override fun conservationBuilding(user: DtoUser, nsiBuildingId: UUID): DtoBuildingOperation {
        countryRepository.findByUserId(user.id)?.let { country ->
            val countryBuilding = countryBuildingRepository.findByCountryAndNsiBuildingId(country, nsiBuildingId)
                ?: notFoundBy(CountryBuilding::class, NsiBuilding::class, nsiBuildingId)

            if (countryBuilding.status == BuildingStatus.ConservationProcess) {
                alreadyExistsWith(CountryBuilding::class, CountryBuilding::status, BuildingStatus.ConservationProcess)
            } else {
                val buildingOperation = buildingOperationRepository.save(
                    BuildingOperation(
                        BuildingStatus.ConservationProcess,
                        countryBuilding.level,
                        countryBuilding.efficiency,
                        country,
                        countryBuilding.nsiBuilding
                    )
                )

                val task = constructionTaskRepository.save(
                    ConstructionTask(
                        countryBuilding.buildingOperation,
                        buildingOperation,
                        taskRepository.save(
                            Task(TaskType.Construction, countryBuilding.nsiBuilding.mothballedTime * countryBuilding.level, country)
                        )
                    )
                )

                val conservationPrice = countryBuilding.nsiBuilding.mothballedPriceByLevel * countryBuilding.level
                country.storageResources[UUID.fromString(moneyUUID)]?.let { budget ->
                    if (budget.quantity < conservationPrice) {
                        notEnoughMoney(conservationPrice - budget.quantity)
                    } else {
                        storageResourceOperationRepository.save(
                            StorageResourceOperation(
                                StorageResourceOperationType.ConstructionTake,
                                -conservationPrice,
                                country,
                                budget.resource
                            ).apply {
                                constructionTask = task
                            }
                        )
                    }
                } ?: notEnoughMoney(conservationPrice)

                return buildingOperation.toDto()
            }
        } ?: notFoundBy(Country::class, User::class, user.id)
    }

    @Transactional
    override fun reactivateBuilding(user: DtoUser, nsiBuildingId: UUID): DtoBuildingOperation {
        countryRepository.findByUserId(user.id)?.let { country ->
            val countryBuilding = countryBuildingRepository.findByCountryAndNsiBuildingId(country, nsiBuildingId)
                ?: notFoundBy(CountryBuilding::class, NsiBuilding::class, nsiBuildingId)

            if (countryBuilding.status == BuildingStatus.ReactivationProcess) {
                alreadyExistsWith(CountryBuilding::class, CountryBuilding::status, BuildingStatus.ReactivationProcess)
            } else {
                val buildingOperation = buildingOperationRepository.save(
                    BuildingOperation(
                        BuildingStatus.ReactivationProcess,
                        countryBuilding.level,
                        countryBuilding.efficiency,
                        country,
                        countryBuilding.nsiBuilding
                    )
                )

                val task = constructionTaskRepository.save(
                    ConstructionTask(
                        countryBuilding.buildingOperation,
                        buildingOperation,
                        taskRepository.save(
                            Task(TaskType.Construction, countryBuilding.nsiBuilding.mothballedTime * countryBuilding.level, country)
                        )
                    )
                )

                val reactivationPrice = countryBuilding.nsiBuilding.mothballedPriceByLevel * countryBuilding.level
                country.storageResources[UUID.fromString(moneyUUID)]?.let { budget ->
                    if (budget.quantity < reactivationPrice) {
                        notEnoughMoney(reactivationPrice - budget.quantity)
                    } else {
                        storageResourceOperationRepository.save(
                            StorageResourceOperation(
                                StorageResourceOperationType.ConstructionTake,
                                -reactivationPrice,
                                country,
                                budget.resource
                            ).apply {
                                constructionTask = task
                            }
                        )
                    }
                } ?: notEnoughMoney(reactivationPrice)

                return buildingOperation.toDto()
            }
        } ?: notFoundBy(Country::class, User::class, user.id)
    }

    @Transactional
    override fun destroyBuilding(user: DtoUser, nsiBuildingId: UUID): DtoBuildingOperation {
        countryRepository.findByUserId(user.id)?.let { country ->
            val countryBuilding = countryBuildingRepository.findByCountryAndNsiBuildingId(country, nsiBuildingId)
                ?: notFoundBy(CountryBuilding::class, NsiBuilding::class, nsiBuildingId)

            if (countryBuilding.status == BuildingStatus.DestroyProcess) {
                alreadyExistsWith(CountryBuilding::class, CountryBuilding::status, BuildingStatus.DestroyProcess)
            } else {
                val buildingOperation = buildingOperationRepository.save(
                    BuildingOperation(
                        BuildingStatus.DestroyProcess,
                        countryBuilding.level,
                        countryBuilding.efficiency,
                        country,
                        countryBuilding.nsiBuilding
                    )
                )

                val task = constructionTaskRepository.save(
                    ConstructionTask(
                        countryBuilding.buildingOperation,
                        buildingOperation,
                        taskRepository.save(
                            Task(TaskType.Construction, countryBuilding.nsiBuilding.destroyTime, country)
                        )
                    )
                )

                val destroyPrice = countryBuilding.nsiBuilding.destroyPriceForLevel * countryBuilding.level
                country.storageResources[UUID.fromString(moneyUUID)]?.let { budget ->
                    if (budget.quantity < destroyPrice) {
                        notEnoughMoney(destroyPrice - budget.quantity)
                    } else {
                        storageResourceOperationRepository.save(
                            StorageResourceOperation(
                                StorageResourceOperationType.ConstructionTake,
                                -destroyPrice,
                                country,
                                budget.resource
                            ).apply {
                                constructionTask = task
                            }
                        )
                    }
                } ?: notEnoughMoney(destroyPrice)

                return buildingOperation.toDto()
            }
        } ?: notFoundBy(Country::class, User::class, user.id)
    }

    @Transactional
    override fun stopConstruction(user: DtoUser, nsiBuildingId: UUID): DtoBuildingOperation {
        countryRepository.findByUserId(user.id)?.let { country ->
            val countryBuilding = countryBuildingRepository.findByCountryAndNsiBuildingId(country, nsiBuildingId)
                ?: notFoundBy(CountryBuilding::class, NsiBuilding::class, nsiBuildingId)

            if (countryBuilding.status != BuildingStatus.Ready && countryBuilding.status != BuildingStatus.ProductionProcess) {

                constructionTaskRepository.findByToBuildingOperation(countryBuilding.buildingOperation)?.let { constTask ->

                    val buildingOperation = constTask.fromBuildingOperation?.let { fromBuildingOperation ->
                        buildingOperationRepository.save(
                            BuildingOperation(
                                fromBuildingOperation.status,
                                fromBuildingOperation.level,
                                fromBuildingOperation.efficiency,
                                country,
                                fromBuildingOperation.nsiBuilding
                            )
                        )
                    } ?: run {
                        buildingOperationRepository.save(
                            BuildingOperation(
                                BuildingStatus.Destroyed,
                                null,
                                null,
                                country,
                                countryBuilding.nsiBuilding
                            )
                        )
                    }

                    storageResourceOperationRepository.saveAll(
                        constTask.storageResourceOperation.map {
                            StorageResourceOperation(
                                StorageResourceOperationType.ConstructionReturn,
                                abs(it.quantity),
                                country,
                                it.resource
                            ).apply {
                                constructionTask = constTask
                            }
                        }
                    )

                    taskRepository.save(constTask.task.apply { deletedAt = Date() })

                   return buildingOperation.toDto()
                } ?: notFoundBy(ConstructionTask::class, BuildingOperation::class, countryBuilding.buildingOperation.id)
            } else {
                alreadyExistsWith(CountryBuilding::class, CountryBuilding::status, countryBuilding.status)
            }
        } ?: notFoundBy(Country::class, User::class, user.id)
    }
}