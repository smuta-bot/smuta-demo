package ru.yan.core.processes.notification.tasks.unread

import org.springframework.stereotype.Component
import ru.yan.core.bot.BotTask
import ru.yan.core.bot.message.BotButtonMessage
import ru.yan.core.bot.message.InBotMessage
import ru.yan.core.bot.message.InlineButton
import ru.yan.core.bot.message.OutBotMessage
import ru.yan.core.getMessengerId
import ru.yan.database.model.smuta.dto.DtoUser

@Component
class UnreadNotificationsTask: BotTask {

    override fun execute(message: InBotMessage, user: DtoUser, params: HashMap<String, Any>): OutBotMessage {
        return BotButtonMessage(
            "Работы с непрочитанными уведомлениями",
            listOf(
                listOf(InlineButton("Выбрать категорию", "category")),
                listOf(InlineButton("Показать все", "all")),
                listOf(InlineButton("Прочитать всё", "read_all"))
            ),
            null,
            user.getMessengerId()
        )
    }

    companion object {
        const val ID = "4a3e2696-8f48-4cc7-90f2-1c18e8f00d92"
    }
}