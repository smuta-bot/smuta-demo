package ru.yan.database.model.nsi.dto

import ru.yan.database.model.nsi.BuildingResource
import java.util.*

/**
 * DTO для [BuildingResource]
 */
data class DtoBuildingResource(
    val id: UUID,
    val quantityPerLevel: Double,
    val resource: DtoSimpleDictionary
)
