package ru.yan.core.processes.registration

import org.springframework.stereotype.Component
import ru.yan.core.bot.BotProcess
import ru.yan.core.bot.message.InBotMessage
import ru.yan.core.bot.message.BotButtonMessage
import ru.yan.core.bot.message.InlineButton
import ru.yan.core.bot.message.OutBotMessage
import ru.yan.core.getMessengerId
import ru.yan.database.model.smuta.dto.DtoUser

/**
 * Процесс регистрации пользователя, создания страны, первого знакомства пользователя с ботом и т.п.
 */
@Component
class RegistrationProcess: BotProcess() {
    override fun start(message: InBotMessage, user: DtoUser): OutBotMessage {
        return BotButtonMessage(
            "Приветствую, воин!!! Это бот - симулятор страны.",
            listOf(listOf(InlineButton("Понял", "Go"))),
            null,
            user.getMessengerId()
        )
    }

    companion object {
        const val ID = "e85c3113-14fc-4a20-9d4c-54ea2b165786"
    }
}