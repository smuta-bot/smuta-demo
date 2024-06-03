package ru.yan.database.repository.smuta.custom

import ru.yan.database.model.smuta.User

interface CustomUserRepository {
    /**
     * Найти пользователей из списка, которым МОЖНО отправить сообщение с учетом лимитов телеги
     *
     * @param telegramIds Идентификаторы пользователей телеграмма, которым НУЖНО отправить сообщения
     * @param limit Лимит сообщений
     */
    fun findOutOfTelegramLimit(telegramIds: List<Long>, limit: Int): List<User>

    /**
     * Найти пользователей из списка, которым можно отправить сообщение с учетом лимитов контакта
     *
     * @param vkIds Идентификаторы пользователей контакта, которым НУЖНО отправить сообщения
     * @param limit Лимит сообщений
     */
    fun findOutOfVkLimit(vkIds: List<Long>, limit: Int): List<User>
}