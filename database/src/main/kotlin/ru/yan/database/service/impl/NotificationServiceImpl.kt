package ru.yan.database.service.impl

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.yan.database.exceptions.notFoundBy
import ru.yan.database.model.nsi.dto.DtoNotificationCategory
import ru.yan.database.model.nsi.dto.toDto
import ru.yan.database.model.smuta.Country
import ru.yan.database.model.smuta.Notification
import ru.yan.database.model.smuta.User
import ru.yan.database.model.smuta.dto.DtoNotification
import ru.yan.database.model.smuta.dto.DtoNotificationToSend
import ru.yan.database.model.smuta.dto.DtoUser
import ru.yan.database.model.smuta.dto.toDto
import ru.yan.database.repository.nsi.NotificationCategoryRepository
import ru.yan.database.repository.smuta.CountryRepository
import ru.yan.database.repository.smuta.NotificationRepository
import ru.yan.database.service.NotificationService
import java.util.*

@Service
class NotificationServiceImpl(
    private val countryRepository: CountryRepository,
    private val notificationRepository: NotificationRepository,
    private val notificationCategoryRepository: NotificationCategoryRepository
): NotificationService {

    @Transactional
    override fun getAllNotificationCategories(pageRequest: PageRequest) =
        notificationCategoryRepository.findAll(pageRequest).map { it.toDto() }

    @Transactional
    override fun getUnreadImportantNotificationsToSend() =
        notificationRepository.findUnreadImportantNotifications().map {
            DtoNotificationToSend(
                it.id,
                it.message,
                it.isImportant,
                it.notificationCategory.toDto(),
                it.country.user.toDto()
            )
        }

    @Transactional
    override fun getUnreadNotificationCategories(user: DtoUser, pageRequest: PageRequest): Page<DtoNotificationCategory> {
        countryRepository.findByUserId(user.id)?.let { country ->

            return notificationCategoryRepository.findUnreadNotificationCategory(country.id, pageRequest)
                .map { it.toDto() }

        } ?: notFoundBy(Country::class, User::class, user.id)
    }

    @Transactional
    override fun getAllNotifications(user: DtoUser, notificationCategoryId: UUID?, pageRequest: PageRequest): Page<DtoNotification> {
        val page = notificationCategoryId?.let { id ->
            notificationRepository.findAllNotificationsByCategory(user.id, id, pageRequest)
        } ?: run {
            notificationRepository.findAllNotifications(user.id, pageRequest)
        }

        val readedNotifications = mutableListOf<Notification>()
        page.forEach {
            if (it.deletedAt == null) {
                it.deletedAt = Date()

                readedNotifications.add(it)
            }
        }

        notificationRepository.saveAll(readedNotifications)

        return page.map { it.toDto() }
    }

    @Transactional
    override fun getUnreadNotifications(user: DtoUser, notificationCategoryId: UUID?, pageRequest: PageRequest): Page<DtoNotification> {
        val page = notificationCategoryId?.let { id ->
            notificationRepository.findUnreadNotificationsByCategory(user.id, id, pageRequest)
        } ?: run {
            notificationRepository.findAllUnreadNotifications(user.id, pageRequest)
        }

        val readedNotifications = page.mapIndexed { _, notification ->
            notification.deletedAt = Date()

            notification
        }

        notificationRepository.saveAll(readedNotifications)

        return page.map { it.toDto() }
    }

    @Transactional
    override fun redAllNotifications(user: DtoUser) {
        val unreadNotifications = notificationRepository.findAllUnreadNotifications(user.id)

        if (unreadNotifications.isNotEmpty()) {
            notificationRepository.saveAll(
                unreadNotifications.map {
                    it.deletedAt = Date()

                    it
                }
            )
        }
    }
}