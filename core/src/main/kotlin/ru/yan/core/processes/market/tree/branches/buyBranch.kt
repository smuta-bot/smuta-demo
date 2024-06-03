package ru.yan.core.processes.market.tree.branches

import ru.yan.core.bot.BotPageInfo
import ru.yan.core.bot.BotTaskNode
import ru.yan.core.convertTo
import ru.yan.core.processes.market.tasks.buy.*
import ru.yan.core.processes.market.tree.MARKET_PAGE
import ru.yan.core.processes.market.tree.MARKET_PRODUCT_ID
import ru.yan.core.processes.market.tree.MARKET_PRODUCT_QUANTITY

fun getBuyBranch(parentNode: BotTaskNode?) = BotTaskNode(
    ChooseMarketProductTask.ID,
    ChooseMarketProductTask::class,
    parentNode
).apply {
    next = listOf(
        BotTaskNode(
            ChooseQuantityProductTask.ID,
            ChooseQuantityProductTask::class,
            this
        ).apply {
            next = listOf(
                BotTaskNode(
                    ConfirmBuyTask.ID,
                    ConfirmBuyTask::class,
                    this
                ).apply {
                    next = listOf(
                        BotTaskNode(
                            BuyMarketProductTask.ID,
                            BuyMarketProductTask::class,
                            this
                        ).apply {
                            processing = { message, _ ->
                                when (message.callbackData) {
                                    "back" -> parent!!
                                    else -> parent!!.parent!!.parent!!
                                }
                            }
                        }
                    )
                    processing = { message, _ ->
                        when (message.callbackData) {
                            "confirm" -> next.first()
                            "not_confirm" -> parent!!
                            else -> this
                        }
                    }
                },
                BotTaskNode(
                    ConfirmBuyAllTask.ID,
                    ConfirmBuyAllTask::class,
                    this
                ).apply {
                    next = listOf(
                        BotTaskNode(
                            BuyAllMarketProductTask.ID,
                            BuyAllMarketProductTask::class,
                            this
                        ).apply {
                            processing = { message, _ ->
                                when (message.callbackData) {
                                    "back" -> parent!!
                                    else -> parent!!.parent!!.parent!!
                                }
                            }
                        }
                    )
                    processing = { message, _ ->
                        when (message.callbackData) {
                            "confirm" -> next.first()
                            "not_confirm" -> parent!!
                            else -> this
                        }
                    }
                }
            )
            processing = { message, params ->
                if (!message.text.isNullOrEmpty()) {
                    params[MARKET_PRODUCT_QUANTITY] = message.text.toLongOrNull() ?: 1L
                    next.first()
                } else {
                    when (message.callbackData) {
                        "buy_all" -> next.last()
                        "back" -> parent!!
                        else -> this
                    }
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