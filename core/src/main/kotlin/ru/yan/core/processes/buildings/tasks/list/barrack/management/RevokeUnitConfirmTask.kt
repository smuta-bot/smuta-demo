package ru.yan.core.processes.buildings.tasks.list.barrack.management

import org.springframework.stereotype.Component
import ru.yan.core.bot.BotTask
import ru.yan.core.bot.message.BotButtonMessage
import ru.yan.core.bot.message.InBotMessage
import ru.yan.core.bot.message.InlineButton
import ru.yan.core.bot.message.OutBotMessage
import ru.yan.core.getMessengerId
import ru.yan.database.model.smuta.dto.DtoUser
import ru.yan.database.service.BattleService
import java.util.HashMap

@Component
class RevokeUnitConfirmTask(
    private val battleService: BattleService
): BotTask {

    override fun execute(message: InBotMessage, user: DtoUser, params: HashMap<String, Any>): OutBotMessage {
        return battleService.getCountryFightingUnit(user)?.let {
            BotButtonMessage(
                "Вы уверены что хотите отозвать подразделение ${it.nsiCombatUnit.name}?",
                listOf(
                    listOf(InlineButton("Да", "confirm")),
                    listOf(InlineButton("Нет", "not_confirm"))
                ),
                null,
                user.getMessengerId()
            )
        } ?: BotButtonMessage(
            "Вы не отправляли подразделение",
            listOf(
                listOf(InlineButton("Назад", "back"))
            ),
            null,
            user.getMessengerId()
        )
    }

    companion object {
        const val ID = "5cb0aae2-2ba7-4af8-b7d4-02cde1bcd4db"
    }
}