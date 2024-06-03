package ru.yan.database.service.impl

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.yan.database.exceptions.*
import ru.yan.database.model.nsi.types.*
import ru.yan.database.model.operation.*
import ru.yan.database.model.operation.dto.DtoCountryFightingUnit
import ru.yan.database.model.operation.dto.toDto
import ru.yan.database.model.smuta.CombatUnit
import ru.yan.database.model.smuta.Country
import ru.yan.database.model.smuta.User
import ru.yan.database.model.smuta.dto.DtoUser
import ru.yan.database.model.smuta.workplacesRate
import ru.yan.database.model.task.BattleTask
import ru.yan.database.model.task.Task
import ru.yan.database.model.task.dto.DtoBattle
import ru.yan.database.model.task.dto.DtoBattleTask
import ru.yan.database.model.task.dto.toDto
import ru.yan.database.model.task.types.TaskType
import ru.yan.database.repository.operation.*
import ru.yan.database.repository.smuta.CombatUnitRepository
import ru.yan.database.repository.smuta.CountryRepository
import ru.yan.database.repository.smuta.PopulationRepository
import ru.yan.database.repository.smuta.StorageResourceRepository
import ru.yan.database.repository.task.BattleTaskRepository
import ru.yan.database.repository.task.TaskRepository
import ru.yan.database.service.BattleService
import java.util.*
import kotlin.math.abs

