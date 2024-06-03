package ru.yan.vk.bot.callback.message

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Объект, описывающий личное сообщение
 */
data class VkCallbackMessage(
    /**
     * Идентификатор сообщения
     */
    val id: Long,

    /**
     * Идентификатор отправителя
     */
    @JsonProperty("from_id")
    val fromId: Long,

    /**
     * Текст сообщения
     */
    val text: String,

    /**
     * Сервисное поле для сообщений ботам (полезная нагрузка)
     */
    val payload: VkCallbackMessagePayload?
)
