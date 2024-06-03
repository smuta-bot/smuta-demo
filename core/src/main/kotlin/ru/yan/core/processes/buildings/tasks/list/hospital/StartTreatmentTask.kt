package ru.yan.core.processes.buildings.tasks.list.hospital

import org.springframework.stereotype.Component
import ru.yan.core.bot.BotTask
import ru.yan.core.bot.message.InBotMessage
import ru.yan.core.bot.message.BotButtonMessage
import ru.yan.core.bot.message.InlineButton
import ru.yan.core.bot.message.OutBotMessage
import ru.yan.core.getMessengerId
import ru.yan.database.exceptions.AlreadyExistsException
import ru.yan.database.model.smuta.dto.DtoUser
import ru.yan.database.service.HospitalService
import java.util.*

@Component
class StartTreatmentTask(
    private val hospitalService: HospitalService
): BotTask {

    override fun execute(message: InBotMessage, user: DtoUser, params: HashMap<String, Any>): OutBotMessage {
        return try {
            hospitalService.startTreatment(user)

            BotButtonMessage(
                "Лечение населения запущено",
                listOf(
                    listOf(InlineButton("Понял", "back"))
                ),
                null,
                user.getMessengerId()
            )
        } catch (ex: AlreadyExistsException) {
            BotButtonMessage(
                "Лечение населения уже запущено",
                listOf(listOf(InlineButton("Понял", "back"))),
                null,
                user.getMessengerId()
            )
        } catch (ex: Throwable) {
            BotButtonMessage(
                "Что то пошло не так",
                listOf(
                    listOf(InlineButton("Назад", "back"))
                ),
                null,
                user.getMessengerId()
            )
        }
    }

    companion object {
        const val ID = "4249c358-e8b9-49da-8f49-a740f7283a8f"
    }
}