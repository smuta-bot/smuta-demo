package ru.yan.database.model.task.dto

import ru.yan.database.model.operation.dto.DtoBuildingOperation
import ru.yan.database.model.task.ConstructionTask
import java.util.UUID

/**
 * DTO для [ConstructionTask]
 */
data class DtoConstructionTask(
    val id: UUID,
    val fromBuildingOperation: DtoBuildingOperation?,
    val toBuildingOperation: DtoBuildingOperation
)
