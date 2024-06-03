package ru.yan.database.components.task

import org.springframework.stereotype.Component
import ru.yan.database.model.nsi.types.CombatUnitStatus
import ru.yan.database.model.operation.CombatUnitOperation
import ru.yan.database.model.smuta.Country
import ru.yan.database.model.task.CombatUnitDecayTask
import ru.yan.database.model.task.CombatUnitSalaryTask
import ru.yan.database.model.task.CombatUnitTask
import ru.yan.database.model.task.Task
import ru.yan.database.model.task.types.TaskType
import ru.yan.database.repository.operation.CombatUnitOperationRepository
import ru.yan.database.repository.task.CombatUnitDecayTaskRepository
import ru.yan.database.repository.task.CombatUnitSalaryTaskRepository
import ru.yan.database.repository.task.TaskRepository
import java.util.*

/**
 * Исполнитель, который строит/модифицирует здания
 */
@Component
class CombatUnitTaskPerformer(
    private val combatUnitOperationRepository: CombatUnitOperationRepository,
    private val combatUnitDecayTaskRepository: CombatUnitDecayTaskRepository,
    private val combatUnitSalaryTaskRepository: CombatUnitSalaryTaskRepository,
    private val taskRepository: TaskRepository
): TaskPerformer<CombatUnitTask> {

    override fun execute(task: CombatUnitTask, country: Country) {
        // Реализация закрыта
    }
}