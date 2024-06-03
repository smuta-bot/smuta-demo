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
import ru.yan.core.processes.buildings.tree.BUILDING_PRODUCTS_PAGE
import ru.yan.core.processes.buildings.tree.NSI_BUILDING_ID
import ru.yan.core.toKeyboard
import ru.yan.database.model.smuta.dto.DtoUser
import ru.yan.database.service.ProductionService
import java.util.*
import kotlin.collections.HashMap

@Component
class ChooseProductTask(
    val productionService: ProductionService
): BotTask {

    override fun execute(message: InBotMessage, user: DtoUser, params: HashMap<String, Any>): OutBotMessage {
        val chosenBuildingId = params[NSI_BUILDING_ID] as String
        val pageInfo = params[BUILDING_PRODUCTS_PAGE]?.convertTo(BotPageInfo::class) ?: BotPageInfo(0, false)

        val buildingProducts = productionService.getBuildingProducts(UUID.fromString(chosenBuildingId), PageRequest.of(pageInfo.number, 3))
        return BotButtonMessage(
            if (buildingProducts.isEmpty) "Нечего добавлять" else "Список возможной продукции для выпуска",
            buildingProducts.toKeyboard { InlineButton(it.resource.name, it.id.toString()) }.plus(
                listOf(listOf(InlineButton("Назад", "back")))
            ),
            null,
            user.getMessengerId()
        )
    }

    companion object {
        const val ID = "5aa2adf0-8633-4f7d-b366-3f07c7423a2e"
    }
}