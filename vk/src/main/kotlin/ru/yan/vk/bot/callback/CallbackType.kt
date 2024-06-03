package ru.yan.vk.bot.callback

/**
 * Тип события
 */
enum class CallbackType {
    /**
     * Новое сообщение
     */
    MessageNew,

    /**
     * Пользователь нажал на Callback-кнопку
     */
    MessageEvent,

    /**
     * Запрос на подтверждение сервера
     */
    Confirmation
}