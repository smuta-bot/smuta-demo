package ru.yan.vk.bot.callback

/**
 * Описание события
 */
data class CallbackDto(
    /**
     * Тип события
     */
    val type: CallbackType,

    /**
     * Версия API, для которой сформировано событие
     */
    val version: String,

    /**
     * ID сообщества, в котором произошло событие
     */
    val groupId: Long,

    /**
     * Идентификатор события
     */
    val eventId: String,

    /**
     * Объект, инициировавший событие
     */
    val body: Any?,

    /**
     * Секретный ключ
     */
    val secret: String
)