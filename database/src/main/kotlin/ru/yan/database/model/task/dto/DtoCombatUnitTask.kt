package ru.yan.database.model.task.dto

import ru.yan.database.model.operation.dto.DtoCombatUnitOperation
import ru.yan.database.model.task.ConstructionTask
import java.util.*

/**
 * DTO для [ConstructionTask]
 */
data class DtoCombatUnitTask(
    val id: UUID,
    val fromBuildingOperation: DtoCombatUnitOperation?,
    val toBuildingOperation: DtoCombatUnitOperation
)
