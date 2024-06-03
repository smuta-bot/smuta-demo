package ru.yan.database.components.task

import org.springframework.stereotype.Component
import ru.yan.database.model.constructionUUID
import ru.yan.database.model.nsi.types.StorageResourceOperationType
import ru.yan.database.model.operation.BuildingOperation
import ru.yan.database.model.operation.StorageResourceOperation
import ru.yan.database.model.smuta.Country
import ru.yan.database.model.smuta.Notification
import ru.yan.database.model.task.BuildingDecayTask
import ru.yan.database.model.task.Task
import ru.yan.database.model.task.types.TaskType
import ru.yan.database.repository.nsi.NotificationCategoryRepository
import ru.yan.database.repository.operation.BuildingOperationRepository
import ru.yan.database.repository.operation.StorageResourceOperationRepository
import ru.yan.database.repository.smuta.CountryBuildingRepository
import ru.yan.database.repository.smuta.NotificationRepository
import ru.yan.database.repository.task.BuildingDecayTaskRepository
import ru.yan.database.repository.task.TaskRepository
import java.util.*

/**
 * Исполнитель задачи, который симулирует гниение зданий
 */
@Component
class BuildingDecayTaskPerformer(
    private val countryBuildingRepository: CountryBuildingRepository,
    private val buildingDecayTaskRepository: BuildingDecayTaskRepository,
    private val notificationRepository: NotificationRepository,
    private val notificationCategoryRepository: NotificationCategoryRepository,
    private val buildingOperationRepository: BuildingOperationRepository,
    private val storageResourceOperationRepository: StorageResourceOperationRepository,
    private val taskRepository: TaskRepository
): TaskPerformer<BuildingDecayTask> {

    override fun execute(task: BuildingDecayTask, country: Country) {
        // Реализация закрыта
    }
}