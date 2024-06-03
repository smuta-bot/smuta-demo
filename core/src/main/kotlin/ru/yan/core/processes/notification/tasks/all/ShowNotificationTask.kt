package ru.yan.core.processes.notification.tasks.all

import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component
import ru.yan.core.bot.BotPageInfo
import ru.yan.core.bot.BotTask
import ru.yan.core.bot.message.BotButtonMessage
import ru.yan.core.bot.message.InBotMessage
import ru.yan.core.bot.message.InlineButton
import ru.yan.core.bot.message.OutBotMessage
import ru.yan.core.convertTo
import ru.yan.core.getControlButtons
import ru.yan.core.getMessengerId
import ru.yan.core.processes.notification.tree.NOTIFICATION_CATEGORY_ID
import ru.yan.core.processes.notification.tree.NOTIFICATION_PAGE
import ru.yan.database.model.smuta.dto.DtoUser
import ru.yan.database.service.NotificationService
import java.util.*
import kotlin.collections.HashMap

@Component
class ShowNotificationTask(
    private val notificationService: NotificationService
): BotTask {

    override fun execute(message: InBotMessage, user: DtoUser, params: HashMap<String, Any>): OutBotMessage {
        val chosenNotificationCategoryId = params[NOTIFICATION_CATEGORY_ID] as? String
        val pageInfo = params[NOTIFICATION_PAGE]?.convertTo(BotPageInfo::class) ?: BotPageInfo(0, false)

        val notification = notificationService.getAllNotifications(
            user,
            chosenNotificationCategoryId?.let { UUID.fromString(chosenNotificationCategoryId) },
            PageRequest.of(pageInfo.number, 1)
        )

        return BotButtonMessage(
            if (notification.isEmpty) "Уведомлений нет!" else "Уведомление ${pageInfo.number + 1} \n\n${notification.first().message}",
            (notification.getControlButtons() ?: listOf())
                .plus(listOf(listOf(InlineButton("Назад", "back")))),
            null,
            user.getMessengerId(),
            if (pageInfo.editable) message.messageId else null
        )
    }

    companion object {
        const val ID = "540abc91-fd11-4ba9-af9c-4e4ac552e66c"
    }
}