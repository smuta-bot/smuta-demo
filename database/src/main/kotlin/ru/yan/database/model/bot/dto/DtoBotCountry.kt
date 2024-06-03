package ru.yan.database.model.bot.dto

import ru.yan.database.model.bot.BotCountry
import java.util.*

/**
 * DTO для [BotCountry]
 */
data class DtoBotCountry(
    val id: UUID,
    val name: String,
    val area: Double
)
