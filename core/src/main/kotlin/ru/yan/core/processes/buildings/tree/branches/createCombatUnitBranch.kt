package ru.yan.core.processes.buildings.tree.branches

import ru.yan.core.bot.BotPageInfo
import ru.yan.core.bot.BotTaskNode
import ru.yan.core.convertTo
import ru.yan.core.processes.buildings.tasks.list.barrack.create.ChooseNumberPeopleTask
import ru.yan.core.processes.buildings.tasks.list.barrack.create.ConfirmCombatUnitCreationTask
import ru.yan.core.processes.buildings.tasks.list.barrack.create.CreateCombatUnitTask
import ru.yan.core.processes.buildings.tasks.list.barrack.create.CreateUnitTask
import ru.yan.core.processes.buildings.tree.*

fun getCreateCombatUnitBranch(parentNode: BotTaskNode?) = BotTaskNode(
    CreateUnitTask.ID,
    CreateUnitTask::class,
    parentNode
).apply {
    next = listOf(
        BotTaskNode(
            ChooseNumberPeopleTask.ID,
            ChooseNumberPeopleTask::class,
            this
        ).apply {
            next = listOf(
                BotTaskNode(
                    ConfirmCombatUnitCreationTask.ID,
                    ConfirmCombatUnitCreationTask::class,
                    this
                ).apply {
                    next = listOf(
                        BotTaskNode(
                            CreateCombatUnitTask.ID,
                            CreateCombatUnitTask::class,
                            this
                        ).apply {
                            processing = { message, _ ->
                                when (message.callbackData) {
                                    "got_it" -> parent!!.parent!!.parent!!.parent!!
                                    "back" -> parent!!
                                    else -> this
                                }
                            }
                        }
                    )
                    processing = { message, _ ->
                        when (message.callbackData) {
                            "confirm" -> next.first()
                            "not_confirm" -> parent!!
                            "back" -> parent!!
                            else -> this
                        }
                    }
                }
            )
            processing = { message, params ->
                if (!message.text.isNullOrEmpty()) {
                    params[COMBAT_UNIT_SIZE] = message.text.toLongOrNull() ?: 1
                    next.first()
                } else {
                    when (message.callbackData) {
                        "back" -> parent!!
                        else -> this
                    }
                }
            }
        }
    )
    processing = { message, params ->
        val pageInfo = params[NSI_COMBAT_UNITS_PAGE]?.convertTo(BotPageInfo::class) ?: BotPageInfo(0, false)

        when (message.callbackData) {
            "page_back" -> {
                params[NSI_COMBAT_UNITS_PAGE] = BotPageInfo(pageInfo.number - 1, true)
                this
            }
            "page_forward" -> {
                params[NSI_COMBAT_UNITS_PAGE] = BotPageInfo(pageInfo.number + 1, true)
                this
            }
            "back" -> parent!!
            null -> this
            else -> {
                params[NSI_COMBAT_UNIT_ID] = message.callbackData
                next.first()
            }
        }
    }
}