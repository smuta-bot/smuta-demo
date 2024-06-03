package ru.yan.database.model.task.dto

import ru.yan.database.model.nsi.types.BattleStage
import ru.yan.database.model.task.BattleTask
import java.util.UUID

/**
 * DTO для [BattleTask]
 */
data class DtoBattleTask(
    val id: UUID,
    val stage: BattleStage
)