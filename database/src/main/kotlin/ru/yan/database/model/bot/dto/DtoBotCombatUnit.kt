package ru.yan.database.model.bot.dto

import ru.yan.database.model.bot.BotCombatUnit
import ru.yan.database.model.nsi.dto.DtoNsiCombatUnit
import java.util.*

/**
 * DTO для [BotCombatUnit]
 */
data class DtoBotCombatUnit(
    val id: UUID,
    val quantity: Long,
    val experience: Double,
    val nsiCombatUnit: DtoNsiCombatUnit
)
