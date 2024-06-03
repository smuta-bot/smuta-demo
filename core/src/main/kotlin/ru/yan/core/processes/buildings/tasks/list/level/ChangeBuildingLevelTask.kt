package ru.yan.core.processes.buildings.tasks.list.level

import org.springframework.stereotype.Component
import ru.yan.core.bot.BotTask
import ru.yan.core.bot.message.InBotMessage
import ru.yan.core.bot.message.BotButtonMessage
import ru.yan.core.bot.message.InlineButton
import ru.yan.core.bot.message.OutBotMessage
import ru.yan.core.getMessengerId
import ru.yan.core.processes.buildings.tree.NEW_BUILDING_LEVEL
import ru.yan.core.processes.buildings.tree.NSI_BUILDING_ID
import ru.yan.database.exceptions.AlreadyExistsException
import ru.yan.database.exceptions.NotEnoughAreaException
import ru.yan.database.exceptions.NotEnoughMoneyException
import ru.yan.database.exceptions.NotEnoughResourceException
import ru.yan.database.model.smuta.dto.DtoUser
import ru.yan.database.service.BuildingService
import java.util.*

@Component
class ChangeBuildingLevelTask(
    val buildingService: BuildingService
): BotTask {
    override fun execute(message: InBotMessage, user: DtoUser, params: HashMap<String, Any>): OutBotMessage {
        val chosenNsiBuildingId = params[NSI_BUILDING_ID] as String
        val chosenBuildingLevel = params[NEW_BUILDING_LEVEL] as Int

        return try {
            buildingService.changeBuildingLevel(user, UUID.fromString(chosenNsiBuildingId), chosenBuildingLevel)

            BotButtonMessage(
                "Процесс запущен",
                listOf(listOf(InlineButton("Понял", "back"))),
                null,
                user.getMessengerId()
            )
        } catch (ex: NotEnoughResourceException) {
            BotButtonMessage(
                "Недостаточно ресурсов",
                listOf(listOf(InlineButton("Назад", "back"))),
                null,
                user.getMessengerId()
            )
        } catch (ex: NotEnoughMoneyException) {
            BotButtonMessage(
                "Недостаточно денег",
                listOf(listOf(InlineButton("Назад", "back"))),
                null,
                user.getMessengerId()
            )
        } catch (ex: NotEnoughAreaException) {
            BotButtonMessage(
                "Не хватает свободной территории",
                listOf(listOf(InlineButton("Назад", "back"))),
                null,
                user.getMessengerId()
            )
        } catch (ex: AlreadyExistsException) {
            BotButtonMessage(
                "Процесс уже запущен",
                listOf(listOf(InlineButton("Назад", "back"))),
                null,
                user.getMessengerId()
            )
        } catch (ex: Throwable) {
            BotButtonMessage(
                "Что то пошло не так",
                listOf(listOf(InlineButton("Назад", "back"))),
                null,
                user.getMessengerId()
            )
        }
    }

    companion object {
        const val ID = "4b4788b3-d5bf-4d18-a4e7-a8c1247d05c6"
    }
}