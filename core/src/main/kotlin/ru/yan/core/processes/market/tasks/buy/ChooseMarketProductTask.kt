package ru.yan.core.processes.market.tasks.buy

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
class ChooseMarketProductTask(
    private val marketService: MarketService
): BotTask {

    override fun execute(message: InBotMessage, user: DtoUser, params: HashMap<String, Any>): OutBotMessage {
        val pageInfo = params[MARKET_PAGE]?.convertTo(BotPageInfo::class) ?: BotPageInfo(0, false)

        //TODO: Нужно отображать пользовательские ресурсы с пометкой, а не скрывать их
        val products = marketService.getMarketProductsExcludeUser(user, PageRequest.of(pageInfo.number, 3))
        return BotButtonMessage(
            if (products.isEmpty) "На рынке нет товаров" else "Список возможных товаров для покупки",
            products.toKeyboard { InlineButton(it.resource.name, it.id.toString()) },
            null,
            user.getMessengerId()
        )
    }

    companion object {
        const val ID = "f6c53c75-5c98-4a1f-b477-e5feff9b8e67"
    }
}