@Service
class BattleServiceImpl(
    private val countryRepository: CountryRepository,
    private val taskRepository: TaskRepository,
    private val battleTaskRepository: BattleTaskRepository,
    private val populationRepository: PopulationRepository,
    private val combatUnitRepository: CombatUnitRepository,
    private val battleOperationRepository: BattleOperationRepository,
    private val populationOperationRepository: PopulationOperationRepository,
    private val storageResourceRepository: StorageResourceRepository,
    private val storageResourceOperationRepository: StorageResourceOperationRepository,
    private val countryFightingUnitRepository: CountryFightingUnitRepository,
    private val combatUnitOperationRepository: CombatUnitOperationRepository,
    private val happinessOperationRepository: HappinessOperationRepository
): BattleService {

    @Transactional
    override fun getCurrentBattle(user: DtoUser): DtoBattle? {
        countryRepository.findByUserId(user.id)?.let { country ->
            return battleTaskRepository.findByBattleMember(country.id)?.let { battleTask ->
                DtoBattle(
                    battleTask.id,
                    battleTask.battleOperation?.defendingCountry != country.id,
                    battleTask.stage
                )
            }
        } ?: notFoundBy(Country::class, User::class, user.id)
    }

    @Transactional
    override fun getCountryFightingUnit(user: DtoUser): DtoCountryFightingUnit? {
        countryRepository.findByUserId(user.id)?.let { country ->
            return battleOperationRepository.findActualByMemberId(country.id)?.let { operation ->
                val purpose = if (operation.defendingCountry != country.id) {
                    CombatUnitPurpose.Attack
                } else {
                    CombatUnitPurpose.Defend
                }

                countryFightingUnitRepository.findByOperationAndPurpose(operation, purpose)?.toDto()
            }
        } ?: notFoundBy(Country::class, User::class, user.id)
    }

    @Transactional
    override fun startBattle(user: DtoUser): DtoBattleTask {
        countryRepository.findByUserId(user.id)?.let { country ->
            val units = combatUnitRepository.findByCountry(country)
            if (units.isEmpty()) {
                permissionDenied()
            }

            battleTaskRepository.findByBattleMember(country.id)?.let {
                alreadyExists(BattleTask::class, it.id)
            } ?: run {
                return battleTaskRepository.save(
                    BattleTask(
                        BattleStage.Search,
                        null,
                        taskRepository.save(Task(TaskType.Battle, 600, country))
                    )
                ).toDto()
            }
        } ?: notFoundBy(Country::class, User::class, user.id)
    }

    @Transactional
    override fun stopBattle(user: DtoUser) {
        countryRepository.findByUserId(user.id)?.let { country ->
            battleTaskRepository.findActualByCountry(country)?.let { battleTask ->
                if (battleTask.stage == BattleStage.Search) {
                    taskRepository.save(
                        battleTask.task.apply {
                            deletedAt = Date()
                        }
                    )
                } else {
                    permissionDenied()
                }
            } ?: run {
                alreadyExistsWith(Task::class, Task::deletedAt, Date())
            }
        } ?: notFoundBy(Country::class, User::class, user.id)
    }

    @Transactional
    override fun sendCombatUnit(user: DtoUser, combatUnitId: UUID, combatUnitSize: Long?): DtoCountryFightingUnit {
        countryRepository.findByUserId(user.id)?.let { country ->
            battleOperationRepository.findActualByMemberId(country.id)?.let { operation ->
                val battleTask = battleTaskRepository.findByBattleOperation(operation)
                if (battleTask.stage != BattleStage.Preparation) {
                    permissionDenied()
                }

                val purpose = if (operation.defendingCountry != country.id) {
                    CombatUnitPurpose.Attack
                } else {
                    CombatUnitPurpose.Defend
                }

                val combatUnit = combatUnitRepository.findByCountryAndId(country, combatUnitId)
                    ?: notFound(CombatUnit::class, combatUnitId)

                if (combatUnit.quantity < (combatUnitSize ?: 1)) {
                    notEnoughPeople((combatUnitSize ?: 1) - combatUnit.quantity)
                }

                if (combatUnit.status != CombatUnitStatus.Ready) {
                    permissionDenied()
                }

                val combatUnitQuantity = if (country.mobilization) {
                    val populations = populationRepository.findByCountry(country)
                    val q = populations.find {
                        it.ageGroup == AgeGroup.MiddleAge &&
                                it.healthStatus == HealthStatus.Healthy &&
                                it.gender == Gender.Male
                    }?.quantity ?: 0

                    minOf(q, combatUnitSize ?: combatUnit.quantity)
                } else {
                    ((combatUnitSize ?: combatUnit.quantity) * country.workplacesRate()).toLong()
                }

                val storageResources = storageResourceRepository.findByCountryAndAllResourceIdIn(
                    country,
                    combatUnit.nsiCombatUnit.combatUnitResources.map { it.id }
                ).associate { it?.id to it!!.quantity }

                val resourceCoverages = combatUnit.nsiCombatUnit.combatUnitResources.map { combatResource ->
                    (storageResources[combatResource.id] ?: 0.0) / (combatUnitQuantity * combatResource.quantity)
                }

                storageResourceOperationRepository.saveAll(
                    combatUnit.nsiCombatUnit.combatUnitResources.map { combatResource ->
                        StorageResourceOperation(
                            StorageResourceOperationType.WarTake,
                            storageResources[combatResource.id]?.let {
                                if (it > combatUnitQuantity * combatResource.quantity) {
                                    (combatUnitQuantity * combatResource.quantity).toDouble()
                                } else {
                                    it
                                }
                            } ?: 0.0,
                            country,
                            combatResource.resource
                        ).apply {
                            this.battleTask = battleTask
                        }
                    }
                )

                val populationOperation = populationOperationRepository.save(
                    PopulationOperation(
                        PopulationOperationType.WarTaken,
                        null,
                        combatUnitQuantity,
                        country,
                        Gender.Male,
                        AgeGroup.MiddleAge,
                        HealthStatus.Healthy
                    )
                )

                val combatUnitOperation = combatUnitOperationRepository.save(
                    CombatUnitOperation(
                        CombatUnitStatus.Battle,
                        combatUnit.quantity,
                        combatUnit.experience,
                        combatUnit.country,
                        combatUnit.nsiCombatUnit
                    )
                )

                return countryFightingUnitRepository.save(
                    CountryFightingUnit(
                        purpose,
                        combatUnit.experience,
                        resourceCoverages.min(),
                        operation,
                        populationOperation,
                        combatUnitOperation,
                        combatUnit.nsiCombatUnit
                    )
                ).toDto()
            }
        } ?: notFoundBy(Country::class, User::class, user.id)
    }

    @Transactional
    override fun revokeCombatUnit(user: DtoUser) {
        countryRepository.findByUserId(user.id)?.let { country ->
            battleOperationRepository.findActualByMemberId(country.id)?.let { operation ->
                val battleTask = battleTaskRepository.findByBattleOperation(operation)
                if (battleTask.stage != BattleStage.Preparation) {
                    permissionDenied()
                }

                val purpose = if (operation.defendingCountry != country.id) {
                    CombatUnitPurpose.Attack
                } else {
                    CombatUnitPurpose.Defend
                }

                storageResourceOperationRepository.saveAll(
                    battleTask.storageResourceOperation.map {
                        StorageResourceOperation(
                            StorageResourceOperationType.WarReturn,
                            it.quantity,
                            it.country,
                            it.resource
                        )
                    }
                )

                countryFightingUnitRepository.findByOperationAndPurpose(operation, purpose)?.let { unit ->
                    combatUnitOperationRepository.save(
                        CombatUnitOperation(
                            CombatUnitStatus.Ready,
                            unit.combatUnitOperation.quantity,
                            unit.combatUnitOperation.experience,
                            unit.combatUnitOperation.country,
                            unit.combatUnitOperation.nsiCombatUnit
                        )
                    )

                    countryFightingUnitRepository.delete(unit)
                }
            }
        } ?: notFoundBy(Country::class, User::class, user.id)
    }

    @Transactional
    override fun stopAttack(user: DtoUser) {
        countryRepository.findByUserId(user.id)?.let { country ->
            val battleTask = battleTaskRepository.findActualByCountry(country)

            if (battleTask != null) {
                if (battleTask.stage == BattleStage.Battle) {
                    permissionDenied()
                }

                storageResourceOperationRepository.saveAll(
                    battleTask.storageResourceOperation.map {
                        StorageResourceOperation(
                            StorageResourceOperationType.WarReturn,
                            it.quantity,
                            it.country,
                            it.resource
                        )
                    }
                )

                battleTask.battleOperation!!.let { operation ->

                    combatUnitOperationRepository.saveAll(
                        countryFightingUnitRepository.findByBattleOperation(operation).map {
                            CombatUnitOperation(
                                CombatUnitStatus.Ready,
                                it.combatUnitOperation.quantity,
                                it.combatUnitOperation.experience,
                                it.combatUnitOperation.country,
                                it.combatUnitOperation.nsiCombatUnit
                            )
                        }
                    )

                    populationOperationRepository.saveAll(
                        operation.fightingUnits.map {
                            PopulationOperation(
                                PopulationOperationType.WarReturn,
                                null,
                                abs(it.populationOperation.quantity),
                                it.populationOperation.country,
                                it.populationOperation.gender,
                                it.populationOperation.ageGroup,
                                it.populationOperation.healthStatus
                            )
                        }
                    )

                    happinessOperationRepository.save(
                        HappinessOperation(
                            HappinessOperationType.WarCompleted,
                            10.0,
                            country
                        )
                    )
                }

                taskRepository.save(
                    battleTask.task.apply {
                        deletedAt = Date()
                    }
                )
            } else {
                alreadyExistsWith(Task::class, Task::deletedAt, Date())
            }
        } ?: notFoundBy(Country::class, User::class, user.id)
    }

    @Transactional
    override fun surrender(user: DtoUser) {
        countryRepository.findByUserId(user.id)?.let { country ->
            val battleTask = battleTaskRepository.findByBattleMember(country.id)
            if (battleTask != null) {
                if (battleTask.stage == BattleStage.Battle) {
                    permissionDenied()
                }

                storageResourceOperationRepository.saveAll(
                    battleTask.storageResourceOperation.map {
                        StorageResourceOperation(
                            StorageResourceOperationType.WarReturn,
                            it.quantity,
                            it.country,
                            it.resource
                        )
                    }
                )

                battleTask.battleOperation!!.let { operation ->
                    val resources = storageResourceRepository.findByCountry(country)

                    storageResourceOperationRepository.saveAll(
                        resources.map {
                            listOf(
                                StorageResourceOperation(
                                    StorageResourceOperationType.Contribution,
                                    -(it.quantity / 2),
                                    it.country,
                                    it.resource
                                ).apply {
                                    this.battleTask = battleTask
                                },
                                StorageResourceOperation(
                                    StorageResourceOperationType.WarProfit,
                                    it.quantity / 2,
                                    operation.attackingCountry,
                                    it.resource
                                ).apply {
                                    this.battleTask = battleTask
                                }
                            )
                        }.flatten()
                    )

                    combatUnitOperationRepository.saveAll(
                        countryFightingUnitRepository.findByBattleOperation(operation).map {
                            CombatUnitOperation(
                                CombatUnitStatus.Ready,
                                it.combatUnitOperation.quantity,
                                it.combatUnitOperation.experience,
                                it.combatUnitOperation.country,
                                it.combatUnitOperation.nsiCombatUnit
                            )
                        }
                    )

                    populationOperationRepository.saveAll(
                        operation.fightingUnits.map {
                            PopulationOperation(
                                PopulationOperationType.WarReturn,
                                null,
                                abs(it.populationOperation.quantity),
                                it.populationOperation.country,
                                it.populationOperation.gender,
                                it.populationOperation.ageGroup,
                                it.populationOperation.healthStatus
                            )
                        }
                    )

                    happinessOperationRepository.saveAll(
                        listOf(
                            HappinessOperation(
                                HappinessOperationType.WarLose,
                                10.0,
                                country
                            ),
                            HappinessOperation(
                                HappinessOperationType.WarWin,
                                10.0,
                                operation.attackingCountry
                            )
                        )
                    )
                }

                taskRepository.save(
                    battleTask.task.apply {
                        deletedAt = Date()
                    }
                )
            } else {
                alreadyExistsWith(Task::class, Task::deletedAt, Date())
            }
        } ?: notFoundBy(Country::class, User::class, user.id)
    }
}