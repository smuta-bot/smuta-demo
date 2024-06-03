package ru.yan.database.model.smuta.dto

import ru.yan.database.model.nsi.dto.DtoNsiBuilding
import ru.yan.database.model.nsi.types.BuildingStatus
import ru.yan.database.model.smuta.CountryBuilding
import java.util.*

/**
 * DTO для [CountryBuilding]
 */
data class DtoCountryBuilding(
    val id: UUID,
    val status: BuildingStatus,
    val level: Int,
    val efficiency: Double,
    val nsiBuilding: DtoNsiBuilding
)
