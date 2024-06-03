package ru.yan.database.components.task

import org.springframework.stereotype.Component
import ru.yan.database.components.TreatmentCalculator
import ru.yan.database.model.hospitalUUID
import ru.yan.database.model.nsi.types.HappinessOperationType
import ru.yan.database.model.nsi.types.HealthStatus
import ru.yan.database.model.nsi.types.PopulationOperationType
import ru.yan.database.model.operation.HappinessOperation
import ru.yan.database.model.smuta.Country
import ru.yan.database.model.operation.PopulationOperation
import ru.yan.database.model.task.TreatmentTask
import ru.yan.database.repository.operation.HappinessOperationRepository
import ru.yan.database.repository.operation.PopulationOperationRepository
import ru.yan.database.repository.smuta.CountryBuildingRepository
import java.util.*

/**
 * Исполнитель задачи на лечение
 */
@Component
class TreatmentTaskPerformer(
    private val populationOperationRepository: PopulationOperationRepository,
    private val countryBuildingRepository: CountryBuildingRepository,
    private val happinessOperationRepository: HappinessOperationRepository,
    private val treatmentCalculator: TreatmentCalculator
): TaskPerformer<TreatmentTask> {

    override fun execute(task: TreatmentTask, country: Country) {
        // Реализация закрыта
    }
}