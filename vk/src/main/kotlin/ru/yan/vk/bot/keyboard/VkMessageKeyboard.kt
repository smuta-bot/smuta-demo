package ru.yan.vk.bot.keyboard

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Описание клавиатуры в Vk chat bot
 */
@Serializable
data class VkMessageKeyboard (
    /**
     * true — клавиатура отображается внутри сообщения. false — показывает клавиатуру в диалоге, под полем ввода сообщения
     */
    val inline: Boolean = false,

    /**
     * Массив строк с массивом клавиш
     */
    val buttons: List<List<VkKeyboardButton>>,

    /**
     * Скрывать ли клавиатуру после нажатия на клавишу, отправляющую сообщение
     */
    @SerialName("one_time")
    val oneTime: Boolean = false
)
