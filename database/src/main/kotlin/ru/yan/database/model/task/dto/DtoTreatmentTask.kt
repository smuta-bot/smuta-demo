package ru.yan.database.model.task.dto

import ru.yan.database.model.operation.dto.DtoPopulationOperation
import ru.yan.database.model.task.TreatmentTask
import java.util.UUID

/**
 * DTO для [TreatmentTask]
 */
data class DtoTreatmentTask(
    val id: UUID,
    val populationOperations: List<DtoPopulationOperation>
)
