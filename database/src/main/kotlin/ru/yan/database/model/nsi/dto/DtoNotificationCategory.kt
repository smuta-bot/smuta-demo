package ru.yan.database.model.nsi.dto

import ru.yan.database.model.nsi.NotificationCategory
import java.util.*

/**
 * DTO для [NotificationCategory]
 */
data class DtoNotificationCategory (
    val id: UUID,
    val name: String
)