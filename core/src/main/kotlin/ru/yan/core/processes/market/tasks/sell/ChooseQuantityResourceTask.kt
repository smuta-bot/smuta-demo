package ru.yan.core.processes.market.tasks.sell

import org.springframework.stereotype.Component
import ru.yan.core.bot.BotTask
import ru.yan.core.bot.message.InBotMessage
import ru.yan.core.bot.message.BotButtonMessage
import ru.yan.core.bot.message.InlineButton
import ru.yan.core.bot.message.OutBotMessage
import ru.yan.core.getMessengerId
import ru.yan.core.processes.market.tree.STORAGE_RESOURCE_ID
import ru.yan.database.model.smuta.dto.DtoUser
import ru.yan.database.service.MarketService
import java.util.*
import kotlin.collections.HashMap

@Component
class ChooseQuantityResourceTask(
    private val marketService: MarketService
): BotTask {

    override fun execute(message: InBotMessage, user: DtoUser, params: HashMap<String, Any>): OutBotMessage {
        val chosenStorageResourceId = params[STORAGE_RESOURCE_ID] as String

        val sr = marketService.getStorageResource(UUID.fromString(chosenStorageResourceId))
        return BotButtonMessage(
            "Введите кол-во ресурса для продажи. Это должно быть целое число. Текущее кол-во товара: ${sr.quantity}",
            listOf(
                listOf(InlineButton("Назад", "back"))
            ),
            null,
            user.getMessengerId()
        )
    }

    companion object {
        const val ID = "4648b091-26a1-41d3-97ef-ff0d0d2e0b29"
    }
}