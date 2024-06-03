package ru.yan.core.processes.buildings.tree.branches

import ru.yan.core.bot.BotPageInfo
import ru.yan.core.bot.BotTaskNode
import ru.yan.core.convertTo
import ru.yan.core.processes.buildings.tasks.list.*
import ru.yan.core.processes.buildings.tasks.list.hospital.DescribeHospitalTask
import ru.yan.core.processes.buildings.tree.*
import ru.yan.database.model.barrackUUID
import ru.yan.database.model.hospitalUUID

fun getBuildingsListBranch(parentNode: BotTaskNode?) = BotTaskNode(
    BuildingsListTask.ID,
    BuildingsListTask::class,
    parentNode
).apply {
    next = listOf(
        BotTaskNode(
            DescribeChosenBuildingTask.ID,
            DescribeChosenBuildingTask::class,
            this
        ).apply {
            next = listOf(
                getChangeLevelBranch(this),
                getProductsBranch(this),
                getChangeStatusBranch(this)
            )
            processing = { message, _ ->
                when (message.callbackData) {
                    "level_change" -> next[0]
                    "products" -> next[1]
                    "status_change" -> next[2]
                    "back" -> parent!!
                    else -> this
                }
            }
        },
        getHospitalBranch(this),
        getBarrackBranch(this)
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
            hospitalUUID -> next[1]
            barrackUUID -> next[2]
            null -> this
            else -> {
                params[NSI_BUILDING_ID] = message.callbackData
                next[0]
            }
        }
    }
}