package ru.yan.core.processes.buildings.tasks.list.barrack.management

import org.springframework.stereotype.Component
import ru.yan.core.bot.BotTask
import ru.yan.core.bot.message.BotButtonMessage
import ru.yan.core.bot.message.InBotMessage
import ru.yan.core.bot.message.InlineButton
import ru.yan.core.bot.message.OutBotMessage
import ru.yan.core.getMessengerId
import ru.yan.database.exceptions.PermissionDeniedException
import ru.yan.database.model.smuta.dto.DtoUser
import ru.yan.database.service.BattleService
import java.util.*

@Component
class RevokeUnitTask(
    private val battleService: BattleService
): BotTask {
    override fun execute(message: InBotMessage, user: DtoUser, params: HashMap<String, Any>): OutBotMessage {

        return try {
            battleService.revokeCombatUnit(user)

            BotButtonMessage(
                "Подразделение отозвано",
                listOf(listOf(InlineButton("Понял", "got_it"))),
                null,
                user.getMessengerId()
            )
        } catch (ex: PermissionDeniedException) {
            BotButtonMessage(
                "В текущий момент отзыв подразделения невозможен",
                listOf(listOf(InlineButton("Понял", "got_it"))),
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
        const val ID = "f65d8cb8-835e-4ae7-b390-e10e570a5b88"
    }
}