package ru.yan.database.model.nsi.dto

import ru.yan.database.model.nsi.CombatUnitResource
import java.util.*

/**
 * DTO для [CombatUnitResource]
 */
data class DtoCombatUnitResource(
    val id: UUID,
    val quantity: Int,
    val resource: DtoSimpleDictionary
)
