package ru.yan.core.processes.buildings.tree.branches

import ru.yan.core.bot.BotTaskNode
import ru.yan.core.general.CONFIRMATION_TYPE
import ru.yan.core.general.ConfirmationType
import ru.yan.core.general.GetConfirmationTask
import ru.yan.core.processes.buildings.tasks.list.barrack.*
import ru.yan.core.processes.buildings.tasks.list.status.StopConstructionTask
import ru.yan.core.processes.buildings.tree.*
import ru.yan.database.model.barrackUUID

fun getBarrackBranch(parentNode: BotTaskNode?) = BotTaskNode(
    DescribeBarrackTask.ID,
    DescribeBarrackTask::class,
    parentNode
).apply {
    next = listOf(
        getCombatUnitsBranch(this),
        getBattleManagementBranch(this),
        getCreateCombatUnitBranch(this),
        BotTaskNode(
            GetConfirmationTask.ID,
            GetConfirmationTask::class,
            this
        ).apply {
            next = listOf(
                BotTaskNode(
                    StopConstructionTask.ID,
                    StopConstructionTask::class,
                    this
                ).apply {
                    processing = { message, _ ->
                        when (message.callbackData) {
                            "back" -> parent!!.parent!!
                            else -> this
                        }
                    }
                }
            )
        }
    )
    processing = { message, params ->
        when (message.callbackData) {
            "units" -> {
                next[0]
            }
            "battle" -> {
                next[1]
            }
            "create" -> {
                next[2]
            }
            "stop_construction" -> {
                params[CONFIRMATION_TYPE] = ConfirmationType.StopConstruction.toString()
                params[NSI_BUILDING_ID] = barrackUUID
                next[3]
            }
            "back" -> parent!!
            else -> this
        }
    }
}