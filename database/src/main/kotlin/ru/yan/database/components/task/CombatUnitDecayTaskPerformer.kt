package ru.yan.database.components.task

import org.springframework.stereotype.Component
import ru.yan.database.model.operation.CombatUnitOperation
import ru.yan.database.model.smuta.Country
import ru.yan.database.model.task.CombatUnitDecayTask
import ru.yan.database.model.task.Task
import ru.yan.database.model.task.types.TaskType
import ru.yan.database.repository.operation.CombatUnitOperationRepository
import ru.yan.database.repository.smuta.CombatUnitRepository
import ru.yan.database.repository.task.CombatUnitDecayTaskRepository
import ru.yan.database.repository.task.TaskRepository

/**
 * Исполнитель задачи, который симулирует гниение(потерю опыта) подразделений
 */
@Component
class CombatUnitDecayTaskPerformer(
    private val combatUnitRepository: CombatUnitRepository,
    private val combatUnitOperationRepository: CombatUnitOperationRepository,
    private val combatUnitDecayTaskRepository: CombatUnitDecayTaskRepository,
    private val taskRepository: TaskRepository
): TaskPerformer<CombatUnitDecayTask> {

    override fun execute(task: CombatUnitDecayTask, country: Country) {
        // Реализация закрыта
    }
}