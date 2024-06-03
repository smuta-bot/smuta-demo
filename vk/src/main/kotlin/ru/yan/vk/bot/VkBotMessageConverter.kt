package ru.yan.vk.bot

import org.springframework.stereotype.Component
import ru.yan.core.bot.message.BotButtonMessage
import ru.yan.core.bot.message.BotMessageConverter
import ru.yan.core.bot.message.BotTextMessage
import ru.yan.vk.bot.keyboard.*

@Component
class VkBotMessageConverter: BotMessageConverter<VkMessage>() {

    override fun convertButtonMessage(message: BotButtonMessage): VkMessage {
        val keyboard = VkMessageKeyboard(
            message.keyboard == null,
            message.keyboard?.let { keyboard ->
                keyboard.map { row ->
                    row.map {
                        VkKeyboardButton(
                            VkButtonAction(
                                it,
                                ButtonActionType.Text,
                                VkActionPayload(it)
                            )
                        )
                    }
                }
            } ?: message.inlineKeyboard!!.let { inlineKeyboard ->
                inlineKeyboard.map { row ->
                    row.map {
                        VkKeyboardButton(
                            VkButtonAction(
                                it.text,
                                ButtonActionType.Callback,
                                VkActionPayload(it.callbackData)
                            )
                        )
                    }
                }
            }
        )

        return message.messageId?.let { messageId ->
            VkEditMessage(
                messageId,
                message.userId,
                message.text,
                keyboard
            )
        } ?: VkSendMessage(
            message.userId,
            message.text,
            keyboard
        )
    }

    override fun convertTextMessage(message: BotTextMessage): VkMessage {
        return VkSendMessage(
            message.userId,
            message.text,
            null
        )
    }
}