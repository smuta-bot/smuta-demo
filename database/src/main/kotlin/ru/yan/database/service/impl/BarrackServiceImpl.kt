package ru.yan.database.service.impl

import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.yan.database.exceptions.alreadyExistsWith
import ru.yan.database.exceptions.notFound
import ru.yan.database.exceptions.notFoundBy
import ru.yan.database.model.barrackUUID
import ru.yan.database.model.nsi.NsiCombatUnit
import ru.yan.database.model.nsi.dto.toDto
import ru.yan.database.model.nsi.types.CombatUnitStatus
import ru.yan.database.model.nsi.types.StorageResourceOperationType
import ru.yan.database.model.operation.CombatUnitOperation
import ru.yan.database.model.operation.StorageResourceOperation
import ru.yan.database.model.operation.dto.DtoCombatUnitOperation
import ru.yan.database.model.operation.dto.toDto
import ru.yan.database.model.smuta.CombatUnit
import ru.yan.database.model.smuta.Country
import ru.yan.database.model.smuta.User
import ru.yan.database.model.smuta.dto.DtoUser
import ru.yan.database.model.smuta.dto.toDto
import ru.yan.database.model.task.CombatUnitTask
import ru.yan.database.model.task.Task
import ru.yan.database.model.task.dto.DtoCombatUnitTask
import ru.yan.database.model.task.dto.toDto
import ru.yan.database.model.task.types.TaskType
import ru.yan.database.repository.nsi.NsiCombatUnitRepository
import ru.yan.database.repository.operation.CombatUnitOperationRepository
import ru.yan.database.repository.operation.StorageResourceOperationRepository
import ru.yan.database.repository.smuta.CombatUnitRepository
import ru.yan.database.repository.smuta.CountryBuildingRepository
import ru.yan.database.repository.smuta.CountryRepository
import ru.yan.database.repository.task.CombatUnitTaskRepository
import ru.yan.database.repository.task.TaskRepository
import ru.yan.database.service.BarrackService
import java.util.*
import kotlin.math.abs

