package ru.yan.core.processes.main

import ru.yan.core.bot.BotProcessTree
import ru.yan.core.processes.buildings.tree.buildingsProcessTreeRoot
import ru.yan.core.processes.management.tree.managementProcessTreeRoot
import ru.yan.core.processes.market.tree.marketProcessTreeRoot
import ru.yan.core.processes.notification.tree.notificationProcessTreeRoot

fun mainProcessTreeRoot() = BotProcessTree(
    "/menu",
    listOf("Здания", "Рынок", "Управление", "Уведомления"),
    MainMenuProcess.ID,
    MainMenuProcess::class
)

val mainProcessTree: BotProcessTree = mainProcessTreeRoot().apply {
    next = listOf(
        buildingsProcessTreeRoot(),
        marketProcessTreeRoot(),
        managementProcessTreeRoot(),
        notificationProcessTreeRoot()
    )
}