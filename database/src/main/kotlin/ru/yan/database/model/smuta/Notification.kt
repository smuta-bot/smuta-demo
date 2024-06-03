package ru.yan.database.model.smuta

import jakarta.persistence.*
import ru.yan.database.model.nsi.NotificationCategory
import java.util.*

/**
 * Уведомления
 */
@Entity
@Table(schema = "smuta", name = "notification")
class Notification(
    /**
     * Сообщение
     */
    val message: String,

    /**
     * Является ли уведомление важным
     */
    @Column(name = "is_important")
    val isImportant: Boolean,

    /**
     * Страна
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_uuid")
    val country: Country,

    /**
     * Категория уведомления
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notification_category_uuid")
    val notificationCategory: NotificationCategory
) {
    /**
     * Идентификатор
     */
    @Id
    @Column(name = "id", updatable = false)
    val id: UUID = UUID.randomUUID()

    /**
     * Дата создания
     */
    @Column(name = "created_at")
    val createdAt: Date = Date()

    /**
     * Дата удаления
     */
    @Column(name = "deleted_at")
    var deletedAt: Date? = null
}