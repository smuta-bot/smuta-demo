package ru.yan.database.model.nsi.dto

import ru.yan.database.model.nsi.Battlefield
import ru.yan.database.model.nsi.types.DayTime
import ru.yan.database.model.nsi.types.Weather
import java.util.*

/**
 * DTO для [Battlefield]
 */
data class DtoBattlefield(
    val id: UUID,
    val dayTime: DayTime,
    val weather: Weather,
    val nsiBattlefieldEffects: List<DtoNsiBattlefieldEffect>
)
