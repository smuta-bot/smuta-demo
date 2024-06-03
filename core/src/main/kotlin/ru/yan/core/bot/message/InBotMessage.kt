package ru.yan.core.bot.message

/**
 * Базовая структура, описывающая сообщение от бота
 *
 * @param userId Id пользователя, отправившего сообщение
 * @param messageId Id сообщения
 * @param text Текст сообщения
 * @param callbackData Данные, привязанные к кнопке, которую нажал пользователь
 */
data class InBotMessage(
    val userId: Long,
    val messageId: Long,
    val text: String?,
    val callbackData: String?
)