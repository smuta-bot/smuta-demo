package ru.yan.core.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import ru.yan.core.bot.message.InBotMessage
import ru.yan.core.bot.message.BotTextMessage
import ru.yan.core.bot.request.BotMessageQueue
import ru.yan.core.enums.ProcessingStatus
import ru.yan.core.getMessengerId
import ru.yan.core.processes.buildings.tree.buildingsProcessTree
import ru.yan.core.processes.main.mainProcessTree
import ru.yan.core.processes.management.tree.managementProcessTree
import ru.yan.core.processes.market.tree.marketProcessTree
import ru.yan.core.processes.notification.tree.notificationProcessTree
import ru.yan.core.processes.registration.registrationProcessTree
import ru.yan.database.model.smuta.UserActivity
import ru.yan.database.model.smuta.dto.DtoUser
import ru.yan.database.service.UserService

abstract class MessageProcessingService {
    private val processTrees = listOf(
        registrationProcessTree,
        mainProcessTree,
        buildingsProcessTree,
        marketProcessTree,
        managementProcessTree,
        notificationProcessTree
    )

    @Autowired
    protected lateinit var userService: UserService

    @Autowired
    private lateinit var appContext: ApplicationContext

    @Autowired
    private lateinit var queue: BotMessageQueue<*>

    abstract fun getUser(id: Long): DtoUser

    fun processing(message: InBotMessage?): ProcessingStatus {
        message ?: return ProcessingStatus.FAILED

        val user = getUser(message.userId)
        val params = user.userActivity?.params ?: hashMapOf()
        val node = if (message.text?.startsWith("/") == true) {
            processTrees.find { it.startCommand == message.text }
        } else {
            user.userActivity?.let { activity ->
                processTrees.find { it.id == activity.breadCrumbs.taskId }
                    ?.let {
                       it.checkButtons(message, params) ?:
                       it.fromBreadCrumbs(activity.breadCrumbs)
                           ?.processing
                           ?.invoke(message, params)
                    }
            }
        }

        node?.let {
            val response = appContext
                .getBean(node.executor.java)
                .execute(message, user, params)

            queue.add(response)
            userService.updateUserActivity(user, UserActivity(params, it.toBreadCrumbs()))

        } ?: queue.add(BotTextMessage("Команда не распознана", user.getMessengerId()))

        return ProcessingStatus.OK
    }
}