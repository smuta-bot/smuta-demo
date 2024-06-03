package ru.yan.database.components.task

import org.springframework.stereotype.Component
import ru.yan.database.components.ProductionCalculator
import ru.yan.database.model.nsi.types.StorageResourceOperationType
import ru.yan.database.model.smuta.Country
import ru.yan.database.model.operation.StorageResourceOperation
import ru.yan.database.model.task.ProductionTask
import ru.yan.database.repository.operation.StorageResourceOperationRepository
import ru.yan.database.repository.smuta.CountryBuildingRepository

/**
 * Исполнитель задачи на производство
 */
@Component
class ProductionTaskPerformer(
    private val productionCalculator: ProductionCalculator,
    private val countryBuildingRepository: CountryBuildingRepository,
    private val storageResourceOperationRepository: StorageResourceOperationRepository
): TaskPerformer<ProductionTask> {

    override fun execute(task: ProductionTask, country: Country) {
        // Реализация закрыта
    }
}