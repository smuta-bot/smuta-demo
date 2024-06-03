package ru.yan.core.bot.message

/**
 * Структура, описывающая сообщение с кнопками
 *
 * @param text Текст сообщения
 * @param inlineKeyboard Набор кнопок, расположенный под сообщением
 * @param keyboard Набор кнопок, расположенный под строкой ввода
 */
data class BotButtonMessage(
    val text: String,
    val inlineKeyboard: List<List<InlineButton>>?,
    val keyboard: List<List<String>>?,
    override val userId: Long,
    override val messageId: Long? = null
): OutBotMessage()