package ru.yan.database.components

import org.springframework.stereotype.Component
import ru.yan.database.model.nsi.NsiBuilding
import ru.yan.database.model.nsi.types.StorageResourceOperationType
import ru.yan.database.model.operation.StorageResourceOperation
import ru.yan.database.model.productionUUID
import ru.yan.database.model.smuta.*
import ru.yan.database.model.task.ProductionProduct
import ru.yan.database.model.task.ProductionTask
import ru.yan.database.model.task.Task
import ru.yan.database.model.task.dto.DtoProductionTask
import ru.yan.database.model.task.dto.toDto
import ru.yan.database.model.task.types.TaskType
import ru.yan.database.repository.nsi.NotificationCategoryRepository
import ru.yan.database.repository.operation.StorageResourceOperationRepository
import ru.yan.database.repository.smuta.CountryBuildingRepository
import ru.yan.database.repository.smuta.NotificationRepository
import ru.yan.database.repository.smuta.ProductRepository
import ru.yan.database.repository.task.ProductionProductRepository
import ru.yan.database.repository.task.ProductionTaskRepository
import ru.yan.database.repository.task.TaskRepository
import java.util.*
import kotlin.math.floor

/**
 * Калькулятор, рассчитывающий кол-ва производимого товара
 */
@Component
class ProductionCalculator(
    private val taskRepository: TaskRepository,
    private val productionTaskRepository: ProductionTaskRepository,
    private val productRepository: ProductRepository,
    private val countryBuildingRepository: CountryBuildingRepository,
    private val productionProductRepository: ProductionProductRepository,
    private val notificationRepository: NotificationRepository,
    private val notificationCategoryRepository: NotificationCategoryRepository,
    private val storageResourceOperationRepository: StorageResourceOperationRepository
) {
    fun calculate(country: Country, nsiBuilding: NsiBuilding): DtoProductionTask {
        val building = countryBuildingRepository.findByCountryAndNsiBuilding(country, nsiBuilding)!!

        return calculate(country, building)
    }

    fun calculate(country: Country, building: CountryBuilding): DtoProductionTask {

        // Реализация закрыта

        val prTask =  ProductionTask(
            building.nsiBuilding,
            taskRepository.save(
                Task(TaskType.Production, building.nsiBuilding.productionTime, country)
            )
        )

        return prTask.toDto()
    }
}