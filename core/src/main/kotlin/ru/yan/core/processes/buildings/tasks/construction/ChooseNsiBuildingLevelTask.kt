package ru.yan.core.processes.buildings.tasks.construction

import org.springframework.stereotype.Component
import ru.yan.core.bot.BotTask
import ru.yan.core.bot.message.InBotMessage
import ru.yan.core.bot.message.BotButtonMessage
import ru.yan.core.bot.message.InlineButton
import ru.yan.core.bot.message.OutBotMessage
import ru.yan.core.getMessengerId
import ru.yan.database.model.smuta.dto.DtoUser

@Component
class ChooseNsiBuildingLevelTask: BotTask {

    override fun execute(message: InBotMessage, user: DtoUser, params: HashMap<String, Any>): OutBotMessage {
        return BotButtonMessage(
            "Выберите или введите уровень здания для постройки",
            listOf(
                listOf(InlineButton("Уровень 1", "level_1")),
                listOf(InlineButton("Уровень 5", "level_5")),
                listOf(InlineButton("Уровень 10", "level_10")),
                listOf(InlineButton("Назад", "back"))
            ),
            null,
            user.getMessengerId()
        )
    }

    companion object {
        const val ID = "10df2835-21ca-4050-b621-7ce876a152ea"
    }
}