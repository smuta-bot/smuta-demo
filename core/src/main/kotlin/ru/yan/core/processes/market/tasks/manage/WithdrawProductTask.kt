package ru.yan.core.processes.market.tasks.manage

import org.springframework.stereotype.Component
import ru.yan.core.bot.BotTask
import ru.yan.core.bot.message.InBotMessage
import ru.yan.core.bot.message.BotButtonMessage
import ru.yan.core.bot.message.InlineButton
import ru.yan.core.bot.message.OutBotMessage
import ru.yan.core.getMessengerId
import ru.yan.core.processes.market.tree.MARKET_PRODUCT_ID
import ru.yan.database.exceptions.NotFoundException
import ru.yan.database.model.smuta.dto.DtoUser
import ru.yan.database.service.MarketService
import java.util.*
import kotlin.collections.HashMap

@Component
class WithdrawProductTask(
    private val marketService: MarketService
): BotTask {

    override fun execute(message: InBotMessage, user: DtoUser, params: HashMap<String, Any>): OutBotMessage {
        val chosenMarketProductId = params[MARKET_PRODUCT_ID] as String

        return try {
            marketService.withdrawMarketProduct(UUID.fromString(chosenMarketProductId))

            BotButtonMessage(
                "Товар изъят с рынка",
                listOf(listOf(InlineButton("Понял", "got_it"))),
                null,
                user.getMessengerId()
            )
        } catch (ex: NotFoundException) {
            BotButtonMessage(
                "Товар не найден",
                listOf(listOf(InlineButton("Понял", "got_it"))),
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
        const val ID = "6f9fb530-d8a9-4950-9e7c-4b23cf7ba18f"
    }
}