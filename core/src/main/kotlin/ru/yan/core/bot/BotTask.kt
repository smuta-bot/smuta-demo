package ru.yan.core.bot

import ru.yan.core.bot.message.InBotMessage
import ru.yan.core.bot.message.OutBotMessage
import ru.yan.database.model.smuta.dto.DtoUser

/**
 * Описание задачи, которую может выполнять бот
 */
interface BotTask {

    /**
     * Выполнить задачу
     */
    fun execute(message: InBotMessage, user: DtoUser, params: HashMap<String, Any>): OutBotMessage
}