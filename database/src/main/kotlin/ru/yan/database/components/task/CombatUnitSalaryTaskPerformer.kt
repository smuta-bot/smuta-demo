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
import ru.yan.database.model.task.CombatUnitSalaryTask
import ru.yan.database.model.task.Task
import ru.yan.database.model.task.types.TaskType
import ru.yan.database.repository.nsi.NotificationCategoryRepository
import ru.yan.database.repository.operation.HappinessOperationRepository
import ru.yan.database.repository.operation.StorageResourceOperationRepository
import ru.yan.database.repository.smuta.CombatUnitRepository
import ru.yan.database.repository.smuta.NotificationRepository
import ru.yan.database.repository.smuta.StorageResourceRepository
import ru.yan.database.repository.task.CombatUnitSalaryTaskRepository
import ru.yan.database.repository.task.TaskRepository
import java.util.*

/**
 * Исполнитель задачи, который симулирует выплату зарплат сотрудникам зданий
 */
@Component
class CombatUnitSalaryTaskPerformer(
    private val combatUnitRepository: CombatUnitRepository,
    private val storageResourceRepository: StorageResourceRepository,
    private val storageResourceOperationRepository: StorageResourceOperationRepository,
    private val notificationRepository: NotificationRepository,
    private val notificationCategoryRepository: NotificationCategoryRepository,
    private val happinessOperationRepository: HappinessOperationRepository,
    private val combatUnitSalaryTaskRepository: CombatUnitSalaryTaskRepository,
    private val taskRepository: TaskRepository
): TaskPerformer<CombatUnitSalaryTask> {

    override fun execute(task: CombatUnitSalaryTask, country: Country) {
        // Реализация закрыта
    }
}