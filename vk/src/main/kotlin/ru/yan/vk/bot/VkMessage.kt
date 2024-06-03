package ru.yan.vk.bot

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import ru.yan.vk.bot.keyboard.VkMessageKeyboard

/**
 * Описание сообщения для отправки
 */
abstract class VkMessage(
    private val userId: Long,
    private val text: String,
    private val keyboard: VkMessageKeyboard?
) {
    abstract val url: String

    open fun toParams(): MultiValueMap<String, String> {
        return LinkedMultiValueMap<String, String>().apply {
            add("message", text)
            add("peer_id", userId.toString())
            keyboard?.let {
                add("keyboard", Json { encodeDefaults = true }.encodeToString(it))
            }
        }
    }
}