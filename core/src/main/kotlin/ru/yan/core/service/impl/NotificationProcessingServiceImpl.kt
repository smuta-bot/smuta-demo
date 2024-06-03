package ru.yan.core.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import ru.yan.core.bot.message.BotTextMessage
import ru.yan.core.bot.request.BotMessageQueue
import ru.yan.core.getMessengerId
import ru.yan.core.service.NotificationProcessingService
import ru.yan.database.service.NotificationService
import ru.yan.database.service.TaskService
import ru.yan.database.service.UserService

@Service
class NotificationProcessingServiceImpl(
    private val taskService: TaskService,
    private val userService: UserService,
    private val notificationService: NotificationService
): NotificationProcessingService {

    @Autowired
    private lateinit var queue: BotMessageQueue<*>

    //@Scheduled(fixedRate = 100)
    override fun task() {
        //TODO: Перенести в отдельный модуль
        taskService.processing()
    }

    //@Scheduled(fixedRate = 5000)
    override fun notificateUsers() {
        userService.getToNotificate().map {
            queue.add(
                BotTextMessage("У вас есть не прочитанные уведомления", it.getMessengerId())
            )
        }
    }

    //@Scheduled(fixedRate = 1000)
    override fun importantNotificateUsers() {
        notificationService.getUnreadImportantNotificationsToSend().map {
            queue.add(
                BotTextMessage(it.message, it.user.getMessengerId())
            )
        }
    }
}