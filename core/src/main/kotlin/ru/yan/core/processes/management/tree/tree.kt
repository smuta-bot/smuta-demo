package ru.yan.core.processes.management.tree

import ru.yan.core.bot.BotProcessTree
import ru.yan.core.processes.main.mainProcessTreeRoot
import ru.yan.core.processes.management.ManagementProcess
import ru.yan.core.processes.management.tree.branches.getPopulationBranch
import ru.yan.core.processes.management.tree.branches.getStorageBranch

fun managementProcessTreeRoot() = BotProcessTree(
    "/management",
    listOf("Население", "Хранилище", "Назад"),
    ManagementProcess.ID,
    ManagementProcess::class
)

val managementProcessTree: BotProcessTree = managementProcessTreeRoot().apply {
    next = listOf(
        getPopulationBranch(this),
        getStorageBranch(this),
        mainProcessTreeRoot()
    )
}