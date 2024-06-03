package ru.yan.database.model.operation.dto

import ru.yan.database.model.nsi.types.HappinessOperationType
import ru.yan.database.model.operation.HappinessOperation
import java.util.*

/**
 * DTO для [HappinessOperation]
 */
data class DtoHappinessOperation(
    val id: UUID,
    val type: HappinessOperationType,
    val quantity: Double,
    val createdAt: Date
)