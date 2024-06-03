package ru.yan.core.processes.management.tree.branches

import ru.yan.core.bot.BotTaskNode
import ru.yan.core.processes.management.tasks.storage.StorageTask

fun getStorageBranch(parentNode: BotTaskNode?) = BotTaskNode(
    StorageTask.ID,
    StorageTask::class,
    parentNode
)