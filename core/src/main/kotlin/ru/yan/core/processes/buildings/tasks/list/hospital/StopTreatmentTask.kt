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
import java.util.HashMap

@Component
class StopTreatmentTask(
    private val hospitalService: HospitalService
): BotTask {

    override fun execute(message: InBotMessage, user: DtoUser, params: HashMap<String, Any>): OutBotMessage {
        return try {
            hospitalService.stopTreatment(user)

            BotButtonMessage(
                "Лечение населения прекращено",
                listOf(
                    listOf(InlineButton("Понял", "back"))
                ),
                null,
                user.getMessengerId()
            )
        } catch (ex: AlreadyExistsException) {
            BotButtonMessage(
                "Лечение населения уже прекращено",
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
        const val ID = "fbab6c47-563b-4b72-ac25-85998eb37491"
    }
}