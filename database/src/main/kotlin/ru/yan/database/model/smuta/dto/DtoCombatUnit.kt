package ru.yan.database.model.smuta.dto

import ru.yan.database.model.nsi.dto.DtoNsiCombatUnit
import ru.yan.database.model.nsi.types.CombatUnitStatus
import ru.yan.database.model.smuta.CombatUnit
import java.util.UUID

/**
 * DTO для [CombatUnit]
 */
data class DtoCombatUnit(
    val id: UUID,
    val status: CombatUnitStatus,
    val quantity: Long,
    val experience: Double,
    val nsiCombatUnit: DtoNsiCombatUnit
)
