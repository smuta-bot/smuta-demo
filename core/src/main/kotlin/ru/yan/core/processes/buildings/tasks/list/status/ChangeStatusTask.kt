package ru.yan.core.processes.buildings.tasks.list.status

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
class ChangeStatusTask(
    private val buildingService: BuildingService
): BotTask {

    override fun execute(message: InBotMessage, user: DtoUser, params: HashMap<String, Any>): OutBotMessage {
        val chosenNsiBuildingId = params[NSI_BUILDING_ID] as String

        return buildingService.getCountryBuilding(user, UUID.fromString(chosenNsiBuildingId))?.let {
            val keyboard = when(it.status) {
                BuildingStatus.Ready -> {
                    listOf(
                        listOf(InlineButton("Запустить производство", "start_production")),
                        listOf(InlineButton("Уничтожить", "start_destroy")),
                        listOf(InlineButton("Законсервировать", "start_conservation"))
                    )
                }
                BuildingStatus.Mothballed -> {
                    listOf(
                        listOf(InlineButton("Расконсервировать", "reactivate"))
                    )
                }
                BuildingStatus.Destroyed -> {
                    listOf(
                        listOf(InlineButton("Восстановить", "restore"))
                    )
                }
                BuildingStatus.ProductionProcess -> {
                    listOf(
                        listOf(InlineButton("Остановить производство", "stop_production")),
                        listOf(InlineButton("Уничтожить", "start_destroy")),
                        listOf(InlineButton("Законсервировать", "start_conservation"))
                    )
                }
                BuildingStatus.RenovationProcess,
                BuildingStatus.ConstructionProcess -> {
                    listOf(
                        listOf(InlineButton("Остановить строительство", "stop_construction"))
                    )
                }
                BuildingStatus.DestroyProcess -> {
                    listOf(
                        listOf(InlineButton("Отменить разрушение", "stop_destroy"))
                    )
                }
                BuildingStatus.ConservationProcess -> {
                    listOf(
                        listOf(InlineButton("Отменить консервацию", "stop_conservation"))
                    )
                }
                BuildingStatus.ReactivationProcess -> {
                    listOf(
                        listOf(InlineButton("Отменить расконсервацию", "stop_reactivation"))
                    )
                }
            }.plus(
                listOf(
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
        const val ID = "df6d6b8a-b976-415b-a6ef-0b05ecc3ba99"
    }
}