package ru.yan.core.processes.notification.tasks.all

import org.springframework.stereotype.Component
import ru.yan.core.bot.BotTask
import ru.yan.core.bot.message.BotButtonMessage
import ru.yan.core.bot.message.InBotMessage
import ru.yan.core.bot.message.InlineButton
import ru.yan.core.bot.message.OutBotMessage
import ru.yan.core.getMessengerId
import ru.yan.database.model.smuta.dto.DtoUser

@Component
class AllNotificationsTask: BotTask {

    override fun execute(message: InBotMessage, user: DtoUser, params: HashMap<String, Any>): OutBotMessage {
        return BotButtonMessage(
            "Работа со всеми уведомлениями",
            listOf(
                listOf(InlineButton("Выбрать категорию", "category")),
                listOf(InlineButton("Показать все", "all"))
            ),
            null,
            user.getMessengerId()
        )
    }

    companion object {
        const val ID = "36be69dd-2142-4283-9db1-39dbb99bc59e"
    }
}