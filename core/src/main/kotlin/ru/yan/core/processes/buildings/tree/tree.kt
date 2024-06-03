package ru.yan.core.processes.buildings.tree

import ru.yan.core.bot.BotProcessTree
import ru.yan.core.processes.buildings.BuildingsProcess
import ru.yan.core.processes.buildings.tree.branches.getBuildBuildingBranch
import ru.yan.core.processes.buildings.tree.branches.getBuildingsListBranch
import ru.yan.core.processes.main.mainProcessTreeRoot

fun buildingsProcessTreeRoot() = BotProcessTree(
    "/buildings",
    listOf("Список построек", "Построить", "Назад"),
    BuildingsProcess.ID,
    BuildingsProcess::class
)

val buildingsProcessTree = buildingsProcessTreeRoot().apply {
    next = listOf(
        getBuildingsListBranch(this),
        getBuildBuildingBranch(this),
        mainProcessTreeRoot()
    )
}