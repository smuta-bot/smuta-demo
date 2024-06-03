package ru.yan.core.processes.notification

import org.springframework.stereotype.Component
import ru.yan.core.bot.BotProcess
import ru.yan.core.bot.message.BotButtonMessage
import ru.yan.core.bot.message.InBotMessage
import ru.yan.core.bot.message.OutBotMessage
import ru.yan.core.getMessengerId
import ru.yan.database.model.smuta.dto.DtoUser

/**
 * Процесс для работы с уведомлениями
 */
@Component
class NotificationProcess: BotProcess() {
    override fun start(message: InBotMessage, user: DtoUser): OutBotMessage {
        return BotButtonMessage(
            "Здесь можно прочитать уведомления",
            null,
            listOf(listOf("Непрочитанные", "Все"), listOf("Назад")),
            user.getMessengerId()
        )
    }

    companion object {
        const val ID = "e34f0e0f-2428-4e12-b75a-a6dab73f0d4e"
    }
}