package ru.yan.core.bot.message

/**
 * Класс, конвертирующий сообщение в понятный для каждого типа бота формат
 *
 * TMessage - формат сообщения, который принимает бот
 */
abstract class BotMessageConverter<TMessage> {

    fun convert(message: OutBotMessage): TMessage {
        return when (message) {
            is BotTextMessage -> convertTextMessage(message)
            is BotButtonMessage -> convertButtonMessage(message)
        }
    }

    /**
     * Преобразование сообщения с кнопками
     */
    protected abstract fun convertButtonMessage(message: BotButtonMessage): TMessage

    /**
     * Преобразование обычного текстового сообщения
     */
    protected abstract fun convertTextMessage(message: BotTextMessage): TMessage
}