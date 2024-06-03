package ru.yan.core.processes.buildings.tasks.construction

import org.springframework.stereotype.Component
import ru.yan.core.bot.message.InBotMessage
import ru.yan.core.bot.message.BotButtonMessage
import ru.yan.core.bot.message.BotTextMessage
import ru.yan.core.bot.message.InlineButton
import ru.yan.core.bot.BotTask
import ru.yan.core.bot.message.OutBotMessage
import ru.yan.core.description
import ru.yan.core.getMessengerId
import ru.yan.core.processes.buildings.tree.NSI_BUILDING_ID
import ru.yan.database.model.smuta.dto.DtoUser
import ru.yan.database.service.BuildingService
import java.util.*

@Component
class DescribeNsiBuildingTask(
    private val buildingService: BuildingService
): BotTask {

    override fun execute(message: InBotMessage, user: DtoUser, params: HashMap<String, Any>): OutBotMessage {
        val chosenNsiBuildingId = params[NSI_BUILDING_ID] as String

        return buildingService.getNsiBuilding(UUID.fromString(chosenNsiBuildingId))?.let {
            BotButtonMessage(
                it.description(),
                listOf(
                    listOf(InlineButton("Построить", "build")),
                    listOf(InlineButton("Выбрать уровень", "level")),
                    listOf(InlineButton("Назад", "back"))
                ),
                null,
                user.getMessengerId()
            )
        } ?: BotTextMessage("Здание не найдено", user.getMessengerId())
    }

    companion object {
        const val ID = "0631c130-af00-47f0-98b2-3913a80bceca"
    }
}