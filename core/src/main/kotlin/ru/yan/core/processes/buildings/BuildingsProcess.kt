package ru.yan.core.processes.buildings

import org.springframework.stereotype.Component
import ru.yan.core.bot.BotProcess
import ru.yan.core.bot.message.InBotMessage
import ru.yan.core.bot.message.BotButtonMessage
import ru.yan.core.bot.message.OutBotMessage
import ru.yan.core.getMessengerId
import ru.yan.database.model.smuta.dto.DtoUser

/**
 * Процесс для работы со зданиями пользователя
 */
@Component
class BuildingsProcess: BotProcess() {
    override fun start(message: InBotMessage, user: DtoUser): OutBotMessage {
        return BotButtonMessage(
            "Меню управления зданиями",
            null,
            listOf(listOf("Список построек"), listOf("Назад", "Построить")),
            user.getMessengerId()
        )
    }

    companion object {
        const val ID = "d1aa3254-f056-48bb-844c-ecaca79e326a"
    }
}