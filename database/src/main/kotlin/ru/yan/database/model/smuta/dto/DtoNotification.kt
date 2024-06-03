package ru.yan.database.model.smuta.dto

import ru.yan.database.model.nsi.dto.DtoNotificationCategory
import ru.yan.database.model.smuta.Notification
import java.util.*

/**
 * DTO для [Notification]
 */
data class DtoNotification (
    val id: UUID,
    val message: String,
    val isImportant: Boolean,
    val notificationCategory: DtoNotificationCategory
)