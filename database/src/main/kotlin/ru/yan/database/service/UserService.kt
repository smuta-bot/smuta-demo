package ru.yan.database.service

import ru.yan.database.model.nsi.types.MessengerType
import ru.yan.database.model.smuta.UserActivity
import ru.yan.database.model.smuta.dto.DtoUser

/**
 * Сервис для работы с сущностью "Пользователь"
 */
interface UserService {
    /**
     * Получить пользователя по его id в телеге или если его не существует то создать
     *
     * @param telegramId Id пользователя в телеге
     */
    fun getOrCreateTelegram(telegramId: Long): DtoUser

    /**
     * Получить пользователя по его id в вк или если его не существует то создать
     *
     * @param vkId Id пользователя в вк
     */
    fun getOrCreateVk(vkId: Long): DtoUser

    /**
     * Получить пользователей, которые не превысили лимит сообщений мессенджера
     *
     * @param ids Идентификаторы пользователей, из которых нужно выбирать
     * @param limit Лимит сообщений
     * @param messengerType Тип мессенджера
     */
    fun getOutOfMessengerLimit(ids: List<Long>, limit: Int, messengerType: MessengerType): List<DtoUser>

    /**
     * Получить пользователей, у которых есть не прочтенные уведомления
     */
    fun getToNotificate(): List<DtoUser>

    /**
     * Обновление данных об активности пользователя
     *
     * @param user Пользователь
     * @param activity Данные об активности
     */
    fun updateUserActivity(user: DtoUser, activity: UserActivity?): DtoUser

    /**
     * Обновить время последней активности пользователей
     *
     * @param ids Идентификаторы пользователей, время активности которых нужно обновить
     * @param messengerType Тип мессенджера
     */
    fun updateActivityTime(ids: List<Long>, messengerType: MessengerType): List<DtoUser>
}