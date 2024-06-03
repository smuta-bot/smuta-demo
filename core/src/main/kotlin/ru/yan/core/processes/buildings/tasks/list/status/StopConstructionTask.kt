package ru.yan.core.processes.buildings.tasks.list.status

import org.springframework.stereotype.Component
import ru.yan.core.bot.BotTask
import ru.yan.core.bot.message.InBotMessage
import ru.yan.core.bot.message.BotButtonMessage
import ru.yan.core.bot.message.InlineButton
import ru.yan.core.bot.message.OutBotMessage
import ru.yan.core.getMessengerId
import ru.yan.core.processes.buildings.tree.NSI_BUILDING_ID
import ru.yan.database.exceptions.AlreadyExistsException
import ru.yan.database.model.smuta.dto.DtoUser
import ru.yan.database.service.BuildingService
import java.util.*
import kotlin.collections.HashMap

@Component
class StopConstructionTask(
    val buildingService: BuildingService
): BotTask {

    override fun execute(message: InBotMessage, user: DtoUser, params: HashMap<String, Any>): OutBotMessage {
        val chosenNsiBuildingId = params[NSI_BUILDING_ID] as String

        return try {
            buildingService.stopConstruction(user, UUID.fromString(chosenNsiBuildingId))

            BotButtonMessage(
                "Строительство отменено",
                listOf(
                    listOf(InlineButton("Понял", "back"))
                ),
                null,
                user.getMessengerId()
            )
        } catch (ex: AlreadyExistsException) {
            BotButtonMessage(
                "Строительство уже отменено",
                listOf(listOf(InlineButton("Понял", "back"))),
                null,
                user.getMessengerId()
            )
        } catch (ex: Throwable) {
            BotButtonMessage(
                "Что то пошло не так",
                listOf(
                    listOf(InlineButton("Назад", "back"))
                ),
                null,
                user.getMessengerId()
            )
        }
    }

    companion object {
        const val ID = "a5c8bf50-4056-4403-8086-8e4b117275e1"
    }
}