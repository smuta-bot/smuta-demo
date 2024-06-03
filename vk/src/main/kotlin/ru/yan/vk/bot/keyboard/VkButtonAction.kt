package ru.yan.vk.bot.keyboard

import kotlinx.serialization.Serializable

/**
 * Описание действия кнопки в Vk chat bot
 */
@Serializable
data class VkButtonAction(
    /**
     * Текст кнопки
     */
    val label: String,

    /**
     * Тип клавиши
     */
    val type: ButtonActionType,

    /**
     * Дополнительная информация
     */
    val payload: VkActionPayload
)