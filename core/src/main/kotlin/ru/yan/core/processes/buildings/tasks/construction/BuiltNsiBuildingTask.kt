package ru.yan.core.processes.buildings.tasks.construction

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
import java.util.UUID

@Component
class BuiltNsiBuildingTask(
    private val buildingService: BuildingService
): BotTask {

    override fun execute(message: InBotMessage, user: DtoUser, params: HashMap<String, Any>): OutBotMessage {
        val chosenBuildingId = params[NSI_BUILDING_ID] as String
        val chosenBuildingLevel = params[NEW_BUILDING_LEVEL] as Int

        return try {
            buildingService.buildBuilding(user, UUID.fromString(chosenBuildingId), chosenBuildingLevel)

            BotButtonMessage(
                "Процесс строительства запущен",
                listOf(listOf(InlineButton("Понял", "got_it"))),
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
                "Здание строится или уже построено",
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
        const val ID = "4ca68142-31d7-4e98-b624-5799f39da77b"
    }
}