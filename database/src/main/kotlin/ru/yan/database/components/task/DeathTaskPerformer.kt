package ru.yan.database.components.task

import org.springframework.stereotype.Component
import ru.yan.database.model.nsi.types.HappinessOperationType
import ru.yan.database.model.nsi.types.HealthStatus
import ru.yan.database.model.nsi.types.PopulationOperationType
import ru.yan.database.model.operation.HappinessOperation
import ru.yan.database.model.operation.PopulationOperation
import ru.yan.database.model.populationUUID
import ru.yan.database.model.smuta.Country
import ru.yan.database.model.smuta.Notification
import ru.yan.database.model.task.Task
import ru.yan.database.model.task.types.TaskType
import ru.yan.database.repository.nsi.NotificationCategoryRepository
import ru.yan.database.repository.operation.HappinessOperationRepository
import ru.yan.database.repository.operation.PopulationOperationRepository
import ru.yan.database.repository.smuta.NotificationRepository
import ru.yan.database.repository.smuta.PopulationRepository
import ru.yan.database.repository.task.TaskRepository
import java.util.*
import kotlin.math.min
import kotlin.random.Random

/**
 * Исполнитель задачи, который симулирует смертность в стране
 */
@Component
class DeathTaskPerformer(
    private val populationRepository: PopulationRepository,
    private val happinessOperationRepository: HappinessOperationRepository,
    private val populationOperationRepository: PopulationOperationRepository,
    private val notificationRepository: NotificationRepository,
    private val notificationCategoryRepository: NotificationCategoryRepository,
    private val taskRepository: TaskRepository
): TaskPerformer<Nothing?> {

    override fun execute(task: Nothing?, country: Country) {
        // Реализация закрыта
    }
}