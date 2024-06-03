package ru.yan.vk.bot.callback.message

import kotlinx.serialization.Serializable

/**
 * Описание полезной нагрузки
 */
@Serializable
data class VkCallbackMessagePayload (
    val command: String
)