package ru.yan.core.processes.management.tasks.storage

import org.springframework.stereotype.Component
import ru.yan.core.bot.BotTask
import ru.yan.core.bot.message.InBotMessage
import ru.yan.core.bot.message.BotTextMessage
import ru.yan.core.bot.message.OutBotMessage
import ru.yan.core.getMessengerId
import ru.yan.database.model.smuta.dto.DtoUser
import ru.yan.database.service.StorageService
import kotlin.collections.HashMap

@Component
class StorageTask(
    private val storageService: StorageService
): BotTask {
    override fun execute(message: InBotMessage, user: DtoUser, params: HashMap<String, Any>): OutBotMessage {
        return try {
            val resources = storageService.getStorageResources(user)

            BotTextMessage(
                resources.fold("") { a, b -> a + "${b.resource.name} ${b.quantity}\n"},
                user.getMessengerId()
            )
        } catch (ex: Throwable) {
            BotTextMessage(
                "Что то пошло не так",
                user.getMessengerId()
            )
        }
    }

    companion object {
        const val ID = "64453bb7-13e5-4651-901c-3fb64f37e6fe"
    }
}