package ru.yan.core.processes.registration.tasks

import org.springframework.stereotype.Component
import ru.yan.core.bot.message.InBotMessage
import ru.yan.core.bot.message.BotTextMessage
import ru.yan.core.bot.BotTask
import ru.yan.core.bot.message.OutBotMessage
import ru.yan.core.getMessengerId
import ru.yan.database.model.smuta.dto.DtoUser

@Component
class CreateCountryTask: BotTask {
    override fun execute(message: InBotMessage, user: DtoUser, params: HashMap<String, Any>): OutBotMessage {
        return BotTextMessage(
            "Для начала введи название своей страны",
            user.getMessengerId()
        )
    }

    companion object {
        const val ID = "46991bec-21e0-4f6b-bcab-dbb52e22b49c"
    }
}