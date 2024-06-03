package ru.yan.vk.bot.callback.message

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Ответ, который приходит после отправки сообщения
 */
@JsonIgnoreProperties(ignoreUnknown = true)
data class MessageSendResponse(
    /**
     * Идентификатор назначения
     */
    @JsonProperty(value = "peer_id")
    val peerId: Long,

    /**
     * Идентификатор сообщения
     */
    @JsonProperty(value = "message_id")
    val messageId: Long,

    /**
     * Сообщение об ошибке, если сообщение не было доставлено получателю
     */
    @JsonProperty(value = "error")
    val error: MessagesSendError?
)