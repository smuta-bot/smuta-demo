package ru.yan.core.processes.buildings.tree.branches

import ru.yan.core.bot.BotTaskNode
import ru.yan.core.processes.buildings.tasks.list.level.*
import ru.yan.core.processes.buildings.tree.NEW_BUILDING_LEVEL

fun getChangeLevelBranch(parentNode: BotTaskNode?) = BotTaskNode(
    ChooseBuildingLevelTask.ID,
    ChooseBuildingLevelTask::class,
    parentNode
).apply {
    next = listOf(
        BotTaskNode(
            ChangeBuildingLevelTask.ID,
            ChangeBuildingLevelTask::class,
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
    processing = { message, params ->
        if (!message.text.isNullOrEmpty()) {
            params[NEW_BUILDING_LEVEL] = message.text.toIntOrNull() ?: 1
            next.first()
        } else {
            when (message.callbackData) {
                "back" -> parent!!
                null -> this
                else -> {
                    params[NEW_BUILDING_LEVEL] = message.callbackData.toInt()
                    next.first()
                }
            }
        }
    }
}