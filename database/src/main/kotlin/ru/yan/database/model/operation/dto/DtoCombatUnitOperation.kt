package ru.yan.database.model.operation.dto

import ru.yan.database.model.nsi.dto.DtoNsiCombatUnit
import ru.yan.database.model.nsi.types.CombatUnitStatus
import ru.yan.database.model.operation.CombatUnitOperation
import java.util.*

/**
 * DTO для [CombatUnitOperation]
 */
data class DtoCombatUnitOperation(
    val id: UUID,
    val status: CombatUnitStatus,
    val quantity: Long,
    val experience: Double,
    val nsiCombatUnit: DtoNsiCombatUnit,
    val createdAt: Date
)
