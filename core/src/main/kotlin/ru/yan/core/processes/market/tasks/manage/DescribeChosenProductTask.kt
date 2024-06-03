package ru.yan.core.processes.market.tasks.manage

import org.springframework.stereotype.Component
import ru.yan.core.bot.BotTask
import ru.yan.core.bot.message.InBotMessage
import ru.yan.core.bot.message.BotButtonMessage
import ru.yan.core.bot.message.InlineButton
import ru.yan.core.bot.message.OutBotMessage
import ru.yan.core.getMessengerId
import ru.yan.core.processes.market.tree.MARKET_PRODUCT_ID
import ru.yan.database.model.smuta.dto.DtoUser
import ru.yan.database.service.MarketService
import java.util.*
import kotlin.collections.HashMap

@Component
class DescribeChosenProductTask(
    private val marketService: MarketService
): BotTask {

    override fun execute(message: InBotMessage, user: DtoUser, params: HashMap<String, Any>): OutBotMessage {
        val chosenMarketProductId = params[MARKET_PRODUCT_ID] as String

        val mp = marketService.getMarketProduct(UUID.fromString(chosenMarketProductId))
        return BotButtonMessage(
            "Текущее кол-во товара: ${mp.quantity}\n" +
                 "Цена за единицу: ${mp.price}\n" +
                 "Суммарная цена: ${mp.quantity * mp.price}",
            listOf(
                listOf(InlineButton("Изъять с продажи", "withdraw")),
                listOf(InlineButton("Назад", "back"))
            ),
            null,
            user.getMessengerId()
        )
    }

    companion object {
        const val ID = "cc2c39a9-1a91-4625-b1c9-14a546df5b5f"
    }
}