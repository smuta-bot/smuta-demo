package ru.yan.core.processes.market.tasks.sell

import org.springframework.stereotype.Component
import ru.yan.core.bot.BotTask
import ru.yan.core.bot.message.InBotMessage
import ru.yan.core.bot.message.BotButtonMessage
import ru.yan.core.bot.message.InlineButton
import ru.yan.core.bot.message.OutBotMessage
import ru.yan.core.getMessengerId
import ru.yan.core.processes.market.tree.STORAGE_RESOURCE_ID
import ru.yan.core.processes.market.tree.STORAGE_RESOURCE_PRICE
import ru.yan.core.processes.market.tree.STORAGE_RESOURCE_QUANTITY
import ru.yan.database.exceptions.AlreadyExistsException
import ru.yan.database.exceptions.NotEnoughMoneyException
import ru.yan.database.exceptions.NotEnoughResourceException
import ru.yan.database.model.smuta.dto.DtoUser
import ru.yan.database.service.MarketService
import java.util.*
import kotlin.collections.HashMap

@Component
class SellStorageResourceTask(
    private val marketService: MarketService
): BotTask {

    override fun execute(message: InBotMessage, user: DtoUser, params: HashMap<String, Any>): OutBotMessage {
        val chosenStorageResourceId = params[STORAGE_RESOURCE_ID] as String
        val quantity = (params[STORAGE_RESOURCE_QUANTITY] as Number).toLong()
        val price = params[STORAGE_RESOURCE_PRICE] as Double

        return try {
            marketService.sellStorageResource(
                UUID.fromString(chosenStorageResourceId),
                quantity,
                price
            )

            BotButtonMessage(
                "Ресурс выставлен на продажу",
                listOf(listOf(InlineButton("Понял", "got_it"))),
                null,
                user.getMessengerId()
            )
        } catch (ex: NotEnoughResourceException) {
            BotButtonMessage(
                "Недостаточно ресурса в хранилище",
                listOf(listOf(InlineButton("Назад", "back"))),
                null,
                user.getMessengerId()
            )
        } catch (ex: NotEnoughMoneyException) {
            BotButtonMessage(
                "Недостаточно денег для уплаты налога",
                listOf(listOf(InlineButton("Назад", "back"))),
                null,
                user.getMessengerId()
            )
        } catch (ex: AlreadyExistsException) {
            BotButtonMessage(
                "Достигнут лимит товаров на рынке(максимум 3)",
                listOf(listOf(InlineButton("Назад", "got_it"))),
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
        const val ID = "74ea7e30-4f05-47fb-a95c-870210d3e1c6"
    }
}