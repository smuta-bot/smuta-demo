package ru.yan.database.model.nsi

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*

/**
 * Категории уведомлений
 */
@Entity
@Table(schema = "nsi", name = "notification_category")
class NotificationCategory(
    /**
     * Название
     */
    val name: String
) {
    @Id
    @Column(name = "id", updatable = false)
    var id: UUID = UUID.randomUUID()
}