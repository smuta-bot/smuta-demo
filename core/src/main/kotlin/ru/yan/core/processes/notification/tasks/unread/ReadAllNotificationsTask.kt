package ru.yan.core.processes.notification.tasks.unread

import org.springframework.stereotype.Component
import ru.yan.core.bot.BotTask
import ru.yan.core.bot.message.BotButtonMessage
import ru.yan.core.bot.message.InBotMessage
import ru.yan.core.bot.message.InlineButton
import ru.yan.core.bot.message.OutBotMessage
import ru.yan.core.getMessengerId
import ru.yan.database.model.smuta.dto.DtoUser
import ru.yan.database.service.NotificationService

@Component
class ReadAllNotificationsTask(
    private val notificationService: NotificationService
): BotTask {

    override fun execute(message: InBotMessage, user: DtoUser, params: HashMap<String, Any>): OutBotMessage {
        notificationService.redAllNotifications(user)

        return BotButtonMessage(
            "Все уведомления помечены как прочитанные",
            listOf(listOf(InlineButton("Назад", "back"))),
            null,
            user.getMessengerId()
        )
    }

    companion object {
        const val ID = "55cc3a41-f35a-49c3-a6d3-1b948f6e5912"
    }
}