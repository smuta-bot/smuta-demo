package ru.yan.core.processes.market.tree

import ru.yan.core.bot.BotProcessTree
import ru.yan.core.processes.main.mainProcessTreeRoot
import ru.yan.core.processes.market.MarketProcess
import ru.yan.core.processes.market.tree.branches.getBuyBranch
import ru.yan.core.processes.market.tree.branches.getManageBranch
import ru.yan.core.processes.market.tree.branches.getSellBranch

fun marketProcessTreeRoot() = BotProcessTree(
    "/market",
    listOf("Купить", "Продать", "Мои предложения", "Назад"),
    MarketProcess.ID,
    MarketProcess::class
)

val marketProcessTree: BotProcessTree = marketProcessTreeRoot().apply {
    next = listOf(
        getBuyBranch(this),
        getSellBranch(this),
        getManageBranch(this),
        mainProcessTreeRoot()
    )
}