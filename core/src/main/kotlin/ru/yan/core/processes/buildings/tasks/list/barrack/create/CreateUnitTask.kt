package ru.yan.core.processes.buildings.tasks.list.barrack.create

import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component
import ru.yan.core.bot.BotPageInfo
import ru.yan.core.bot.BotTask
import ru.yan.core.bot.message.BotButtonMessage
import ru.yan.core.bot.message.InBotMessage
import ru.yan.core.bot.message.InlineButton
import ru.yan.core.bot.message.OutBotMessage
import ru.yan.core.convertTo
import ru.yan.core.getMessengerId
import ru.yan.core.processes.buildings.tree.NSI_COMBAT_UNITS_PAGE
import ru.yan.core.toKeyboard
import ru.yan.database.model.smuta.dto.DtoUser
import ru.yan.database.service.BarrackService
import java.util.HashMap

@Component
class CreateUnitTask(
    private val barrackService: BarrackService
): BotTask {

    override fun execute(message: InBotMessage, user: DtoUser, params: HashMap<String, Any>): OutBotMessage {
        val pageInfo = params[NSI_COMBAT_UNITS_PAGE]?.convertTo(BotPageInfo::class) ?: BotPageInfo(0, false)
        val units = barrackService.getNsiCombatUnits(PageRequest.of(pageInfo.number, 3))
        return BotButtonMessage(
            "Выберите подразделение",
            units.toKeyboard { InlineButton(it.name, it.id.toString()) }
                .plus(listOf(
                    listOf(InlineButton("Назад", "back"))
                )),
            null,
            user.getMessengerId()
        )
    }

    companion object {
        const val ID = "5a5c6c2e-fd46-470a-82be-56e133c17ddb"
    }
}