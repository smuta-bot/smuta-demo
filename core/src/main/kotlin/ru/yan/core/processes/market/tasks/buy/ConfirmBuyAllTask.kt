package ru.yan.core.processes.market.tasks.buy

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
class ConfirmBuyAllTask(
    private val marketService: MarketService
): BotTask {

    override fun execute(message: InBotMessage, user: DtoUser, params: HashMap<String, Any>): OutBotMessage {
        val chosenMarketProductId = params[MARKET_PRODUCT_ID] as String

        val marketProduct = marketService.getMarketProduct(UUID.fromString(chosenMarketProductId))
        return BotButtonMessage(
            "Вы уверены что хотите купить весь товар(${marketProduct.quantity}) за ${marketProduct.quantity * marketProduct.price}?",
            listOf(
                listOf(InlineButton("Да", "confirm")),
                listOf(InlineButton("Нет", "not_confirm"))
            ),
            null,
            user.getMessengerId()
        )
    }

    companion object {
        const val ID = "fbe3600d-f06a-4bb1-a3b0-a6d08b187339"
    }
}