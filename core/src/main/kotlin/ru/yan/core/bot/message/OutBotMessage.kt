package ru.yan.core.bot.message

/**
 * Базовая структура, описывающая сообщение для бота
 */
sealed class OutBotMessage {
    /**
     * Id пользователя(кому предназначено сообщение)
     */
    abstract val userId: Long

    /**
     * Id сообщения
     * Обычно равно null, но если нет, то сообщение должно быть отредактировано
     */
    abstract val messageId: Long?
}