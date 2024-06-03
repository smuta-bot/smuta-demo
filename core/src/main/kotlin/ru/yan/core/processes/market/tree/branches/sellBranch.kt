package ru.yan.core.processes.market.tree.branches

import ru.yan.core.bot.BotPageInfo
import ru.yan.core.bot.BotTaskNode
import ru.yan.core.convertTo
import ru.yan.core.processes.market.tasks.sell.*
import ru.yan.core.processes.market.tree.MARKET_PAGE
import ru.yan.core.processes.market.tree.STORAGE_RESOURCE_ID
import ru.yan.core.processes.market.tree.STORAGE_RESOURCE_PRICE
import ru.yan.core.processes.market.tree.STORAGE_RESOURCE_QUANTITY

fun getSellBranch(parentNode: BotTaskNode?) = BotTaskNode(
    ChooseStorageResourceTask.ID,
    ChooseStorageResourceTask::class,
    parentNode
).apply {
    next = listOf(
        BotTaskNode(
            ChooseQuantityResourceTask.ID,
            ChooseQuantityResourceTask::class,
            this
        ).apply {
            next = listOf(
                BotTaskNode(
                    ChooseResourcePriceTask.ID,
                    ChooseResourcePriceTask::class,
                    this
                ).apply {
                    next = listOf(
                        BotTaskNode(
                            ConfirmSaleTask.ID,
                            ConfirmSaleTask::class,
                            this
                        ).apply {
                            next = listOf(
                                BotTaskNode(
                                    SellStorageResourceTask.ID,
                                    SellStorageResourceTask::class,
                                    this
                                ).apply {
                                    processing = { message, _ ->
                                        when (message.callbackData) {
                                            "back" -> parent!!.parent!!.parent!!
                                            else -> parent!!.parent!!.parent!!.parent!!
                                        }
                                    }
                                }
                            )
                            processing = { message, _ ->
                                when (message.callbackData) {
                                    "confirm" -> next.first()
                                    "not_confirm" -> parent!!.parent!!.parent!!
                                    else -> this
                                }
                            }
                        }
                    )
                    processing = { message, params ->
                        if (!message.text.isNullOrEmpty()) {
                            params[STORAGE_RESOURCE_PRICE] = message.text.toDoubleOrNull() ?: 1.0
                            next.first()
                        } else {
                            when (message.callbackData) {
                                "back" -> parent!!
                                else -> this
                            }
                        }
                    }
                }
            )
            processing = { message, params ->
                if (!message.text.isNullOrEmpty()) {
                    params[STORAGE_RESOURCE_QUANTITY] = message.text.toLongOrNull() ?: 1L
                    next.first()
                } else {
                    when (message.callbackData) {
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
                params[STORAGE_RESOURCE_ID] = message.callbackData
                next.first()
            }
        }
    }
}