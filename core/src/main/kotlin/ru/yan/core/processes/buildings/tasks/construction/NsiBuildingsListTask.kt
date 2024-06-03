package ru.yan.core.processes.buildings.tasks.construction

import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component
import ru.yan.core.bot.BotPageInfo
import ru.yan.core.bot.message.InBotMessage
import ru.yan.core.bot.message.BotButtonMessage
import ru.yan.core.bot.message.InlineButton
import ru.yan.core.bot.BotTask
import ru.yan.core.bot.message.OutBotMessage
import ru.yan.core.convertTo
import ru.yan.core.getMessengerId
import ru.yan.core.processes.buildings.tree.BUILDINGS_PAGE
import ru.yan.core.toKeyboard
import ru.yan.database.model.smuta.dto.DtoUser
import ru.yan.database.service.BuildingService

@Component
class NsiBuildingsListTask(
    private val buildingService: BuildingService
): BotTask {

    override fun execute(message: InBotMessage, user: DtoUser, params: HashMap<String, Any>): OutBotMessage {
        val pageInfo = params[BUILDINGS_PAGE]?.convertTo(BotPageInfo::class) ?: BotPageInfo(0, false)

        val buildings = buildingService.getNsiBuildingsForBuild(user, PageRequest.of(pageInfo.number, 3))
        return BotButtonMessage(
            if (buildings.isEmpty) "Зданий для постройки не найдено" else "Список возможных зданий для постройки",
            buildings.toKeyboard { InlineButton(it.name, it.id.toString()) },
            null,
            user.getMessengerId(),
            if (pageInfo.editable) message.messageId else null
        )
    }


    companion object {
        const val ID = "c30bfd05-3f39-4469-8e5f-5ed64db2122b"
    }
}