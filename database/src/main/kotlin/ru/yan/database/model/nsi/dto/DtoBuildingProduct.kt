package ru.yan.database.model.nsi.dto

import ru.yan.database.model.nsi.BuildingProduct
import java.util.*

/**
 * DTO для [BuildingProduct]
 */
data class DtoBuildingProduct(
    val id: UUID,
    val quantityPerLevel: Double,
    val resource: DtoSimpleDictionary,
    val productResources: List<DtoBuildingProductResource>,
    val byproducts: List<DtoByproduct>
)
