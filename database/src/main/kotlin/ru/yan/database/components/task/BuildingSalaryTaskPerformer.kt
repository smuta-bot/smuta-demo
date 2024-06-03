package ru.yan.database.components.task

import org.springframework.stereotype.Component
import ru.yan.database.model.moneyUUID
import ru.yan.database.model.nsi.types.HappinessOperationType
import ru.yan.database.model.nsi.types.StorageResourceOperationType
import ru.yan.database.model.operation.HappinessOperation
import ru.yan.database.model.operation.StorageResourceOperation
import ru.yan.database.model.populationUUID
import ru.yan.database.model.smuta.Country
import ru.yan.database.model.smuta.Notification
import ru.yan.database.model.smuta.workplacesRate
import ru.yan.database.model.task.BuildingSalaryTask
import ru.yan.database.model.task.Task
import ru.yan.database.model.task.types.TaskType
import ru.yan.database.repository.nsi.NotificationCategoryRepository
import ru.yan.database.repository.operation.HappinessOperationRepository
import ru.yan.database.repository.operation.StorageResourceOperationRepository
import ru.yan.database.repository.smuta.CountryBuildingRepository
import ru.yan.database.repository.smuta.NotificationRepository
import ru.yan.database.repository.smuta.StorageResourceRepository
import ru.yan.database.repository.task.BuildingSalaryTaskRepository
import ru.yan.database.repository.task.TaskRepository
import java.util.*

/**
 * Исполнитель задачи, который симулирует выплату зарплат сотрудникам зданий
 */
@Component
class BuildingSalaryTaskPerformer(
    private val countryBuildingRepository: CountryBuildingRepository,
    private val storageResourceRepository: StorageResourceRepository,
    private val storageResourceOperationRepository: StorageResourceOperationRepository,
    private val notificationRepository: NotificationRepository,
    private val notificationCategoryRepository: NotificationCategoryRepository,
    private val happinessOperationRepository: HappinessOperationRepository,
    private val taskRepository: TaskRepository,
    private val buildingSalaryTaskRepository: BuildingSalaryTaskRepository
): TaskPerformer<BuildingSalaryTask> {

    override fun execute(task: BuildingSalaryTask, country: Country) {
        // Реализация закрыта
    }
}