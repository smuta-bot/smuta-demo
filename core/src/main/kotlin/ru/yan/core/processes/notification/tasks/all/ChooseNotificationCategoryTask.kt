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
import ru.yan.core.getMessengerId
import ru.yan.core.processes.notification.tree.NOTIFICATION_CATEGORY_PAGE
import ru.yan.core.toKeyboard
import ru.yan.database.model.smuta.dto.DtoUser
import ru.yan.database.service.NotificationService

@Component
class ChooseNotificationCategoryTask(
    private val notificationService: NotificationService
): BotTask {

    override fun execute(message: InBotMessage, user: DtoUser, params: HashMap<String, Any>): OutBotMessage {
        val pageInfo = params[NOTIFICATION_CATEGORY_PAGE]?.convertTo(BotPageInfo::class) ?: BotPageInfo(0, false)

        val notificationCategories = notificationService.getAllNotificationCategories(PageRequest.of(pageInfo.number, 3))
        return BotButtonMessage(
            "Выберите категорию уведомлений",
            notificationCategories.toKeyboard { InlineButton(it.name, it.id.toString()) }
                .plus(listOf(listOf(InlineButton("Назад", "back")))),
            null,
            user.getMessengerId(),
            if (pageInfo.editable) message.messageId else null
        )
    }

    companion object {
        const val ID = "44aed787-d961-4e1f-937a-e1de44d7b9d5"
    }
}