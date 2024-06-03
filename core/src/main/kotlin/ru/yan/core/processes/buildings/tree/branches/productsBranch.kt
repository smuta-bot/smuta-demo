package ru.yan.core.processes.buildings.tree.branches

import ru.yan.core.bot.BotPageInfo
import ru.yan.core.bot.BotTaskNode
import ru.yan.core.convertTo
import ru.yan.core.general.CONFIRMATION_TYPE
import ru.yan.core.general.ConfirmationType
import ru.yan.core.general.GetConfirmationTask
import ru.yan.core.processes.buildings.tasks.list.products.*
import ru.yan.core.processes.buildings.tree.BUILDING_PRODUCTS_PAGE
import ru.yan.core.processes.buildings.tree.BUILDING_PRODUCT_ID
import ru.yan.core.processes.buildings.tree.PRODUCTS_PAGE

fun getProductsBranch(parentNode: BotTaskNode?) = BotTaskNode(
    BuildingProductsTask.ID,
    BuildingProductsTask::class,
    parentNode
).apply {
    next = listOf(
        BotTaskNode(
            ChooseProductTask.ID,
            ChooseProductTask::class,
            this
        ).apply {
            next = listOf(
                BotTaskNode(
                    DescribeProductTask.ID,
                    DescribeProductTask::class,
                    this
                ).apply {
                    next = listOf(
                        BotTaskNode(
                            IncludeProductTask.ID,
                            IncludeProductTask::class,
                            this
                        ).apply {
                            processing = { message, _ ->
                                when (message.callbackData) {
                                    "back" -> parent!!.parent!!
                                    else -> this
                                }
                            }
                        }
                    )
                    processing = { message, _ ->
                        when (message.callbackData) {
                            "include" -> next.first()
                            "back" -> parent!!
                            else -> this
                        }
                    }
                }
            )
            processing = { message, params ->
                val pageInfo = params[BUILDING_PRODUCTS_PAGE]?.convertTo(BotPageInfo::class) ?: BotPageInfo(0, false)

                when (message.callbackData) {
                    "page_back" -> {
                        params[BUILDING_PRODUCTS_PAGE] = BotPageInfo(pageInfo.number - 1, true)
                        this
                    }
                    "page_forward" -> {
                        params[BUILDING_PRODUCTS_PAGE] = BotPageInfo(pageInfo.number + 1, true)
                        this
                    }
                    null -> this
                    "back" -> parent!!
                    else -> {
                        params[BUILDING_PRODUCT_ID] = message.callbackData
                        next.last()
                    }
                }
            }
        },
        BotTaskNode(
            GetConfirmationTask.ID,
            GetConfirmationTask::class,
            this
        ).apply {
            next = listOf(
                BotTaskNode(
                    ExcludeProductTask.ID,
                    ExcludeProductTask::class,
                    this
                ).apply {
                    processing = { message, _ ->
                        when (message.callbackData) {
                            "back" -> parent!!.parent!!
                            else -> this
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
        val pageInfo = params[PRODUCTS_PAGE]?.convertTo(BotPageInfo::class) ?: BotPageInfo(0, false)

        when (message.callbackData) {
            "add" -> next[0]
            "back" -> parent!!
            "page_back" -> {
                params[PRODUCTS_PAGE] = BotPageInfo(pageInfo.number - 1, true)
                this
            }
            "page_forward" -> {
                params[PRODUCTS_PAGE] = BotPageInfo(pageInfo.number + 1, true)
                this
            }
            null -> this
            else -> {
                params[BUILDING_PRODUCT_ID] = message.callbackData
                params[CONFIRMATION_TYPE] = ConfirmationType.ExcludeProduct.toString()

                next[1]
            }
        }
    }
}