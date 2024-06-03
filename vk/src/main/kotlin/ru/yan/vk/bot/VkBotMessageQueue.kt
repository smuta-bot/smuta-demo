package ru.yan.vk.bot

import org.springframework.stereotype.Component
import ru.yan.core.bot.message.BotMessageConverter
import ru.yan.core.bot.request.BotMessageQueue
import ru.yan.database.model.nsi.types.MessengerType
import ru.yan.database.service.UserService
import java.io.Serializable

@Component
class VkBotMessageQueue(
    private val userService: UserService,
    override val converter: BotMessageConverter<VkMessage>
): BotMessageQueue<VkMessage>() {

    override val messagesPerSecond: Int = 20

    override fun findOutOfMessengerLimit(ids: List<Long>, limit: Int) =
        userService.getOutOfMessengerLimit(ids, limit, MessengerType.Vk).map {
            it.vkId!!
        }

    override fun updateActivityTime(ids: List<Long>) =
        userService.updateActivityTime(ids, MessengerType.Vk)
}