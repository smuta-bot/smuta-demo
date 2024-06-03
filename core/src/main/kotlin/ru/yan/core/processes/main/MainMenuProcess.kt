package ru.yan.core.processes.main

import org.springframework.stereotype.Component
import ru.yan.core.bot.BotProcess
import ru.yan.core.bot.message.InBotMessage
import ru.yan.core.bot.message.BotButtonMessage
import ru.yan.core.bot.message.BotTextMessage
import ru.yan.core.bot.message.OutBotMessage
import ru.yan.core.description
import ru.yan.core.getMessengerId
import ru.yan.database.model.smuta.dto.DtoUser
import ru.yan.database.service.CountryService

/**
 * Процесс запуска основного меню и обработки команд
 */
@Component
class MainMenuProcess(
    private val countryService: CountryService
): BotProcess() {
    override fun start(message: InBotMessage, user: DtoUser): OutBotMessage {
        return countryService.getByUser(user)?.let {
            BotButtonMessage(
                it.description(),
                null,
                listOf(listOf("Здания", "Рынок", "Управление"), listOf("Уведомления")),
                user.getMessengerId()
            )
        } ?: BotTextMessage("Пользователь не найден", user.getMessengerId())
    }

    companion object {
        const val ID = "7189d03a-7917-47fe-a0a5-ff32e4275483"
    }
}