package ru.yan.core.processes.buildings.tasks.list.barrack.create

import org.springframework.stereotype.Component
import ru.yan.core.bot.BotTask
import ru.yan.core.bot.message.*
import ru.yan.core.description
import ru.yan.core.getMessengerId
import ru.yan.core.processes.buildings.tree.NSI_COMBAT_UNIT_ID
import ru.yan.database.model.smuta.dto.DtoUser
import ru.yan.database.service.BarrackService
import java.util.*

@Component
class DescribeNsiCombatUnitTask(
    private val barrackService: BarrackService
): BotTask {

    override fun execute(message: InBotMessage, user: DtoUser, params: HashMap<String, Any>): OutBotMessage {
        val chosenNsiCombatUnitId = params[NSI_COMBAT_UNIT_ID] as String

        return barrackService.getNsiCombatUnit(UUID.fromString(chosenNsiCombatUnitId))?.let {
            BotButtonMessage(
                it.description(),
                listOf(
                    listOf(InlineButton("Сформировать", "create")),
                    listOf(InlineButton("Назад", "back"))
                ),
                null,
                user.getMessengerId()
            )
        } ?: BotTextMessage("Здание не найдено", user.getMessengerId())
    }

    companion object {
        const val ID = "6491b640-0d0d-479e-a82a-8cf69d16f966"
    }
}