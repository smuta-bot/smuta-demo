package ru.yan.core.processes.buildings.tasks.list.products

import org.springframework.stereotype.Component
import ru.yan.core.bot.BotTask
import ru.yan.core.bot.message.InBotMessage
import ru.yan.core.bot.message.BotButtonMessage
import ru.yan.core.bot.message.InlineButton
import ru.yan.core.bot.message.OutBotMessage
import ru.yan.core.description
import ru.yan.core.getMessengerId
import ru.yan.core.processes.buildings.tree.BUILDING_PRODUCT_ID
import ru.yan.database.model.smuta.dto.DtoUser
import ru.yan.database.service.ProductionService
import java.util.*
import kotlin.collections.HashMap

@Component
class DescribeProductTask(
    val productionService: ProductionService
): BotTask {

    override fun execute(message: InBotMessage, user: DtoUser, params: HashMap<String, Any>): OutBotMessage {
        val chosenBuildingProductId = params[BUILDING_PRODUCT_ID] as String

        return try {
            val product = productionService.getBuildingProduct(UUID.fromString(chosenBuildingProductId))

            BotButtonMessage(
                product.description(),
                listOf(
                    listOf(InlineButton("Допустить к производству", "include")),
                    listOf(InlineButton("Назад", "back"))
                ),
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
        const val ID = "c4db0063-922a-484e-acab-abe655f02b0d"
    }
}