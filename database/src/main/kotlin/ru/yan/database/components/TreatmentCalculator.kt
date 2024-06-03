package ru.yan.database.components

import org.springframework.stereotype.Component
import ru.yan.database.model.medicamentUUID
import ru.yan.database.model.nsi.types.HealthStatus
import ru.yan.database.model.nsi.types.PopulationOperationType
import ru.yan.database.model.nsi.types.StorageResourceOperationType
import ru.yan.database.model.operation.PopulationOperation
import ru.yan.database.model.operation.StorageResourceOperation
import ru.yan.database.model.populationUUID
import ru.yan.database.model.smuta.Country
import ru.yan.database.model.smuta.CountryBuilding
import ru.yan.database.model.smuta.Notification
import ru.yan.database.model.smuta.workplacesRate
import ru.yan.database.model.task.Task
import ru.yan.database.model.task.TreatmentTask
import ru.yan.database.model.task.dto.DtoTreatmentTask
import ru.yan.database.model.task.dto.toDto
import ru.yan.database.model.task.types.TaskType
import ru.yan.database.repository.nsi.NotificationCategoryRepository
import ru.yan.database.repository.operation.PopulationOperationRepository
import ru.yan.database.repository.operation.StorageResourceOperationRepository
import ru.yan.database.repository.smuta.NotificationRepository
import ru.yan.database.repository.smuta.StorageResourceRepository
import ru.yan.database.repository.task.TaskRepository
import ru.yan.database.repository.task.TreatmentTaskRepository
import java.util.*
import kotlin.math.floor
import kotlin.math.min
import kotlin.random.Random

/**
 * Калькулятор, рассчитывающий сколько людей отправится на лечение
 */
@Component
class TreatmentCalculator(
    private val populationOperationRepository: PopulationOperationRepository,
    private val storageResourceRepository: StorageResourceRepository,
    private val treatmentTaskRepository: TreatmentTaskRepository,
    private val notificationRepository: NotificationRepository,
    private val notificationCategoryRepository: NotificationCategoryRepository,
    private val storageResourceOperationRepository: StorageResourceOperationRepository,
    private val taskRepository: TaskRepository
) {
    fun calculate(country: Country, hospital: CountryBuilding): DtoTreatmentTask {
        // Создаем задачу на лечение
        val task = Task(TaskType.Treatment, hospital.nsiBuilding.productionTime, country)

        return TreatmentTask(null, listOf(), task).toDto()
    }
}