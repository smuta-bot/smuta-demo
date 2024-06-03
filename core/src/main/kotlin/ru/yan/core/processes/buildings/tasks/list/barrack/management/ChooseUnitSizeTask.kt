package ru.yan.core.processes.buildings.tasks.list.barrack.management

import org.springframework.stereotype.Component
import ru.yan.core.bot.BotTask
import ru.yan.core.bot.message.BotButtonMessage
import ru.yan.core.bot.message.InBotMessage
import ru.yan.core.bot.message.InlineButton
import ru.yan.core.bot.message.OutBotMessage
import ru.yan.core.getMessengerId
import ru.yan.core.processes.buildings.tree.COMBAT_UNIT_ID
import ru.yan.database.model.smuta.dto.DtoUser
import ru.yan.database.service.BarrackService
import java.util.*

@Component
class ChooseUnitSizeTask(
    private val barrackService: BarrackService
): BotTask {

    override fun execute(message: InBotMessage, user: DtoUser, params: HashMap<String, Any>): OutBotMessage {
        val chosenCombatUnitId = params[COMBAT_UNIT_ID] as String

        return barrackService.getCombatUnit(user, UUID.fromString(chosenCombatUnitId))?.let {
            BotButtonMessage(
                "Выберите или введите кол-во людей. На текущий момент в подразделении ${it.quantity} людей",
                listOf(
                    listOf(InlineButton("Всех", "all")),
                    listOf(InlineButton("Назад", "back"))
                ),
                null,
                user.getMessengerId()
            )
        } ?: BotButtonMessage(
            "Подразделение не найдено",
            listOf(
                listOf(InlineButton("Назад", "back"))
            ),
            null,
            user.getMessengerId()
        )
    }

    companion object {
        const val ID = "36b3d688-54b6-4f9e-a291-ab05f45561f1"
    }
}