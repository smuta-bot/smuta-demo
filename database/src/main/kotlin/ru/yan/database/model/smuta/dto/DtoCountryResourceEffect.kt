package ru.yan.database.model.smuta.dto

import ru.yan.database.model.nsi.dto.DtoNsiResourceEffect
import ru.yan.database.model.smuta.CountryResourceEffect
import java.util.UUID


/**
 * DTO для [CountryResourceEffect]
 */
data class DtoCountryResourceEffect(
    val id: UUID,
    val quantity: Double,
    val nsiResourceEffect: DtoNsiResourceEffect
)