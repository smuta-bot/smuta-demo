package ru.yan.core.processes.buildings.tree.branches

import ru.yan.core.bot.BotTaskNode
import ru.yan.core.general.CONFIRMATION_TYPE
import ru.yan.core.general.ConfirmationType
import ru.yan.core.general.GetConfirmationTask
import ru.yan.core.processes.buildings.tasks.list.hospital.DescribeHospitalTask
import ru.yan.core.processes.buildings.tasks.list.hospital.StartTreatmentTask
import ru.yan.core.processes.buildings.tasks.list.hospital.StopTreatmentTask
import ru.yan.core.processes.buildings.tasks.list.status.StopConstructionTask
import ru.yan.core.processes.buildings.tree.NSI_BUILDING_ID
import ru.yan.database.model.hospitalUUID

fun getHospitalBranch(parentNode: BotTaskNode?) = BotTaskNode(
    DescribeHospitalTask.ID,
    DescribeHospitalTask::class,
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
                    StartTreatmentTask.ID,
                    StartTreatmentTask::class,
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
                    StopTreatmentTask.ID,
                    StopTreatmentTask::class,
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
                            ConfirmationType.OpenHospital -> next[0]
                            ConfirmationType.CloseHospital -> next[1]
                            else -> this
                        }
                    }
                    "not_confirm" -> parent!!
                    else -> this
                }
            }
        },
        getChangeLevelBranch(this),
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
            "start_treatment" -> {
                params[CONFIRMATION_TYPE] = ConfirmationType.OpenHospital.toString()
                next[0]
            }
            "stop_treatment" -> {
                params[CONFIRMATION_TYPE] = ConfirmationType.CloseHospital.toString()
                next[1]
            }
            "level_change" -> {
                params[NSI_BUILDING_ID] = hospitalUUID
                next[2]
            }
            "stop_construction" -> {
                params[CONFIRMATION_TYPE] = ConfirmationType.StopConstruction.toString()
                params[NSI_BUILDING_ID] = hospitalUUID
                next[3]
            }
            "back" -> parent!!
            else -> this
        }
    }
}