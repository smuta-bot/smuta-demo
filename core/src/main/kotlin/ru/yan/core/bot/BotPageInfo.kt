package ru.yan.core.bot

/**
 * Информация о странице
 *
 * @param number Номер страницы
 * @param editable Редактируется ли страница
 */
data class BotPageInfo(
    val number: Int,
    val editable: Boolean
)
