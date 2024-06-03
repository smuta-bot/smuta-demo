package ru.yan.database.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import ru.yan.database.exceptions.NotFoundException
import ru.yan.database.model.nsi.dto.DtoNotificationCategory
import ru.yan.database.model.smuta.dto.DtoNotification
import ru.yan.database.model.smuta.dto.DtoNotificationToSend
import ru.yan.database.model.smuta.dto.DtoUser
import java.util.UUID

/**
 * Сервис для работы с уведомлениями
 */
interface NotificationService {
    /**
     * Получить все категории уведомлений
     *
     * @param pageRequest Номер страницы и кол-во элементов на ней
     */
    fun getAllNotificationCategories(pageRequest: PageRequest): Page<DtoNotificationCategory>

    /**
     * Получить все непрочитанные и важные уведомления
     */
    fun getUnreadImportantNotificationsToSend(): List<DtoNotificationToSend>

    /**
     * Получить все категории непрочитанных уведомлений
     *
     * @param user Пользователь, запросивший информацию
     * @param pageRequest Номер страницы и кол-во элементов на ней
     *
     * @throws NotFoundException
     */
    fun getUnreadNotificationCategories(user: DtoUser, pageRequest: PageRequest): Page<DtoNotificationCategory>

    /**
     * Получить все уведомления
     *
     * @param user Пользователь, запросивший информацию
     * @param notificationCategoryId Id категории уведомления или null что бы не учитывать категорию
     * @param pageRequest Номер страницы и кол-во элементов на ней
     */
    fun getAllNotifications(user: DtoUser, notificationCategoryId: UUID?, pageRequest: PageRequest): Page<DtoNotification>

    /**
     * Получить все непрочитанные уведомления
     *
     * @param user Пользователь, запросивший информацию
     * @param notificationCategoryId Id категории уведомления или null что бы не учитывать категорию
     * @param pageRequest Номер страницы и кол-во элементов на ней
     */
    fun getUnreadNotifications(user: DtoUser, notificationCategoryId: UUID?, pageRequest: PageRequest): Page<DtoNotification>

    /**
     * Пометить все сообщения как прочитанные
     *
     *  @param user Пользователь, читающий уведомления
     */
    fun redAllNotifications(user: DtoUser)
}