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
import ru.yan.database.exceptions.NotEnoughMoneyException
import ru.yan.database.exceptions.NotEnoughResourceException
import ru.yan.database.model.smuta.dto.DtoUser
import ru.yan.database.service.BuildingService
import java.util.*

@Component
class StartConservationTask(
    private val buildingService: BuildingService
): BotTask {
    override fun execute(message: InBotMessage, user: DtoUser, params: HashMap<String, Any>): OutBotMessage {
        val chosenNsiBuildingId = params[NSI_BUILDING_ID] as String

        return try {
            buildingService.conservationBuilding(user, UUID.fromString(chosenNsiBuildingId))

            BotButtonMessage(
                "Процесс консервации запущен",
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
        const val ID = "6d6ef5a4-a693-4352-85ca-d2823099186f"
    }
}