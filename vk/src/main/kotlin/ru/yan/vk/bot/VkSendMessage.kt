package ru.yan.vk.bot

import ru.yan.vk.bot.keyboard.VkMessageKeyboard

/**
 * Описание обычного сообщения
 */
class VkSendMessage(
    userId: Long,
    text: String,
    keyboard: VkMessageKeyboard?
): VkMessage(userId, text, keyboard) {

    override val url = "https://api.vk.com/method/messages.send"
}