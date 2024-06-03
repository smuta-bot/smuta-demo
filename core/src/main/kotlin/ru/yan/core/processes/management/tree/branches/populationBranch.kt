package ru.yan.core.processes.management.tree.branches

import ru.yan.core.bot.BotTaskNode
import ru.yan.core.processes.management.tasks.population.PopulationTask

fun getPopulationBranch(parentNode: BotTaskNode?) = BotTaskNode(
    PopulationTask.ID,
    PopulationTask::class,
    parentNode
)