package ru.yan.core.processes.buildings.tasks.list.products

import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component
import ru.yan.core.bot.BotPageInfo
import ru.yan.core.bot.BotTask
import ru.yan.core.bot.message.InBotMessage
import ru.yan.core.bot.message.BotButtonMessage
import ru.yan.core.bot.message.InlineButton
import ru.yan.core.bot.message.OutBotMessage
import ru.yan.core.convertTo
import ru.yan.core.getMessengerId
import ru.yan.core.processes.buildings.tree.NSI_BUILDING_ID
import ru.yan.core.processes.buildings.tree.PRODUCTS_PAGE
import ru.yan.core.toKeyboard
import ru.yan.database.model.smuta.dto.DtoUser
import ru.yan.database.service.ProductionService
import java.util.UUID

@Component
class BuildingProductsTask(
    val productionService: ProductionService
): BotTask {

    override fun execute(message: InBotMessage, user: DtoUser, params: HashMap<String, Any>): OutBotMessage {
        val pageInfo = params[PRODUCTS_PAGE]?.convertTo(BotPageInfo::class) ?: BotPageInfo(0, false)
        val chosenNsiBuildingId = params[NSI_BUILDING_ID] as String

        val products = productionService.getProducts(user, UUID.fromString(chosenNsiBuildingId), PageRequest.of(pageInfo.number, 3))
        return BotButtonMessage(
            if (products.isEmpty) "Здание ничего не производит" else "Список производимой продукции",
            products.toKeyboard {
                InlineButton(it.buildingProduct.resource.name, it.buildingProduct.id.toString())
            }.plus(
                listOf(
                    listOf(InlineButton("Добавить", "add")),
                    listOf(InlineButton("Назад", "back"))
                )
            ),
            null,
            user.getMessengerId(),
            if (pageInfo.editable) message.messageId else null
        )
    }

    companion object {
        const val ID = "1e0127cf-73cb-46b7-ae10-0e3ec10d1284"
    }
}