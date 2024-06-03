package ru.yan.database.model.operation.dto

import ru.yan.database.model.nsi.dto.DtoBattlefield
import ru.yan.database.model.nsi.types.BattleType
import ru.yan.database.model.operation.BattleOperation
import java.util.*

/**
 * DTO для [BattleOperation]
 */
data class DtoBattleOperation(
    val id: UUID,
    val battleType: BattleType,
    val area: Double,
    val attackingCountry: UUID,
    val defendingCountry: UUID,
    val battlefield: DtoBattlefield,
    val fightingUnits: List<DtoCountryFightingUnit>,
    val battlefieldEffects: List<DtoBattlefieldEffectOperation>,
    val createdAt: Date
)
