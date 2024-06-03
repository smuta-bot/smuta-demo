package ru.yan.database.model.operation.dto

import ru.yan.database.model.nsi.types.CountryStateOperationType
import ru.yan.database.model.operation.CountryStateOperation
import java.util.*

/**
 * DTO для [CountryStateOperation]
 */
data class DtoCountryStateOperation(
    val id: UUID,
    val type: CountryStateOperationType,
    val quantity: Double,
    val createdAt: Date
)