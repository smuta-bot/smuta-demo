package ru.yan.database.model.task.dto

import ru.yan.database.model.task.Task
import ru.yan.database.model.task.types.TaskType
import java.util.*

/**
 * DTO для [Task]
 */
data class DtoTask (
    val id: UUID,
    val type: TaskType,
    val duration: Int,
    val startedAt: Date,
    val deletedAt: Date?
)