package ru.yan.database.model.task.dto

import ru.yan.database.model.nsi.dto.DtoSimpleDictionary
import ru.yan.database.model.task.ProductionProduct
import java.util.*

/**
 * DTO для [ProductionProduct]
 */
data class DtoProductionProduct(
    val id: UUID,
    val quantity: Double,
    val resource: DtoSimpleDictionary,
    val deletedAt: Date?
)
