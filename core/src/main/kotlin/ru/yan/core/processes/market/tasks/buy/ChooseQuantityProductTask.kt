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
class ChooseQuantityProductTask(
    private val marketService: MarketService
): BotTask {

    override fun execute(message: InBotMessage, user: DtoUser, params: HashMap<String, Any>): OutBotMessage {
        val chosenMarketProductId = params[MARKET_PRODUCT_ID] as String

        val mp = marketService.getMarketProduct(UUID.fromString(chosenMarketProductId))
        return BotButtonMessage(
            "Введите кол-во ресурса для покупки. Это должно быть целое число.\n\n" +
                    "Текущее кол-во товара: ${mp.quantity}\n" +
                    "Цена за единицу: ${mp.price}\n" +
                    "Суммарная цена: ${mp.quantity * mp.price}",
            listOf(
                listOf(InlineButton("Купить все", "buy_all")),
                listOf(InlineButton("Назад", "back"))
            ),
            null,
            user.getMessengerId()
        )
    }

    companion object {
        const val ID = "ad3ea603-6597-4278-913e-86aad04ebbbf"
    }
}