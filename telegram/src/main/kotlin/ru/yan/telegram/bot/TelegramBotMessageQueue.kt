package ru.yan.telegram.bot

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import ru.yan.core.bot.message.BotMessageConverter
import ru.yan.core.bot.request.BotMessageQueue
import ru.yan.database.model.nsi.types.MessengerType
import ru.yan.database.service.UserService
import java.io.Serializable

@Component
class TelegramBotMessageQueue(
    private val userService: UserService,
    override val converter: BotMessageConverter<BotApiMethod<Serializable>>
) : BotMessageQueue<BotApiMethod<Serializable>>() {

    override val messagesPerSecond: Int = 30

    override fun findOutOfMessengerLimit(ids: List<Long>, limit: Int) =
        userService.getOutOfMessengerLimit(ids, limit, MessengerType.Telegram).map {
            it.telegramId!!
        }

    override fun updateActivityTime(ids: List<Long>) =
        userService.updateActivityTime(ids, MessengerType.Telegram)
}