package ru.yan.core.processes.market.tasks.sell

import org.springframework.stereotype.Component
import ru.yan.core.bot.BotTask
import ru.yan.core.bot.message.InBotMessage
import ru.yan.core.bot.message.BotButtonMessage
import ru.yan.core.bot.message.InlineButton
import ru.yan.core.bot.message.OutBotMessage
import ru.yan.core.getMessengerId
import ru.yan.core.processes.market.tree.STORAGE_RESOURCE_PRICE
import ru.yan.core.processes.market.tree.STORAGE_RESOURCE_QUANTITY
import ru.yan.database.model.smuta.dto.DtoUser

@Component
class ConfirmSaleTask: BotTask {

    override fun execute(message: InBotMessage, user: DtoUser, params: HashMap<String, Any>): OutBotMessage {
        val quantity = (params[STORAGE_RESOURCE_QUANTITY] as Number).toLong()
        val price = params[STORAGE_RESOURCE_PRICE] as Double

        return BotButtonMessage(
            "Вы уверены что хотите продать $quantity ресурса за ${quantity * price} золота суммарно или $price золота за единицу. " +
                    "За выставление на продажу будет взыматься налог в размере 10% или ${quantity * price * 0.1}",
            listOf(
                listOf(InlineButton("Да", "confirm")),
                listOf(InlineButton("Нет", "not_confirm"))
            ),
            null,
            user.getMessengerId()
        )
    }

    companion object {
        const val ID = "35327573-22d3-480e-967d-71ceab3c0e11"
    }
}