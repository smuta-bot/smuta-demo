package ru.yan.core.processes.buildings.tasks.list.barrack.units

import org.springframework.stereotype.Component
import ru.yan.core.bot.BotTask
import ru.yan.core.bot.message.BotButtonMessage
import ru.yan.core.bot.message.InBotMessage
import ru.yan.core.bot.message.InlineButton
import ru.yan.core.bot.message.OutBotMessage
import ru.yan.core.description
import ru.yan.core.getMessengerId
import ru.yan.core.processes.buildings.tree.COMBAT_UNIT_ID
import ru.yan.database.model.nsi.types.CombatUnitStatus
import ru.yan.database.model.smuta.dto.DtoUser
import ru.yan.database.service.BarrackService
import java.util.*

@Component
class DescribeUnitTask(
    private val barrackService: BarrackService
): BotTask {

    override fun execute(message: InBotMessage, user: DtoUser, params: HashMap<String, Any>): OutBotMessage {
        val chosenCombatUnitId = params[COMBAT_UNIT_ID] as String

        return barrackService.getCombatUnit(user, UUID.fromString(chosenCombatUnitId))?.let {
            val keyboard = when(it.status) {
                CombatUnitStatus.Ready -> {
                    listOf(
                        listOf(InlineButton("Отправить на учения", "exercise"))
                    )
                }
                CombatUnitStatus.Exercise -> {
                    listOf(
                        listOf(InlineButton("Вернуть с учений", "abort"))
                    )
                }
                CombatUnitStatus.Formation -> {
                    listOf(
                        listOf(InlineButton("Отменить формирование", "abort"))
                    )
                }
                CombatUnitStatus.Disbandment -> {
                    listOf(
                        listOf(InlineButton("Отменить расформирование", "abort"))
                    )
                }
                else -> {
                    listOf(listOf())
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
        const val ID = "70e255d5-7289-46e2-94e2-c824acefed9f"
    }
}