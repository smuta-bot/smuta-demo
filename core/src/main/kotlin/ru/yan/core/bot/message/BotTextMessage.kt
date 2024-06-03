package ru.yan.core.bot.message

/**
 * Структура, описывающая обычное текстовое сообщение
 *
 * @param text Текст сообщения
 */
data class BotTextMessage(
    val text: String,
    override val userId: Long,
    override val messageId: Long? = null
): OutBotMessage()