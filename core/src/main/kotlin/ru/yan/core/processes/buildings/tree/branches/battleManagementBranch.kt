package ru.yan.core.processes.buildings.tree.branches

import ru.yan.core.bot.BotPageInfo
import ru.yan.core.bot.BotTaskNode
import ru.yan.core.convertTo
import ru.yan.core.general.CONFIRMATION_TYPE
import ru.yan.core.general.ConfirmationType
import ru.yan.core.general.GetConfirmationTask
import ru.yan.core.processes.buildings.tasks.list.barrack.management.*
import ru.yan.core.processes.buildings.tree.*

fun getBattleManagementBranch(parentNode: BotTaskNode?) = BotTaskNode(
    BattleManagementTask.ID,
    BattleManagementTask::class,
    parentNode
).apply {
    next = listOf(
        BotTaskNode(
            EnemySearchTask.ID,
            EnemySearchTask::class,
            this
        ).apply {
            processing = { message, _ ->
                when (message.callbackData) {
                    "got_it" -> parent!!
                    else -> this
                }
            }
        },
        BotTaskNode(
            EnemySearchCancelTask.ID,
            EnemySearchCancelTask::class,
            this
        ).apply {
            processing = { message, _ ->
                when (message.callbackData) {
                    "got_it" -> parent!!
                    else -> this
                }
            }
        },
        BotTaskNode(
            SelectCombatUnitTask.ID,
            SelectCombatUnitTask::class,
            this
        ).apply {
            next = listOf(
                BotTaskNode(
                    ChooseUnitSizeTask.ID,
                    ChooseUnitSizeTask::class,
                    this
                ).apply {
                    next = listOf(
                        BotTaskNode(
                            SendCombatUnitTask.ID,
                            SendCombatUnitTask::class,
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
        },
        BotTaskNode(
            RevokeUnitConfirmTask.ID,
            RevokeUnitConfirmTask::class,
            this
        ).apply {
            next = listOf(
                BotTaskNode(
                    RevokeUnitTask.ID,
                    RevokeUnitTask::class,
                    this
                ).apply {
                    processing = { message, _ ->
                        when (message.callbackData) {
                            "got_it",
                            "back" -> parent!!.parent!!
                            else -> this
                        }
                    }
                }
            )
            processing = { message, _ ->
                when (message.callbackData) {
                    "confirm" -> next.first()
                    "not_confirm" -> parent!!
                    else -> this
                }
            }
        },
        BotTaskNode(
            GetConfirmationTask.ID,
            GetConfirmationTask::class,
            this
        ).apply {
            next = listOf(
                BotTaskNode(
                    StopAttackTask.ID,
                    StopAttackTask::class,
                    this
                ).apply {
                    processing = { message, _ ->
                        when (message.callbackData) {
                            "got_it",
                            "back" -> parent!!.parent!!
                            else -> this
                        }
                    }
                },
                BotTaskNode(
                    SurrenderTask.ID,
                    SurrenderTask::class,
                    this
                ).apply {
                    processing = { message, _ ->
                        when (message.callbackData) {
                            "got_it",
                            "back" -> parent!!.parent!!
                            else -> this
                        }
                    }
                }
            )
            processing = { message, params ->
                when (message.callbackData) {
                    "confirm" -> {
                        val type = enumValueOf<ConfirmationType>(params[CONFIRMATION_TYPE] as String)
                        when(type) {
                            ConfirmationType.StopAttack -> next[0]
                            ConfirmationType.Surrender -> next[1]
                            else -> this
                        }
                    }
                    "not_confirm" -> parent!!
                    else -> this
                }
            }
        }
    )
    processing = { message, params ->
        when (message.callbackData) {
            "enemy_search" -> next[0]
            "search_cancel" -> next[1]
            "send_unit" -> next[2]
            "revoke_unit" -> next[3]
            "stop_attack" -> {
                params[CONFIRMATION_TYPE] = ConfirmationType.StopAttack.toString()
                next[4]
            }
            "surrender" -> {
                params[CONFIRMATION_TYPE] = ConfirmationType.Surrender.toString()
                next[4]
            }
            "back" -> parent!!
            else -> this
        }
    }
}