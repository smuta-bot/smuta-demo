package ru.yan.core.processes.buildings.tree.branches

import ru.yan.core.bot.BotTaskNode
import ru.yan.core.general.CONFIRMATION_TYPE
import ru.yan.core.general.ConfirmationType
import ru.yan.core.general.GetConfirmationTask
import ru.yan.core.processes.buildings.tasks.list.status.*

fun getChangeStatusBranch(parentNode: BotTaskNode?) = BotTaskNode(
    ChangeStatusTask.ID,
    ChangeStatusTask::class,
    parentNode
).apply {
    next = listOf(
        BotTaskNode(
            GetConfirmationTask.ID,
            GetConfirmationTask::class,
            this
        ).apply {
            next = listOf(
                BotTaskNode(
                    StartProductionTask.ID,
                    StartProductionTask::class,
                    this
                ).apply {
                    processing = { message, _ ->
                        when (message.callbackData) {
                            "back" -> parent!!.parent!!
                            else -> this
                        }
                    }
                },
                BotTaskNode(
                    StartConservationTask.ID,
                    StartConservationTask::class,
                    this
                ).apply {
                    processing = { message, _ ->
                        when (message.callbackData) {
                            "back" -> parent!!.parent!!
                            else -> this
                        }
                    }
                },
                BotTaskNode(
                    StartDestroyTask.ID,
                    StartDestroyTask::class,
                    this
                ).apply {
                    processing = { message, _ ->
                        when (message.callbackData) {
                            "back" -> parent!!.parent!!
                            else -> this
                        }
                    }
                },
                BotTaskNode(
                    StopProductionTask.ID,
                    StopProductionTask::class,
                    this
                ).apply {
                    processing = { message, _ ->
                        when (message.callbackData) {
                            "back" -> parent!!.parent!!
                            else -> this
                        }
                    }
                },
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
                },
                BotTaskNode(
                    StopDestroyTask.ID,
                    StopDestroyTask::class,
                    this
                ).apply {
                    processing = { message, _ ->
                        when (message.callbackData) {
                            "back" -> parent!!.parent!!
                            else -> this
                        }
                    }
                },
                BotTaskNode(
                    StopConservationTask.ID,
                    StopConservationTask::class,
                    this
                ).apply {
                    processing = { message, _ ->
                        when (message.callbackData) {
                            "back" -> parent!!.parent!!
                            else -> this
                        }
                    }
                },
                BotTaskNode(
                    StopReactivationTask.ID,
                    StopReactivationTask::class,
                    this
                ).apply {
                    processing = { message, _ ->
                        when (message.callbackData) {
                            "back" -> parent!!.parent!!
                            else -> this
                        }
                    }
                },
                BotTaskNode(
                    ReactivateTask.ID,
                    ReactivateTask::class,
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
                when (message.callbackData) {
                    "confirm" -> {
                        val type = enumValueOf<ConfirmationType>(params[CONFIRMATION_TYPE] as String)
                        when(type) {
                            ConfirmationType.StartProduction -> next[0]
                            ConfirmationType.StartConservation -> next[1]
                            ConfirmationType.StartDestroy -> next[2]
                            ConfirmationType.StopProduction -> next[3]
                            ConfirmationType.StopConstruction -> next[4]
                            ConfirmationType.StopDestroy -> next[5]
                            ConfirmationType.StopConservation -> next[6]
                            ConfirmationType.StopReactivation -> next[7]
                            ConfirmationType.Reactivate -> next[8]
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
            "start_production" -> {
                params[CONFIRMATION_TYPE] = ConfirmationType.StartProduction.toString()
                next[0]
            }
            "start_destroy" -> {
                params[CONFIRMATION_TYPE] = ConfirmationType.StartDestroy.toString()
                next[0]
            }
            "start_conservation" -> {
                params[CONFIRMATION_TYPE] = ConfirmationType.StartConservation.toString()
                next[0]
            }
            "stop_production" -> {
                params[CONFIRMATION_TYPE] = ConfirmationType.StopProduction.toString()
                next[0]
            }
            "stop_construction" -> {
                params[CONFIRMATION_TYPE] = ConfirmationType.StopConstruction.toString()
                next[0]
            }
            "stop_destroy" -> {
                params[CONFIRMATION_TYPE] = ConfirmationType.StopDestroy.toString()
                next[0]
            }
            "stop_conservation" -> {
                params[CONFIRMATION_TYPE] = ConfirmationType.StopConservation.toString()
                next[0]
            }
            "stop_reactivation" -> {
                params[CONFIRMATION_TYPE] = ConfirmationType.StopReactivation.toString()
                next[0]
            }
            "reactivate" -> {
                params[CONFIRMATION_TYPE] = ConfirmationType.Reactivate.toString()
                next[0]
            }
            "back" -> parent!!
            else -> this
        }
    }
}