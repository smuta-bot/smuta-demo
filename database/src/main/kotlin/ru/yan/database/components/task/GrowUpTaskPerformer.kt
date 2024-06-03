package ru.yan.database.components.task

import org.springframework.stereotype.Component
import ru.yan.database.model.nsi.types.AgeGroup
import ru.yan.database.model.nsi.types.HealthStatus
import ru.yan.database.model.nsi.types.PopulationOperationType
import ru.yan.database.model.operation.PopulationOperation
import ru.yan.database.model.smuta.Country
import ru.yan.database.model.task.Task
import ru.yan.database.model.task.types.TaskType
import ru.yan.database.repository.operation.PopulationOperationRepository
import ru.yan.database.repository.task.TaskRepository
import kotlin.random.Random

/**
 * Исполнитель задачи, который симулирует старение населения
 */
@Component
class GrowUpTaskPerformer(
    private val populationOperationRepository: PopulationOperationRepository,
    private val taskRepository: TaskRepository
): TaskPerformer<Nothing?> {

    override fun execute(task: Nothing?, country: Country) {
        // Реализация закрыта
    }
}