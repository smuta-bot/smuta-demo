package ru.yan.core.bot

import ru.yan.core.bot.message.InBotMessage
import kotlin.reflect.KClass

/**
 * Описание процесса.
 * Процесс это набор задач выполняемых последовательно
 *
 * @param startCommand Стартовая команда для запуска процесса
 * @param buttons Кнопки под панелью ввода. Они отображаются всегда, в не зависимости от выполняемой задачи, а это значит за обработку кнопок должен отвечать процесс
 */
class BotProcessTree(
    val startCommand: String,
    private val buttons: List<String>,
    id: String,
    executor: KClass<out BotTask>,
): BotTaskNode(id, executor, null) {

    /**
     * Проверка текста сообщения на соответствие кнопкам.
     * При нажатии кнопки сбрасываются входные параметры
     */
    fun checkButtons(message: InBotMessage, params: HashMap<String, Any>): BotTaskNode? {
        val index = buttons.indexOf(message.text)

        return if (index == -1) {
            null
        } else {
            params.clear()
            next[index]
        }
    }
}