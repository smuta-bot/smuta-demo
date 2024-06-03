package ru.yan.database.model.operation.dto

import ru.yan.database.model.nsi.dto.DtoNsiCombatUnit
import ru.yan.database.model.nsi.types.CombatUnitPurpose
import ru.yan.database.model.operation.CountryFightingUnit
import java.util.*

/**
 * DTO для [CountryFightingUnit]
 */
data class DtoCountryFightingUnit(
    val id: UUID,
    val combatUnitPurpose: CombatUnitPurpose,
    val nsiCombatUnit: DtoNsiCombatUnit
)
