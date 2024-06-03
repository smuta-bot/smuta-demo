package ru.yan.database.model.smuta.dto

import ru.yan.database.model.smuta.User
import ru.yan.database.model.smuta.UserActivity
import java.util.*

/**
 * DTO для [User]
 */
data class DtoUser(
    val id: UUID,
    val telegramId: Long?,
    val vkId: Long?,
    val userActivity: UserActivity?,
    val createdAt: Date,
    val activityAt: Date,
    val deletedAt: Date?
)
