package ru.yan.vk.bot.keyboard

import kotlinx.serialization.Serializable

/**
 * Дополнительная информация, привязанная к действию
 */
@Serializable
data class VkActionPayload(
    val command: String
)