package ru.yan.core.bot

import ru.yan.core.bot.message.InBotMessage
import ru.yan.core.bot.message.OutBotMessage
import ru.yan.database.model.smuta.dto.DtoUser

/**
 * Описание процесса.
 * Процесс это та же задача, только при его старте входные параметры сбрасываются
 */
abstract class BotProcess: BotTask {

    override fun execute(message: InBotMessage, user: DtoUser, params: HashMap<String, Any>): OutBotMessage {
        params.clear()

        return start(message, user)
    }

    protected abstract fun start(message: InBotMessage, user: DtoUser): OutBotMessage
}