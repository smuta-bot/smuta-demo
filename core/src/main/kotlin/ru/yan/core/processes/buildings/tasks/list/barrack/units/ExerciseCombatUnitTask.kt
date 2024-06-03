package ru.yan.core.processes.buildings.tasks.list.barrack.units

import org.springframework.stereotype.Component
import ru.yan.core.bot.BotTask
import ru.yan.core.bot.message.BotButtonMessage
import ru.yan.core.bot.message.InBotMessage
import ru.yan.core.bot.message.InlineButton
import ru.yan.core.bot.message.OutBotMessage
import ru.yan.core.getMessengerId
import ru.yan.core.processes.buildings.tree.COMBAT_UNIT_ID
import ru.yan.database.exceptions.AlreadyExistsException
import ru.yan.database.model.smuta.dto.DtoUser
import ru.yan.database.service.BarrackService
import java.util.*

@Component
class ExerciseCombatUnitTask(
    private val barrackService: BarrackService
): BotTask {

    override fun execute(message: InBotMessage, user: DtoUser, params: HashMap<String, Any>): OutBotMessage {
        val chosenCombatUnitId = params[COMBAT_UNIT_ID] as String

        return try {
            barrackService.exerciseCombatUnit(UUID.fromString(chosenCombatUnitId))

            BotButtonMessage(
                "Подразделение отправлено на учения",
                listOf(listOf(InlineButton("Понял", "back"))),
                null,
                user.getMessengerId()
            )
        } catch (ex: AlreadyExistsException) {
            BotButtonMessage(
                "Подразделение уже на учениях",
                listOf(listOf(InlineButton("Понял", "back"))),
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
        const val ID = "57785757-7945-4ba2-9a8b-f896783baa44"
    }
}