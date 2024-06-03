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
import ru.yan.database.exceptions.NotEnoughMoneyException
import ru.yan.database.exceptions.NotEnoughResourceException
import ru.yan.database.model.smuta.dto.DtoUser
import ru.yan.database.service.MarketService
import java.util.*
import kotlin.collections.HashMap

@Component
class BuyMarketProductTask(
    private val marketService: MarketService
): BotTask {

    override fun execute(message: InBotMessage, user: DtoUser, params: HashMap<String, Any>): OutBotMessage {
        val chosenMarketProductId = params[MARKET_PRODUCT_ID] as String
        val chosenMarketProductQuantity = (params[MARKET_PRODUCT_QUANTITY] as Number).toLong()

        return try {
            marketService.buyMarketProduct(
                user,
                UUID.fromString(chosenMarketProductId),
                chosenMarketProductQuantity
            )

            BotButtonMessage(
                "Товар куплен",
                listOf(listOf(InlineButton("Понял", "got_it"))),
                null,
                user.getMessengerId()
            )
        } catch (ex: NotEnoughResourceException) {
            BotButtonMessage(
                "Недостаточно ресурса на рынке. Возможно он был куплен",
                listOf(listOf(InlineButton("Назад", "back"))),
                null,
                user.getMessengerId()
            )
        } catch (ex: NotEnoughMoneyException) {
            BotButtonMessage(
                "Недостаточно денег для покупки",
                listOf(listOf(InlineButton("Назад", "back"))),
                null,
                user.getMessengerId()
            )
        } catch (ex: Throwable) {
            BotButtonMessage(
                "Что то пошло не так",
                listOf(listOf(InlineButton("Назад", "back"))),
                null,
                user.getMessengerId()
            )
        }
    }

    companion object {
        const val ID = "18b0dfd6-d84a-469e-9082-903426e8b9b6"
    }
}