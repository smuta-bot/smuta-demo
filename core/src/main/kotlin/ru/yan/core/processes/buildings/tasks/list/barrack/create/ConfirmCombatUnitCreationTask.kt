package ru.yan.core.processes.buildings.tasks.list.barrack.create

import org.springframework.stereotype.Component
import ru.yan.core.bot.BotTask
import ru.yan.core.bot.message.BotButtonMessage
import ru.yan.core.bot.message.InBotMessage
import ru.yan.core.bot.message.InlineButton
import ru.yan.core.bot.message.OutBotMessage
import ru.yan.core.getMessengerId
import ru.yan.core.processes.buildings.tree.COMBAT_UNIT_SIZE
import ru.yan.core.processes.buildings.tree.NSI_COMBAT_UNIT_ID
import ru.yan.database.model.smuta.dto.DtoUser
import ru.yan.database.service.BarrackService
import java.util.HashMap
import java.util.UUID

@Component
class ConfirmCombatUnitCreationTask(
    private val barrackService: BarrackService
): BotTask {

    override fun execute(message: InBotMessage, user: DtoUser, params: HashMap<String, Any>): OutBotMessage {
        val chosenNsiCombatUnitId = params[NSI_COMBAT_UNIT_ID] as String
        val chosenNumberPeople = (params[COMBAT_UNIT_SIZE] as Number).toLong()

        return barrackService.getNsiCombatUnit(UUID.fromString(chosenNsiCombatUnitId))?.let {
            BotButtonMessage(
                "Вы уверены что хотите создать подразделение ${it.name} с количеством людей $chosenNumberPeople?",
                listOf(
                    listOf(InlineButton("Да", "confirm")),
                    listOf(InlineButton("Нет", "not_confirm"))
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
        const val ID = "93f8e817-3465-4dcd-aeb2-b7afa5c6350c"
    }
}