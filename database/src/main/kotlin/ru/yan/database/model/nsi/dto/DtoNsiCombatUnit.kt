package ru.yan.database.model.nsi.dto

import ru.yan.database.model.nsi.NsiCombatUnit
import java.util.*

/**
 * DTO для [NsiCombatUnit]
 */
data class DtoNsiCombatUnit(
    val id: UUID,
    val name: String,
    val description: String,
    val damage: Int,
    val speed: Int,
    val armor: Int,
    val attackRange: Int,
    val salary: Double
)