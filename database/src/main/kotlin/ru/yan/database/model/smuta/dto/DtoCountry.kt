package ru.yan.database.model.smuta.dto

import ru.yan.database.model.smuta.Country
import java.util.*

/**
 * DTO для [Country]
 */
data class DtoCountry(
    val id: UUID,
    val name: String,
    val area: Double,
    val morbidity: Double,
    val mortality: Double,
    val mobilization: Boolean,
    val populations: List<DtoPopulation>,
    val buildings: List<DtoCountryBuilding>,
    val storageResources: List<DtoStorageResource>,
    val resourceEffects: List<DtoCountryResourceEffect>,
    val marketProducts: List<DtoMarketProduct>,
    val createdAt: Date,
    val deletedAt: Date?
)
