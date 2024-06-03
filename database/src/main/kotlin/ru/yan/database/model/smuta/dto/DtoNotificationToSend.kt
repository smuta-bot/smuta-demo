package ru.yan.database.model.smuta.dto

import ru.yan.database.model.nsi.dto.DtoNotificationCategory
import java.util.*

/**
 * Описание уведомления для отправки
 */
data class DtoNotificationToSend(
    val id: UUID,
    val message: String,
    val isImportant: Boolean,
    val notificationCategory: DtoNotificationCategory,
    val user: DtoUser
)
