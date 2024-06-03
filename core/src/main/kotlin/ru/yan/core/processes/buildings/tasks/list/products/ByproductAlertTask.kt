package ru.yan.core.processes.buildings.tasks.list.products

import org.springframework.stereotype.Component
import ru.yan.core.bot.BotTask
import ru.yan.core.bot.message.InBotMessage
import ru.yan.core.bot.message.BotButtonMessage
import ru.yan.core.bot.message.InlineButton
import ru.yan.core.bot.message.OutBotMessage
import ru.yan.core.getMessengerId
import ru.yan.database.model.smuta.dto.DtoUser
import kotlin.collections.HashMap

@Component
class ByproductAlertTask: BotTask {

    override fun execute(message: InBotMessage, user: DtoUser, params: HashMap<String, Any>): OutBotMessage {

        return BotButtonMessage(
            "Это побочный продукт при производстве основного продукта. Для его исключения вам следует изъять из производства основной продукт",
            listOf(
                listOf(InlineButton("Понял", "ok"))
            ),
            null,
            user.getMessengerId(),
            null
        )
    }

    companion object {
        const val ID = "12f3755d-e310-4532-880c-8f3be5a05f1c"
    }
}