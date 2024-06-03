package ru.yan.database.model.task.dto

import ru.yan.database.model.nsi.dto.DtoNsiCombatUnit
import ru.yan.database.model.task.CombatUnitDecayTask
import java.util.*


/**
 * DTO для [CombatUnitDecayTask]
 */
data class DtoCombatUnitDecayTask (
    val id: UUID,
    val nsiCombatUnit: DtoNsiCombatUnit
)