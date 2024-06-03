package ru.yan.core.processes.market.tasks.manage

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
class ChooseOwnMarketProductTask(
    private val marketService: MarketService
): BotTask {

    override fun execute(message: InBotMessage, user: DtoUser, params: HashMap<String, Any>): OutBotMessage {
        val pageInfo = params[MARKET_PAGE]?.convertTo(BotPageInfo::class) ?: BotPageInfo(0, false)

        val products = marketService.getMarketProductByUser(user, PageRequest.of(pageInfo.number, 3))
        return BotButtonMessage(
            if (products.isEmpty) "На рынке нет ваших товаров" else "Список ваших товаров",
            products.toKeyboard { InlineButton(it.resource.name, it.id.toString()) },
            null,
            user.getMessengerId()
        )
    }

    companion object {
        const val ID = "d6733552-4e4a-4036-b2a0-947771d7bb35"
    }
}