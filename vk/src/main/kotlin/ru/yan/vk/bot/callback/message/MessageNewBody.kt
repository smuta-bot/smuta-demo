package ru.yan.vk.bot.callback.message

import ru.yan.vk.bot.callback.CallbackType

/**
 * Описание объекта, инициировавшего событие [CallbackType.MessageNew]
 */
data class MessageNewBody (

    /**
     * Данные о сообщении, которое написал пользователь
     */
    val message: VkCallbackMessage
)