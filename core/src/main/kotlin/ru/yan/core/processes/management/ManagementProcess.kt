package ru.yan.core.processes.management

import org.springframework.stereotype.Component
import ru.yan.core.bot.BotProcess
import ru.yan.core.bot.message.InBotMessage
import ru.yan.core.bot.message.BotButtonMessage
import ru.yan.core.bot.message.OutBotMessage
import ru.yan.core.getMessengerId
import ru.yan.database.model.smuta.dto.DtoUser

/**
 * Процесс для управления страной
 */
@Component
class ManagementProcess: BotProcess() {
    override fun start(message: InBotMessage, user: DtoUser): OutBotMessage {
        return BotButtonMessage(
            "Меню дополнительной информации и управления страной",
            null,
            listOf(listOf("Население"), listOf("Хранилище"), listOf("Назад")),
            user.getMessengerId()
        )
    }

    companion object {
        const val ID = "335c6191-8a33-4608-845b-9d50ea71cbd0"
    }
}