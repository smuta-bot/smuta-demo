package ru.yan.core.processes.buildings.tree.branches

import ru.yan.core.bot.BotPageInfo
import ru.yan.core.bot.BotTaskNode
import ru.yan.core.convertTo
import ru.yan.core.processes.buildings.tree.NEW_BUILDING_LEVEL
import ru.yan.core.processes.buildings.tree.NSI_BUILDING_ID
import ru.yan.core.processes.buildings.tasks.construction.BuiltNsiBuildingTask
import ru.yan.core.processes.buildings.tasks.construction.ChooseNsiBuildingLevelTask
import ru.yan.core.processes.buildings.tasks.construction.DescribeNsiBuildingTask
import ru.yan.core.processes.buildings.tasks.construction.NsiBuildingsListTask
import ru.yan.core.processes.buildings.tree.BUILDINGS_PAGE

fun getBuildBuildingBranch(parentNode: BotTaskNode?) = BotTaskNode(
    NsiBuildingsListTask.ID,
    NsiBuildingsListTask::class,
    parentNode
).apply {
    next = listOf(
        BotTaskNode(
            DescribeNsiBuildingTask.ID,
            DescribeNsiBuildingTask::class,
            this
        ).apply {
            next = listOf(
                BotTaskNode(
                    BuiltNsiBuildingTask.ID,
                    BuiltNsiBuildingTask::class,
                    this
                ).apply {
                    processing = { message, _ ->
                        when (message.callbackData) {
                            "got_it" -> parent!!.parent!!
                            "back" -> parent!!
                            else -> this
                        }
                    }
                },
                BotTaskNode(
                    ChooseNsiBuildingLevelTask.ID,
                    ChooseNsiBuildingLevelTask::class,
                    this
                ).apply {
                    next = listOf(
                        BotTaskNode(
                            BuiltNsiBuildingTask.ID,
                            BuiltNsiBuildingTask::class,
                            this
                        ).apply {
                            processing = { message, _ ->
                                when (message.callbackData) {
                                    "got_it" -> parent!!.parent!!.parent!!
                                    "back" -> parent!!
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
                                "level_1" -> {
                                    params[NEW_BUILDING_LEVEL] = 1
                                    next.first()
                                }
                                "level_5" -> {
                                    params[NEW_BUILDING_LEVEL] = 5
                                    next.first()
                                }
                                "level_10" -> {
                                    params[NEW_BUILDING_LEVEL] = 10
                                    next.first()
                                }
                                "back" -> parent!!
                                else -> this
                            }
                        }
                    }
                }
            )
            processing = { message, params ->
                when (message.callbackData) {
                    "build" -> {
                        params[NEW_BUILDING_LEVEL] = 1
                        next.first()
                    }
                    "level" -> next.last()
                    "back" -> parent!!
                    else -> this
                }
            }
        }
    )
    processing = { message, params ->
        val pageInfo = params[BUILDINGS_PAGE]?.convertTo(BotPageInfo::class) ?: BotPageInfo(0, false)

        when (message.callbackData) {
            "page_back" -> {
                params[BUILDINGS_PAGE] = BotPageInfo(pageInfo.number - 1, true)
                this
            }
            "page_forward" -> {
                params[BUILDINGS_PAGE] = BotPageInfo(pageInfo.number + 1, true)
                this
            }
            null -> this
            else -> {
                params[NSI_BUILDING_ID] = message.callbackData
                next.first()
            }
        }
    }
}