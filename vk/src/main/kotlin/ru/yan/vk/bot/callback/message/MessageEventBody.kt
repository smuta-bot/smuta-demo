package ru.yan.vk.bot.callback.message

import ru.yan.vk.bot.callback.CallbackType

/**
 * Описание объекта, инициировавшего событие [CallbackType.MessageEvent]
 */
data class MessageEventBody(
    /**
     * Идентификатор пользователя
     */
    val userId: Long,

    /**
     * Дополнительная информация, указанная в клавише
     */
    val payload: String,

    /**
     * Идентификатор сообщения в беседе. Не передается для клавиатур беседы.
     */
    val conversationMessageId: Long?
)