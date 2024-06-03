package ru.yan.telegram.bot

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow
import ru.yan.core.bot.message.BotButtonMessage
import ru.yan.core.bot.message.BotMessageConverter
import ru.yan.core.bot.message.BotTextMessage
import java.io.Serializable

@Component
class TelegramBotMessageConverter: BotMessageConverter<BotApiMethod<Serializable>>() {

    override fun convertButtonMessage(message: BotButtonMessage): BotApiMethod<Serializable> {
        return message.messageId?.let {
            EditMessageText().apply {
                chatId = message.userId.toString()
                messageId = it.toInt()
                text = message.text
                replyMarkup = message.inlineKeyboard?.let { inlineKeyboard ->
                    InlineKeyboardMarkup().apply {
                        keyboard = inlineKeyboard.map { row ->
                            row.map { button -> InlineKeyboardButton(button.text).apply { callbackData = button.callbackData } }
                        }
                    }
                }
            }
        } ?: SendMessage().apply {
            chatId = message.userId.toString()
            text = message.text
            replyMarkup = message.keyboard?.let { keyboard ->
                ReplyKeyboardMarkup().apply {
                    resizeKeyboard = true
                    this.keyboard = keyboard.map { row ->
                        KeyboardRow(
                            row.map { button -> KeyboardButton(button) }
                        )
                    }
                }
            } ?: message.inlineKeyboard?.let { inlineKeyboard ->
                InlineKeyboardMarkup().apply {
                    keyboard = inlineKeyboard.map { row ->
                        row.map { button -> InlineKeyboardButton(button.text).apply { callbackData = button.callbackData } }
                    }
                }
            }
        } as BotApiMethod<Serializable>
    }

    override fun convertTextMessage(message: BotTextMessage): BotApiMethod<Serializable> {
        return SendMessage().apply {
            chatId = message.userId.toString()
            text = message.text
        } as BotApiMethod<Serializable>
    }
}