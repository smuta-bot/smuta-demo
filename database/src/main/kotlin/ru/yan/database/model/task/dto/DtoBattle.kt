package ru.yan.database.model.task.dto

import ru.yan.database.model.nsi.types.BattleStage
import java.util.UUID

/**
 * Описание боя
 */
data class DtoBattle(
    val taskId: UUID,
    val isAttacker: Boolean,
    val battleStage: BattleStage
)