@Service
class BarrackServiceImpl(
    private val countryRepository: CountryRepository,
    private val countryBuildingRepository: CountryBuildingRepository,
    private val nsiCombatUnitRepository: NsiCombatUnitRepository,
    private val combatUnitOperationRepository: CombatUnitOperationRepository,
    private val combatUnitRepository: CombatUnitRepository,
    private val storageResourceOperationRepository: StorageResourceOperationRepository,
    private val combatUnitTaskRepository: CombatUnitTaskRepository,
    private val taskRepository: TaskRepository
): BarrackService {

    @Transactional
    override fun getCountryBarrack(user: DtoUser)=
        countryBuildingRepository.findByUserIdAndNsiBuildingId(user.id, UUID.fromString(barrackUUID))?.toDto()

    @Transactional
    override fun getCombatUnits(user: DtoUser, pageRequest: PageRequest) =
        combatUnitRepository.findByUserId(user.id, pageRequest).map { it.toDto() }

    @Transactional
    override fun getCombatUnit(user: DtoUser, combatUnitId: UUID) =
        combatUnitRepository.findByUserIdAndId(user.id, combatUnitId)?.toDto()

    @Transactional
    override fun getNsiCombatUnits(pageRequest: PageRequest) =
        nsiCombatUnitRepository.findAll(pageRequest).map { it.toDto() }

    @Transactional
    override fun getNsiCombatUnit(nsiCombatUnitId: UUID) =
        nsiCombatUnitRepository.findByIdOrNull(nsiCombatUnitId)?.toDto()

    @Transactional
    override fun createCombatUnit(user: DtoUser, nsiCombatUnitId: UUID, numberPeople: Long): DtoCombatUnitTask {
        countryRepository.findByUserId(user.id)?.let { country ->
            val combatUnit = combatUnitRepository.findByCountryAndNsiCombatUnitId(country, nsiCombatUnitId)
            if (combatUnit != null) {
                alreadyExistsWith(CombatUnit::class, NsiCombatUnit::class, nsiCombatUnitId)
            }

            val operation = combatUnitOperationRepository.save(
                CombatUnitOperation(
                    CombatUnitStatus.Formation,
                    numberPeople,
                    0.5,
                    country,
                    nsiCombatUnitRepository.getReferenceById(nsiCombatUnitId)
                )
            )

            return combatUnitTaskRepository.save(
                CombatUnitTask(
                    null,
                    operation,
                    taskRepository.save(
                        Task(TaskType.CombatUnit, 600, country)
                    )
                )
            ).toDto()
        } ?: notFoundBy(Country::class, User::class, user.id)
    }

    @Transactional
    override fun exerciseCombatUnit(combatUnitId: UUID): DtoCombatUnitTask {
        combatUnitRepository.findByIdOrNull(combatUnitId)?.let { unit ->
            if (unit.status == CombatUnitStatus.Exercise) {
                alreadyExistsWith(CombatUnit::class, CombatUnit::status, CombatUnitStatus.Exercise)
            }

            val operation = combatUnitOperationRepository.save(
                CombatUnitOperation(
                    CombatUnitStatus.Exercise,
                    unit.quantity,
                    with(unit.experience + 0.02) {
                        if (this <= 0.5) {
                            this
                        } else {
                            0.5
                        }
                    },
                    unit.country,
                    unit.nsiCombatUnit
                )
            )

            val combatUnitTask = combatUnitTaskRepository.save(
                CombatUnitTask(
                    unit.combatUnitOperation,
                    operation,
                    taskRepository.save(
                        Task(TaskType.CombatUnit, 600, unit.country)
                    )
                )
            )

            storageResourceOperationRepository.saveAll(
                unit.nsiCombatUnit.combatUnitResources.map {
                    StorageResourceOperation(
                        StorageResourceOperationType.ExerciseTake,
                        -it.quantity.toDouble(),
                        unit.country,
                        it.resource
                    ).apply {
                        this.combatUnitTask = combatUnitTask
                    }
                }
            )

            return combatUnitTask.toDto()
        } ?: notFound(CombatUnit::class, combatUnitId)
    }

    @Transactional
    override fun abortCombatUnit(combatUnitId: UUID): DtoCombatUnitOperation {
        combatUnitRepository.findByIdOrNull(combatUnitId)?.let { unit ->
            if ((unit.status != CombatUnitStatus.Exercise) &&
                (unit.status != CombatUnitStatus.Formation) &&
                (unit.status != CombatUnitStatus.Disbandment)) {
                alreadyExistsWith(CombatUnit::class, CombatUnit::status, unit.status)
            }

            combatUnitTaskRepository.findByToBuildingOperation(unit.combatUnitOperation)?.let { combatUnitTask ->

                val combatUnitOperation = combatUnitTask.fromCombatUnitOperation?.let { fromCombatUnitOperation ->
                    combatUnitOperationRepository.save(
                        CombatUnitOperation(
                            fromCombatUnitOperation.status,
                            fromCombatUnitOperation.quantity,
                            fromCombatUnitOperation.experience,
                            unit.country,
                            unit.nsiCombatUnit
                        )
                    )
                } ?: run {
                    combatUnitOperationRepository.save(
                        CombatUnitOperation(
                            CombatUnitStatus.Disbanded,
                            0,
                            0.0,
                            unit.country,
                            unit.nsiCombatUnit
                        )
                    )
                }

                storageResourceOperationRepository.saveAll(
                    combatUnitTask.storageResourceOperation.map {
                        StorageResourceOperation(
                            StorageResourceOperationType.ExerciseReturn,
                            abs(it.quantity),
                            unit.country,
                            it.resource
                        ).apply {
                            this.combatUnitTask = combatUnitTask
                        }
                    }
                )

                taskRepository.save(combatUnitTask.task.apply { deletedAt = Date() })

                return combatUnitOperation.toDto()
            } ?: notFoundBy(CombatUnitTask::class, CombatUnitOperation::class, unit.combatUnitOperation.id)

        } ?: notFound(CombatUnit::class, combatUnitId)
    }
}