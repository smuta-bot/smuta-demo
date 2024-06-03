package ru.yan.database.components.task

import org.springframework.stereotype.Component
import ru.yan.database.model.moneyUUID
import ru.yan.database.model.nsi.types.AgeGroup
import ru.yan.database.model.nsi.types.HealthStatus
import ru.yan.database.model.nsi.types.StorageResourceOperationType
import ru.yan.database.model.operation.StorageResourceOperation
import ru.yan.database.model.smuta.Country
import ru.yan.database.model.smuta.employmentRate
import ru.yan.database.repository.nsi.ResourceRepository
import ru.yan.database.repository.operation.HappinessOperationRepository
import ru.yan.database.repository.operation.StorageResourceOperationRepository
import java.util.*

/**
 * Исполнитель, который симулирует поступление денег в бюджет
 */
@Component
class ProfitTaskPerformer(
    private val happinessOperationRepository: HappinessOperationRepository,
    private val storageResourceOperationRepository: StorageResourceOperationRepository,
    private val resourceRepository: ResourceRepository
): TaskPerformer<Nothing?> {

    override fun execute(task: Nothing?, country: Country) {
        // Реализация закрыта
    }
}