package ru.yan.core.processes.market.tree.branches

import ru.yan.core.bot.BotPageInfo
import ru.yan.core.bot.BotTaskNode
import ru.yan.core.convertTo
import ru.yan.core.processes.market.tasks.manage.ChooseOwnMarketProductTask
import ru.yan.core.processes.market.tasks.manage.DescribeChosenProductTask
import ru.yan.core.processes.market.tasks.manage.WithdrawProductTask
import ru.yan.core.processes.market.tree.MARKET_PAGE
import ru.yan.core.processes.market.tree.MARKET_PRODUCT_ID

fun getManageBranch(parentNode: BotTaskNode?) = BotTaskNode(
    ChooseOwnMarketProductTask.ID,
    ChooseOwnMarketProductTask::class,
    parentNode
).apply {
    next = listOf(
        BotTaskNode(
            DescribeChosenProductTask.ID,
            DescribeChosenProductTask::class,
            this
        ).apply {
            next = listOf(
                BotTaskNode(
                    WithdrawProductTask.ID,
                    WithdrawProductTask::class,
                    this
                ).apply {
                    processing = { message, _ ->
                        when (message.callbackData) {
                            "got_it" -> parent!!.parent!!
                            "back" -> parent!!
                            else -> this
                        }
                    }
                }
            )
            processing = { message, params ->
                when (message.callbackData) {
                    "withdraw" -> next.first()
                    "back" -> parent!!
                    else -> this
                }
            }
        }
    )
    processing = { message, params ->
        val pageInfo = params[MARKET_PAGE]?.convertTo(BotPageInfo::class) ?: BotPageInfo(0, false)

        when (message.callbackData) {
            "page_back" -> {
                params[MARKET_PAGE] = BotPageInfo(pageInfo.number - 1, true)
                this
            }
            "page_forward" -> {
                params[MARKET_PAGE] = BotPageInfo(pageInfo.number + 1, true)
                this
            }
            null -> this
            else -> {
                params[MARKET_PRODUCT_ID] = message.callbackData
                next.first()
            }
        }
    }
}