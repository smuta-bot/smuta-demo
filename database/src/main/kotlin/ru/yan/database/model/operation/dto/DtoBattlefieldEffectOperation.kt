package ru.yan.database.model.operation.dto

import ru.yan.database.model.nsi.types.CombatUnitAttribute
import ru.yan.database.model.operation.BattlefieldEffectOperation
import java.util.*

/**
 * DTO для [BattlefieldEffectOperation]
 */
data class DtoBattlefieldEffectOperation(
    val id: UUID,
    val attribute: CombatUnitAttribute,
    val quantity: Double
)
