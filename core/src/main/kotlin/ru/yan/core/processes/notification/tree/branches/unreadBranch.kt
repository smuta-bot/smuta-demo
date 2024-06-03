package ru.yan.core.processes.notification.tree.branches

import ru.yan.core.bot.BotPageInfo
import ru.yan.core.bot.BotTaskNode
import ru.yan.core.convertTo
import ru.yan.core.processes.notification.tasks.unread.ChooseUnreadNotificationCategoryTask
import ru.yan.core.processes.notification.tasks.unread.ReadAllNotificationsTask
import ru.yan.core.processes.notification.tasks.unread.ShowUnreadNotificationTask
import ru.yan.core.processes.notification.tasks.unread.UnreadNotificationsTask
import ru.yan.core.processes.notification.tree.NOTIFICATION_CATEGORY_ID
import ru.yan.core.processes.notification.tree.NOTIFICATION_CATEGORY_PAGE
import ru.yan.core.processes.notification.tree.NOTIFICATION_PAGE

fun getUnreadBranch(parentNode: BotTaskNode?) = BotTaskNode(
    UnreadNotificationsTask.ID,
    UnreadNotificationsTask::class,
    parentNode
).apply {
    next = listOf(
        BotTaskNode(
            ChooseUnreadNotificationCategoryTask.ID,
            ChooseUnreadNotificationCategoryTask::class,
            this
        ).apply {
            next = listOf(
                BotTaskNode(
                    ShowUnreadNotificationTask.ID,
                    ShowUnreadNotificationTask::class,
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
            ShowUnreadNotificationTask.ID,
            ShowUnreadNotificationTask::class,
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
        },
        BotTaskNode(
            ReadAllNotificationsTask.ID,
            ReadAllNotificationsTask::class,
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
            "category" -> next[0]
            "all" -> next[1]
            "read_all" -> next[2]
            else -> this
        }
    }
}