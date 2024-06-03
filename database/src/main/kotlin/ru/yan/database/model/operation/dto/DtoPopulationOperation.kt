package ru.yan.database.model.operation.dto

import ru.yan.database.model.nsi.types.AgeGroup
import ru.yan.database.model.nsi.types.Gender
import ru.yan.database.model.nsi.types.PopulationOperationType
import ru.yan.database.model.operation.PopulationOperation
import ru.yan.database.model.smuta.dto.DtoCountry
import ru.yan.database.model.task.dto.DtoTreatmentTask
import java.util.*

/**
 * DTO для [PopulationOperation]
 */
data class DtoPopulationOperation(
    val id: UUID,
    val type: PopulationOperationType,
    val quantity: Long,
    val gender: Gender,
    val ageGroup: AgeGroup,
    val createdAt: Date
)
