package ru.yan.database.model.nsi.dto

import ru.yan.database.model.nsi.BuildingProductResource
import java.util.UUID

/**
 * DTO для [BuildingProductResource]
 */
data class DtoBuildingProductResource(
    val id: UUID,
    var quantity: Double,
    var resource: DtoSimpleDictionary
)
