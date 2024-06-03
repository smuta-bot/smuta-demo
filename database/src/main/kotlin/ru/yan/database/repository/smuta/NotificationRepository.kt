package ru.yan.database.repository.smuta

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import ru.yan.database.model.smuta.Notification
import java.util.*

@Repository
interface NotificationRepository: JpaRepository<Notification, UUID> {
    @Query("SELECT n FROM Notification n WHERE " +
            "n.country.user.id = :userId AND " +
            "n.notificationCategory.id = :categoryId AND " +
            "n.country.deletedAt IS NULL AND " +
            "n.deletedAt IS NULL " +
            "ORDER BY n.createdAt")
    fun findUnreadNotificationsByCategory(userId: UUID, categoryId: UUID, pageable: Pageable): Page<Notification>

    @Query("SELECT n FROM Notification n WHERE " +
            "n.country.user.id = :userId AND " +
            "n.notificationCategory.id = :categoryId AND " +
            "n.country.deletedAt IS NULL " +
            "ORDER BY n.createdAt")
    fun findAllNotificationsByCategory(userId: UUID, categoryId: UUID, pageable: Pageable): Page<Notification>

    @Query("SELECT n FROM Notification n WHERE " +
            "n.country.user.id = :userId AND " +
            "n.country.deletedAt IS NULL " +
            "ORDER BY n.createdAt")
    fun findAllNotifications(userId: UUID, pageable: Pageable): Page<Notification>

    @Query("SELECT n FROM Notification n WHERE " +
            "n.country.user.id = :userId AND " +
            "n.country.deletedAt IS NULL AND " +
            "n.deletedAt IS NULL " +
            "ORDER BY n.createdAt")
    fun findAllUnreadNotifications(userId: UUID, pageable: Pageable): Page<Notification>

    @Query("SELECT n FROM Notification n WHERE " +
            "n.country.user.id = :userId AND " +
            "n.country.deletedAt IS NULL AND " +
            "n.deletedAt IS NULL " +
            "ORDER BY n.createdAt")
    fun findAllUnreadNotifications(userId: UUID): List<Notification>

    @Query("SELECT n FROM Notification n " +
            "WHERE n.isImportant = true AND " +
            "n.country.deletedAt IS NULL AND " +
            "n.deletedAt IS NULL")
    fun findUnreadImportantNotifications(): List<Notification>
}