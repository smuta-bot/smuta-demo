package ru.yan.core.processes.notification.tree

import ru.yan.core.bot.BotProcessTree
import ru.yan.core.processes.main.mainProcessTreeRoot
import ru.yan.core.processes.notification.NotificationProcess
import ru.yan.core.processes.notification.tree.branches.getAllBranch
import ru.yan.core.processes.notification.tree.branches.getUnreadBranch

fun notificationProcessTreeRoot() = BotProcessTree(
    "/notification",
    listOf("Непрочитанные", "Все", "Назад"),
    NotificationProcess.ID,
    NotificationProcess::class
)

val notificationProcessTree: BotProcessTree = notificationProcessTreeRoot().apply {
    next = listOf(
        getUnreadBranch(this),
        getAllBranch(this),
        mainProcessTreeRoot()
    )
}