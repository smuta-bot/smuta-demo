package ru.yan.database.model.smuta.dto

import ru.yan.database.model.nsi.dto.DtoSimpleDictionary
import ru.yan.database.model.operation.dto.DtoStorageResourceOperation
import ru.yan.database.model.smuta.MarketProduct
import java.util.*

/**
 * DTO для [MarketProduct]
 */
data class DtoMarketProduct(
    val id: UUID,
    val price: Double,
    var quantity: Long,
    val resource: DtoSimpleDictionary
)
