package ru.yan.vk.bot

import org.springframework.util.MultiValueMap
import ru.yan.vk.bot.keyboard.VkMessageKeyboard

/**
 * Описание сообщения для редактирования
 */
class VkEditMessage(
    private val messageId: Long,
    userId: Long,
    text: String,
    keyboard: VkMessageKeyboard?
): VkMessage(userId, text, keyboard) {

    override val url = "https://api.vk.com/method/messages.edit"

    override fun toParams(): MultiValueMap<String, String> {
        val params = super.toParams()

        return params.apply {
            add("message_id", messageId.toString())
        }
    }
}