package ru.yan.core.processes.buildings.tree.branches

import ru.yan.core.bot.BotPageInfo
import ru.yan.core.bot.BotTaskNode
import ru.yan.core.convertTo
import ru.yan.core.processes.buildings.tasks.list.barrack.units.*
import ru.yan.core.processes.buildings.tree.*

fun getCombatUnitsBranch(parentNode: BotTaskNode?) = BotTaskNode(
    UnitsListTask.ID,
    UnitsListTask::class,
    parentNode
).apply {
    next = listOf(
        BotTaskNode(
            DescribeUnitTask.ID,
            DescribeUnitTask::class,
            this
        ).apply {
            next = listOf(
                BotTaskNode(
                    ExerciseCombatUnitTask.ID,
                    ExerciseCombatUnitTask::class,
                    this
                ).apply {
                    processing = { message, _ ->
                        when (message.callbackData) {
                            "back" -> parent!!
                            else -> this
                        }
                    }
                },
                BotTaskNode(
                    AbortCombatUnitTask.ID,
                    AbortCombatUnitTask::class,
                    this
                ).apply {
                    processing = { message, _ ->
                        when (message.callbackData) {
                            "back" -> parent!!
                            else -> this
                        }
                    }
                }
            )
            processing = { message, _ ->
                when (message.callbackData) {
                    "exercise" -> next.first()
                    "abort" -> next.last()
                    "back" -> parent!!
                    else -> this
                }
            }
        }
    )
    processing = { message, params ->
        val pageInfo = params[COMBAT_UNITS_PAGE]?.convertTo(BotPageInfo::class) ?: BotPageInfo(0, false)

        when (message.callbackData) {
            "page_back" -> {
                params[COMBAT_UNITS_PAGE] = BotPageInfo(pageInfo.number - 1, true)
                this
            }
            "page_forward" -> {
                params[COMBAT_UNITS_PAGE] = BotPageInfo(pageInfo.number + 1, true)
                this
            }
            "back" -> parent!!
            null -> this
            else -> {
                params[COMBAT_UNIT_ID] = message.callbackData
                next.first()
            }
        }
    }
}