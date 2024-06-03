package ru.yan.database.model.nsi.dto

import ru.yan.database.model.nsi.NsiBattlefieldEffect
import ru.yan.database.model.nsi.types.CombatUnitAttribute
import java.util.*

/**
 * DTO для [NsiBattlefieldEffect]
 */
data class DtoNsiBattlefieldEffect(
    val id: UUID,
    val attribute: CombatUnitAttribute,
    val min: Double,
    val max: Double,
)
