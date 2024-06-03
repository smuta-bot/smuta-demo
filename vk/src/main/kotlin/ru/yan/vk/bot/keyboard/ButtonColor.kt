package ru.yan.vk.bot.keyboard

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Цвет кнопки
 */
@Serializable
enum class ButtonColor {
    /**
     * Основное действие
     */
    @SerialName("primary")
    Primary,

    /**
     * Обычная кнопка
     */
    @SerialName("secondary")
    Secondary,

    /**
     * Опасное действие или отказ, например «Удалить» или «Отклонить»
     */
    @SerialName("negative")
    Negative,

    /**
     * «Согласиться», «Подтвердить»
     */
    @SerialName("positive")
    Positive
}