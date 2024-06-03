package ru.yan.database.components.task

import org.springframework.stereotype.Component
import ru.yan.database.model.moneyUUID
import ru.yan.database.model.nsi.types.*
import ru.yan.database.model.operation.HappinessOperation
import ru.yan.database.model.operation.StorageResourceOperation
import ru.yan.database.model.populationUUID
import ru.yan.database.model.smuta.Country
import ru.yan.database.model.smuta.Notification
import ru.yan.database.model.task.Task
import ru.yan.database.model.task.types.TaskType
import ru.yan.database.repository.nsi.NotificationCategoryRepository
import ru.yan.database.repository.operation.HappinessOperationRepository
import ru.yan.database.repository.operation.StorageResourceOperationRepository
import ru.yan.database.repository.smuta.NotificationRepository
import ru.yan.database.repository.smuta.PopulationRepository
import ru.yan.database.repository.task.TaskRepository
import java.util.*

/**
 * Исполнитель задачи на выплату пенсий
 */
@Component
class PensionTaskPerformer(
    private val populationRepository: PopulationRepository,
    private val storageResourceOperationRepository: StorageResourceOperationRepository,
    private val happinessOperationRepository: HappinessOperationRepository,
    private val notificationRepository: NotificationRepository,
    private val notificationCategoryRepository: NotificationCategoryRepository,
    private val taskRepository: TaskRepository
): TaskPerformer<Nothing?> {

    override fun execute(task: Nothing?, country: Country) {
        // Реализация закрыта
    }
}