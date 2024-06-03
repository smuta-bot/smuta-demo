package ru.yan.core.processes.notification.tree.branches

import ru.yan.core.bot.BotPageInfo
import ru.yan.core.bot.BotTaskNode
import ru.yan.core.convertTo
import ru.yan.core.processes.notification.tasks.all.AllNotificationsTask
import ru.yan.core.processes.notification.tasks.all.ChooseNotificationCategoryTask
import ru.yan.core.processes.notification.tasks.all.ShowNotificationTask
import ru.yan.core.processes.notification.tree.NOTIFICATION_CATEGORY_ID
import ru.yan.core.processes.notification.tree.NOTIFICATION_CATEGORY_PAGE
import ru.yan.core.processes.notification.tree.NOTIFICATION_PAGE

fun getAllBranch(parentNode: BotTaskNode?) = BotTaskNode(
    AllNotificationsTask.ID,
    AllNotificationsTask::class,
    parentNode
).apply {
    next = listOf(
        BotTaskNode(
            ChooseNotificationCategoryTask.ID,
            ChooseNotificationCategoryTask::class,
            this
        ).apply {
            next = listOf(
                BotTaskNode(
                    ShowNotificationTask.ID,
                    ShowNotificationTask::class,
                    this
                ).apply {
                    processing = { message, params ->
                        val pageInfo = params[NOTIFICATION_PAGE]?.convertTo(BotPageInfo::class) ?: BotPageInfo(0, false)

                        when (message.callbackData) {
                            "page_back" -> {
                                params[NOTIFICATION_PAGE] = BotPageInfo(pageInfo.number - 1, true)
                                this
                            }
                            "page_forward" -> {
                                params[NOTIFICATION_PAGE] = BotPageInfo(pageInfo.number + 1, true)
                                this
                            }
                            "back" -> parent!!
                            else -> {
                                this
                            }
                        }
                    }
                }
            )
            processing = { message, params ->
                val pageInfo = params[NOTIFICATION_CATEGORY_PAGE]?.convertTo(BotPageInfo::class) ?: BotPageInfo(0, false)

                when (message.callbackData) {
                    "page_back" -> {
                        params[NOTIFICATION_CATEGORY_PAGE] = BotPageInfo(pageInfo.number - 1, true)
                        this
                    }
                    "page_forward" -> {
                        params[NOTIFICATION_CATEGORY_PAGE] = BotPageInfo(pageInfo.number + 1, true)
                        this
                    }
                    "back" -> parent!!
                    null -> this
                    else -> {
                        params[NOTIFICATION_CATEGORY_ID] = message.callbackData
                        next.first()
                    }
                }
            }
        },
        BotTaskNode(
            ShowNotificationTask.ID,
            ShowNotificationTask::class,
            this
        ).apply {
            processing = { message, params ->
                val pageInfo = params[NOTIFICATION_PAGE]?.convertTo(BotPageInfo::class) ?: BotPageInfo(0, false)

                when (message.callbackData) {
                    "page_back" -> {
                        params[NOTIFICATION_PAGE] = BotPageInfo(pageInfo.number - 1, true)
                        this
                    }
                    "page_forward" -> {
                        params[NOTIFICATION_PAGE] = BotPageInfo(pageInfo.number + 1, true)
                        this
                    }
                    "back" -> parent!!
                    else -> {
                        this
                    }
                }
            }
        }
    )
    processing = { message, _ ->
        when (message.callbackData) {
            "category" -> next.first()
            "all" -> next.last()
            else -> this
        }
    }
}