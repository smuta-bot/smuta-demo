package ru.yan.core.processes.buildings.tasks.list.level

import org.springframework.stereotype.Component
import ru.yan.core.bot.BotTask
import ru.yan.core.bot.message.InBotMessage
import ru.yan.core.bot.message.BotButtonMessage
import ru.yan.core.bot.message.BotTextMessage
import ru.yan.core.bot.message.InlineButton
import ru.yan.core.bot.message.OutBotMessage
import ru.yan.core.getMessengerId
import ru.yan.core.processes.buildings.tree.NSI_BUILDING_ID
import ru.yan.database.model.smuta.dto.DtoUser
import ru.yan.database.service.BuildingService
import java.util.*

@Component
class ChooseBuildingLevelTask(
    private val buildingService: BuildingService
): BotTask {

    override fun execute(message: InBotMessage, user: DtoUser, params: HashMap<String, Any>): OutBotMessage {
        val chosenNsiBuildingId = params[NSI_BUILDING_ID] as String

        return buildingService.getCountryBuilding(user, UUID.fromString(chosenNsiBuildingId))?.let {
            BotButtonMessage(
                "Выберите или введите новый уровень здания(уровень может быть больше или меньше текущего, но не меньше нуля)",
                listOf(
                    listOf(InlineButton("Уровень ${it.level + 1}", "${it.level + 1}")),
                    listOf(InlineButton("Назад", "back"))
                ),
                null,
                user.getMessengerId()
            )
        } ?: BotTextMessage("Здание не найдено", user.getMessengerId())
    }

    companion object {
        const val ID = "d076076d-418d-481c-bd9b-9d7c3f3e6263"
    }
}