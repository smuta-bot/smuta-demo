package ru.yan.vk.bot.callback.message

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Описание ошибки
 */
data class MessagesSendError(
    @JsonProperty(value = "error_code")
    val errorCode: Long,

    @JsonProperty(value = "error_msg")
    val errorMsg: String,
)
