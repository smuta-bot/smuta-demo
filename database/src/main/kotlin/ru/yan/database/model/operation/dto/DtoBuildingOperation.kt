package ru.yan.database.model.operation.dto

import ru.yan.database.model.nsi.types.BuildingStatus
import ru.yan.database.model.operation.BuildingOperation
import java.util.*

/**
 * DTO для [BuildingOperation]
 */
data class DtoBuildingOperation(
    val id: UUID,
    val status: BuildingStatus?,
    val level: Int?,
    val efficiency: Double?,
    val createdAt: Date
)
