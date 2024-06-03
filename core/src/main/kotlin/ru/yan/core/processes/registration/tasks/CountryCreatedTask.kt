package ru.yan.core.processes.registration.tasks

import org.hibernate.exception.ConstraintViolationException
import org.springframework.stereotype.Component
import ru.yan.core.bot.message.InBotMessage
import ru.yan.core.bot.message.BotButtonMessage
import ru.yan.core.bot.message.InlineButton
import ru.yan.core.bot.BotTask
import ru.yan.core.bot.message.BotTextMessage
import ru.yan.core.bot.message.OutBotMessage
import ru.yan.core.getMessengerId
import ru.yan.database.model.smuta.dto.DtoUser
import ru.yan.database.service.CountryService

@Component
class CountryCreatedTask(
    private val countryService: CountryService
): BotTask {
    override fun execute(message: InBotMessage, user: DtoUser, params: HashMap<String, Any>): OutBotMessage {
        return try {
            countryService.create(user, message.text!!)

            BotButtonMessage(
                "Страна создана, хранилища наполнены. Люди ждут приказаний",
                listOf(listOf(InlineButton("Понял", "Go"))),
                null,
                user.getMessengerId()
            )
        } catch (ex: Throwable) {
            if (ex.cause is ConstraintViolationException) {
                BotButtonMessage(
                    "У тебя уже есть действующая страна. Население надеется на вас!!",
                    listOf(listOf(InlineButton("Понял", "Go"))),
                    null,
                    user.getMessengerId()
                )
            } else {
                BotTextMessage(
                    "Что то пошло не так",
                    user.getMessengerId()
                )
            }
        }
    }

    companion object {
        const val ID = "5db61d3a-1d37-4c5c-bd9c-8ba5b14ba726"
    }
}