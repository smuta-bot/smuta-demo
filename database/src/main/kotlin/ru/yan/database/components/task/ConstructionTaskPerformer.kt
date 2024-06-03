package ru.yan.database.components.task

import org.springframework.stereotype.Component
import ru.yan.database.model.constructionUUID
import ru.yan.database.model.nsi.NsiBuilding
import ru.yan.database.model.nsi.types.*
import ru.yan.database.model.operation.BuildingOperation
import ru.yan.database.model.operation.HappinessOperation
import ru.yan.database.model.operation.ProductOperation
import ru.yan.database.model.operation.StorageResourceOperation
import ru.yan.database.model.smuta.Country
import ru.yan.database.model.smuta.Notification
import ru.yan.database.model.task.ConstructionTask
import ru.yan.database.model.task.BuildingDecayTask
import ru.yan.database.model.task.BuildingSalaryTask
import ru.yan.database.model.task.Task
import ru.yan.database.model.task.types.TaskType
import ru.yan.database.repository.nsi.NotificationCategoryRepository
import ru.yan.database.repository.operation.BuildingOperationRepository
import ru.yan.database.repository.operation.HappinessOperationRepository
import ru.yan.database.repository.operation.ProductOperationRepository
import ru.yan.database.repository.operation.StorageResourceOperationRepository
import ru.yan.database.repository.smuta.NotificationRepository
import ru.yan.database.repository.smuta.ProductRepository
import ru.yan.database.repository.task.BuildingDecayTaskRepository
import ru.yan.database.repository.task.ProductionTaskRepository
import ru.yan.database.repository.task.BuildingSalaryTaskRepository
import ru.yan.database.repository.task.TaskRepository
import java.util.*
import kotlin.math.abs

/**
 * Исполнитель, который строит/модифицирует здания
 */
@Component
class ConstructionTaskPerformer(
    private val taskRepository: TaskRepository,
    private val productRepository: ProductRepository,
    private val notificationRepository: NotificationRepository,
    private val notificationCategoryRepository: NotificationCategoryRepository,
    private val productOperationRepository: ProductOperationRepository,
    private val productionTaskRepository: ProductionTaskRepository,
    private val buildingOperationRepository: BuildingOperationRepository,
    private val happinessOperationRepository: HappinessOperationRepository,
    private val storageResourceOperationRepository: StorageResourceOperationRepository,
    private val buildingSalaryTaskRepository: BuildingSalaryTaskRepository,
    private val buildingDecayTaskRepository: BuildingDecayTaskRepository
): TaskPerformer<ConstructionTask> {

    override fun execute(task: ConstructionTask, country: Country) {
        // Реализация закрыта
    }

    /**
     * При уничтожении здания нужно остановить производство, выплату зарплаты и гниение
     */
    private fun deleteTasks(country: Country, building: NsiBuilding) {
        activityTermination(country, building)

        buildingDecayTaskRepository.findByCountryAndBuilding(country, building)
            ?.let {
                taskRepository.save(
                    it.task.apply {
                        deletedAt = Date()
                    }
                )
            }
    }

    /**
     * Прекращение всей активности, связанной со зданием в стране.
     * Нужно остановить производство и выплату зарплаты
     */
    private fun activityTermination(country: Country, building: NsiBuilding) {
        productionTaskRepository.findByCountryAndBuilding(country, building)
            ?.let { productionTask ->
                storageResourceOperationRepository.saveAll(
                    productionTask.storageResourceOperation.map {
                        StorageResourceOperation(
                            StorageResourceOperationType.ProductionReturn,
                            abs(it.quantity),
                            country,
                            it.resource
                        )
                    }
                )

                taskRepository.save(
                    productionTask.task.apply {
                        deletedAt = Date()
                    }
                )
            }

        buildingSalaryTaskRepository.findByCountryAndBuilding(country, building)
            ?.let {
                taskRepository.save(
                    it.task.apply {
                        deletedAt = Date()
                    }
                )
            }
    }
}