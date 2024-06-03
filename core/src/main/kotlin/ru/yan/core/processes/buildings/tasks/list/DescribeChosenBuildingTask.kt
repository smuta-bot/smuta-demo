package ru.yan.core.processes.buildings.tasks.list

import org.springframework.stereotype.Component
import ru.yan.core.bot.BotTask
import ru.yan.core.bot.message.InBotMessage
import ru.yan.core.bot.message.BotButtonMessage
import ru.yan.core.bot.message.BotTextMessage
import ru.yan.core.bot.message.InlineButton
import ru.yan.core.bot.message.OutBotMessage
import ru.yan.core.description
import ru.yan.core.getMessengerId
import ru.yan.core.processes.buildings.tree.NSI_BUILDING_ID
import ru.yan.database.model.nsi.types.BuildingStatus
import ru.yan.database.model.smuta.dto.DtoUser
import ru.yan.database.service.BuildingService
import java.util.*

@Component
class DescribeChosenBuildingTask(
    private val buildingService: BuildingService
): BotTask {

    override fun execute(message: InBotMessage, user: DtoUser, params: HashMap<String, Any>): OutBotMessage {
        val chosenNsiBuildingId = params[NSI_BUILDING_ID] as String

        return buildingService.getCountryBuilding(user, UUID.fromString(chosenNsiBuildingId))?.let {
           val keyboard = when(it.status) {
                BuildingStatus.Ready,
                BuildingStatus.ProductionProcess -> {
                    listOf(
                        listOf(InlineButton("Производимая продукция", "products")),
                        listOf(InlineButton("Изменить уровень", "level_change")),
                    )
                }
               else -> listOf()
            }.plus(
               listOf(
                   listOf(InlineButton("Изменить статус", "status_change")),
                   listOf(InlineButton("Назад", "back"))
               )
            )

            BotButtonMessage(
                it.description(),
                keyboard,
                null,
                user.getMessengerId()
            )
        } ?: BotTextMessage("Здание не найдено", user.getMessengerId())
    }

    companion object {
        const val ID = "f35245d4-1223-4cd9-9d8f-6977cba416d1"
    }
}