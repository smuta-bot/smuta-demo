package ru.yan.telegram

import org.springframework.stereotype.Service
import ru.yan.core.service.MessageProcessingService

@Service
class MessageProcessingServiceImpl: MessageProcessingService() {
    override fun getUser(id: Long) = userService.getOrCreateTelegram(id)
}