package ru.yan.vk.bot.keyboard

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Тип кнопки
 */
@Serializable
enum class ButtonActionType {
    /**
     * Текстовая кнопка
     */
    @SerialName("text")
    Text,

    /**
     * Клавиша-ссылка
     */
    @SerialName("open_link")
    OpenLink,

    /**
     * По нажатию отправляет местоположение в диалог с ботом или беседу
     */
    @SerialName("location")
    Location,

    /**
     * Открывает окно оплаты VK Pay с предопределёнными параметрами
     */
    @SerialName("vkpay")
    VkPay,

    /**
     * Открывает указанное приложение VK Mini Apps
     */
    @SerialName("open_app")
    OpenApp,

    /**
     * Позволяет без отправки сообщения от пользователя получить уведомление о нажатии на кнопку и выполнить необходимое действие
     */
    @SerialName("callback")
    Callback
}