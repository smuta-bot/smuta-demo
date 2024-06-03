package ru.yan.vk.bot.keyboard

import kotlinx.serialization.Serializable

/**
 * Описание кнопки в Vk chat bot
 */
@Serializable
data class VkKeyboardButton(
    /**
     * Объект, описывающий тип действия и его параметры
     */
    val action: VkButtonAction,

    /**
     * Цвет кнопки
     */
    val color: ButtonColor = ButtonColor.Secondary
)