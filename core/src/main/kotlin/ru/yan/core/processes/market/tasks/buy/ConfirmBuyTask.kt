package ru.yan.core.processes.market.tasks.buy

import org.springframework.stereotype.Component
import ru.yan.core.bot.BotTask
import ru.yan.core.bot.message.InBotMessage
import ru.yan.core.bot.message.BotButtonMessage
import ru.yan.core.bot.message.InlineButton
import ru.yan.core.bot.message.OutBotMessage
import ru.yan.core.getMessengerId
import ru.yan.core.processes.market.tree.MARKET_PRODUCT_ID
import ru.yan.core.processes.market.tree.MARKET_PRODUCT_QUANTITY
import ru.yan.database.model.smuta.dto.DtoUser
import ru.yan.database.service.MarketService
import java.util.*
import kotlin.collections.HashMap

@Component
class ConfirmBuyTask(
    private val marketService: MarketService
): BotTask {

    override fun execute(message: InBotMessage, user: DtoUser, params: HashMap<String, Any>): OutBotMessage {
        val chosenMarketProductId = params[MARKET_PRODUCT_ID] as String
        val quantity = (params[MARKET_PRODUCT_QUANTITY] as Number).toLong()

        val marketProduct = marketService.getMarketProduct(UUID.fromString(chosenMarketProductId))
        return BotButtonMessage(
            "Вы уверены что хотите купить $quantity товара за ${quantity * marketProduct.price}?",
            listOf(
                listOf(InlineButton("Да", "confirm")),
                listOf(InlineButton("Нет", "not_confirm"))
            ),
            null,
            user.getMessengerId()
        )
    }

    companion object {
        const val ID = "d4e1fcae-73c7-4a1e-81c2-643be7a0b4dd"
    }
}