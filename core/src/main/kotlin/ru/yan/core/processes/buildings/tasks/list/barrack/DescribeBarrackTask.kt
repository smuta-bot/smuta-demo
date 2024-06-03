package ru.yan.core.processes.buildings.tasks.list.barrack

import org.springframework.stereotype.Component
import ru.yan.core.bot.BotTask
import ru.yan.core.bot.message.*
import ru.yan.core.description
import ru.yan.core.getMessengerId
import ru.yan.database.model.nsi.types.BuildingStatus
import ru.yan.database.model.smuta.dto.DtoUser
import ru.yan.database.service.BarrackService
import java.util.HashMap

@Component
class DescribeBarrackTask(
    private val barrackService: BarrackService
): BotTask {

    override fun execute(message: InBotMessage, user: DtoUser, params: HashMap<String, Any>): OutBotMessage {
        return barrackService.getCountryBarrack(user)?.let {
            val keyboard = when(it.status) {
                BuildingStatus.Ready -> {
                    listOf(
                        listOf(InlineButton("Подразделения", "units")),
                        listOf(InlineButton("Текущий бой", "battle")),
                        listOf(InlineButton("Сформировать", "create"))
                    )
                }
                BuildingStatus.ConstructionProcess,
                BuildingStatus.RenovationProcess -> {
                    listOf(
                        listOf(InlineButton("Остановить строительство", "stop_construction"))
                    )
                }
                else -> listOf()
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
        const val ID = "c37d9ec1-cc92-4a3d-86f5-7a4f5a312918"
    }
}