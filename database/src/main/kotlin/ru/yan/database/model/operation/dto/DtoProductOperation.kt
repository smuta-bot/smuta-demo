package ru.yan.database.model.operation.dto

import ru.yan.database.model.nsi.dto.DtoBuildingProduct
import ru.yan.database.model.nsi.types.ProductOperationType
import ru.yan.database.model.operation.ProductOperation
import java.util.*

/**
 * DTO для [ProductOperation]
 */
data class DtoProductOperation(
    val id: UUID,
    val type: ProductOperationType,
    val buildingProduct: DtoBuildingProduct,
    val createdAt: Date
)
