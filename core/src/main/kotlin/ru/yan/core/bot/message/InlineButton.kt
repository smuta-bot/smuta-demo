package ru.yan.core.bot.message

/**
 * Описание inline кнопки(кнопки расположенной под сообщением)
 *
 * @param text Текст кнопки, отображаемый пользователю
 * @param callbackData Данные, используемые при нажатии кнопки
 */
data class InlineButton(
    val text: String,
    val callbackData: String
)
