package ru.yan.database.model.bot.dto

import ru.yan.database.model.bot.BotStorageResource
import ru.yan.database.model.nsi.dto.DtoSimpleDictionary
import java.util.*

/**
 * DTO для [BotStorageResource]
 */
data class DtoBotStorageResource(
    val id: UUID,
    val quantity: Double,
    val resource: DtoSimpleDictionary
)