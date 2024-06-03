package ru.yan.database.model.operation.dto

import ru.yan.database.model.nsi.dto.DtoSimpleDictionary
import ru.yan.database.model.nsi.types.MarketProductOperationType
import ru.yan.database.model.operation.MarketProductOperation
import java.util.*

/**
 * DTO для [MarketProductOperation]
 */
data class DtoMarketProductOperation(
    val id: UUID,
    val type: MarketProductOperationType,
    val price: Double,
    val quantity: Long,
    val resource: DtoSimpleDictionary,
    val createdAt: Date
)
