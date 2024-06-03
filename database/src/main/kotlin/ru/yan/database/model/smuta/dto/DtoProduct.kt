package ru.yan.database.model.smuta.dto

import ru.yan.database.model.nsi.dto.DtoBuildingProduct
import ru.yan.database.model.smuta.Product
import java.util.*

/**
 * DTO для [Product]
 */
data class DtoProduct(
    val id: UUID,
    val buildingProduct: DtoBuildingProduct
)
