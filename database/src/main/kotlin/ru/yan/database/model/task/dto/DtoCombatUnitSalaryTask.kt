package ru.yan.database.model.task.dto

import ru.yan.database.model.nsi.dto.DtoNsiCombatUnit
import ru.yan.database.model.task.CombatUnitSalaryTask
import java.util.*

/**
 * DTO для [CombatUnitSalaryTask]
 */
data class DtoCombatUnitSalaryTask(
    val id: UUID,
    val nsiCombatUnit: DtoNsiCombatUnit
)
