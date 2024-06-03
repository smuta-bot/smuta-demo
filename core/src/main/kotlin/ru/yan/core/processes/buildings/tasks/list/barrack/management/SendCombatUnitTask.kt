package ru.yan.core.processes.buildings.tasks.list.barrack.management

import org.hibernate.exception.ConstraintViolationException
import org.springframework.stereotype.Component
import ru.yan.core.bot.BotTask
import ru.yan.core.bot.message.BotButtonMessage
import ru.yan.core.bot.message.InBotMessage
import ru.yan.core.bot.message.InlineButton
import ru.yan.core.bot.message.OutBotMessage
import ru.yan.core.getMessengerId
import ru.yan.core.processes.buildings.tree.COMBAT_UNIT_ID
import ru.yan.core.processes.buildings.tree.COMBAT_UNIT_SIZE
import ru.yan.database.exceptions.NotEnoughPeopleException
import ru.yan.database.exceptions.PermissionDeniedException
import ru.yan.database.model.smuta.dto.DtoUser
import ru.yan.database.service.BattleService
import java.util.*

@Component
class SendCombatUnitTask(
    private val battleService: BattleService
): BotTask {

    override fun execute(message: InBotMessage, user: DtoUser, params: HashMap<String, Any>): OutBotMessage {
        val chosenCombatUnitId = params[COMBAT_UNIT_ID] as String
        val chosenCombatUnitSize = (params[COMBAT_UNIT_SIZE] as Number).toLong()

        return try {
            battleService.sendCombatUnit(user, UUID.fromString(chosenCombatUnitId), chosenCombatUnitSize)

            BotButtonMessage(
                "Подразделение отправлено",
                listOf(listOf(InlineButton("Понял", "got_it"))),
                null,
                user.getMessengerId()
            )
        } catch (ex: NotEnoughPeopleException) {
            BotButtonMessage(
                "Не хватает людей",
                listOf(listOf(InlineButton("Назад", "back"))),
                null,
                user.getMessengerId()
            )
        } catch (ex: PermissionDeniedException) {
            BotButtonMessage(
                "В текущий момент отправка подразделений невозможна",
                listOf(listOf(InlineButton("Понял", "got_it"))),
                null,
                user.getMessengerId()
            )
        } catch (ex: Throwable) {
            if (ex.cause is ConstraintViolationException) {
                BotButtonMessage(
                    "Подразделение уже отправлено",
                    listOf(listOf(InlineButton("Понял", "got_it"))),
                    null,
                    user.getMessengerId()
                )
            } else {
                BotButtonMessage(
                    "Что то пошло не так",
                    listOf(listOf(InlineButton("Назад", "back"))),
                    null,
                    user.getMessengerId()
                )
            }
        }
    }

    companion object {
        const val ID = "43bd3e39-be96-4a3a-95d7-cc3ddeae6471"
    }
}