package ru.yan.core.processes.buildings.tasks.list.barrack.create

import org.hibernate.exception.ConstraintViolationException
import org.springframework.stereotype.Component
import ru.yan.core.bot.BotTask
import ru.yan.core.bot.message.*
import ru.yan.core.getMessengerId
import ru.yan.core.processes.buildings.tree.COMBAT_UNIT_SIZE
import ru.yan.core.processes.buildings.tree.NSI_COMBAT_UNIT_ID
import ru.yan.database.model.smuta.dto.DtoUser
import ru.yan.database.service.BarrackService
import java.util.*

@Component
class CreateCombatUnitTask(
    private val barrackService: BarrackService
): BotTask {

    override fun execute(message: InBotMessage, user: DtoUser, params: HashMap<String, Any>): OutBotMessage {
        val chosenNsiCombatUnitId = params[NSI_COMBAT_UNIT_ID] as String
        val chosenNumberPeople = (params[COMBAT_UNIT_SIZE] as Number).toLong()

        return try {
            barrackService.createCombatUnit(user, UUID.fromString(chosenNsiCombatUnitId), chosenNumberPeople)

            BotButtonMessage(
                "Подразделение создано",
                listOf(listOf(InlineButton("Понял", "got_it"))),
                null,
                user.getMessengerId()
            )
        } catch (ex: Throwable) {
            if (ex.cause is ConstraintViolationException) {
                BotButtonMessage(
                    "Подразделение уже существует",
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
        const val ID = "2d20a3c3-6ab4-4441-8e4b-80ba95905007"
    }
}