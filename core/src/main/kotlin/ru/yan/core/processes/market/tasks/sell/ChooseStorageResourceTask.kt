package ru.yan.core.processes.market.tasks.sell

import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component
import ru.yan.core.bot.BotPageInfo
import ru.yan.core.bot.BotTask
import ru.yan.core.bot.message.InBotMessage
import ru.yan.core.bot.message.BotButtonMessage
import ru.yan.core.bot.message.InlineButton
import ru.yan.core.bot.message.OutBotMessage
import ru.yan.core.convertTo
import ru.yan.core.getMessengerId
import ru.yan.core.processes.market.tree.MARKET_PAGE
import ru.yan.core.toKeyboard
import ru.yan.database.model.smuta.dto.DtoUser
import ru.yan.database.service.MarketService

@Component
class ChooseStorageResourceTask(
    private val marketService: MarketService
): BotTask {

    override fun execute(message: InBotMessage, user: DtoUser, params: HashMap<String, Any>): OutBotMessage {
        val pageInfo = params[MARKET_PAGE]?.convertTo(BotPageInfo::class) ?: BotPageInfo(0, false)

        val resources = marketService.getStorageResources(user, PageRequest.of(pageInfo.number, 3))
        return BotButtonMessage(
            if (resources.isEmpty) "В хранилище пусто" else "Список возможных товаров для продажи",
            resources.toKeyboard { InlineButton(it.resource.name, it.id.toString()) },
            null,
            user.getMessengerId()
        )
    }

    companion object {
        const val ID = "c53cc7d3-5d34-4e8d-accc-0cc47cfb8293"
    }
}