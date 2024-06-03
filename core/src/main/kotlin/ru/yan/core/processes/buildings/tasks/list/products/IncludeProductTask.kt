package ru.yan.core.processes.buildings.tasks.list.products

import org.springframework.stereotype.Component
import ru.yan.core.bot.BotTask
import ru.yan.core.bot.message.InBotMessage
import ru.yan.core.bot.message.BotButtonMessage
import ru.yan.core.bot.message.InlineButton
import ru.yan.core.bot.message.OutBotMessage
import ru.yan.core.getMessengerId
import ru.yan.core.processes.buildings.tree.BUILDING_PRODUCT_ID
import ru.yan.core.processes.buildings.tree.NSI_BUILDING_ID
import ru.yan.database.exceptions.AlreadyExistsException
import ru.yan.database.model.smuta.dto.DtoUser
import ru.yan.database.service.ProductionService
import java.util.*
import kotlin.collections.HashMap

@Component
class IncludeProductTask(
    val productionService: ProductionService
): BotTask {

    override fun execute(message: InBotMessage, user: DtoUser, params: HashMap<String, Any>): OutBotMessage {
        val chosenBuildingProductId = params[BUILDING_PRODUCT_ID] as String
        val chosenNsiBuildingId = params[NSI_BUILDING_ID] as String

        return try {
            productionService.addProductToProduction(user, UUID.fromString(chosenNsiBuildingId), UUID.fromString(chosenBuildingProductId))

            BotButtonMessage(
                "Продукт допущен к производству",
                listOf(listOf(InlineButton("Ok", "back"))),
                null,
                user.getMessengerId()
            )
        } catch (ex: AlreadyExistsException) {
            BotButtonMessage(
                "Продукт уже допущен к производству",
                listOf(listOf(InlineButton("Ok", "back"))),
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
        const val ID = "022bfa68-a455-416e-a6c8-564f8823147e"
    }
}