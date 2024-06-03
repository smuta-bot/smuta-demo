package ru.yan.core.processes.buildings.tasks.list.hospital

import org.springframework.stereotype.Component
import ru.yan.core.bot.BotTask
import ru.yan.core.bot.message.InBotMessage
import ru.yan.core.bot.message.BotButtonMessage
import ru.yan.core.bot.message.BotTextMessage
import ru.yan.core.bot.message.InlineButton
import ru.yan.core.bot.message.OutBotMessage
import ru.yan.core.description
import ru.yan.core.getMessengerId
import ru.yan.database.model.nsi.types.BuildingStatus
import ru.yan.database.model.smuta.dto.DtoUser
import ru.yan.database.service.HospitalService
import java.util.*

@Component
class DescribeHospitalTask(
    private val hospitalService: HospitalService
): BotTask {

    override fun execute(message: InBotMessage, user: DtoUser, params: HashMap<String, Any>): OutBotMessage {
        return hospitalService.getCountryHospital(user)?.let {
            val keyboard = when(it.status) {
                BuildingStatus.Ready -> {
                    listOf(
                        listOf(InlineButton("Изменить уровень", "level_change")),
                        listOf(InlineButton("Начать лечение", "start_treatment"))
                    )
                }
                BuildingStatus.ProductionProcess -> {
                    listOf(
                        listOf(InlineButton("Изменить уровень", "level_change")),
                        listOf(InlineButton("Отменить лечение", "stop_treatment"))
                    )
                }
                BuildingStatus.ConstructionProcess,
                BuildingStatus.RenovationProcess -> {
                    listOf(
                        listOf(InlineButton("Остановить строительство", "stop_construction"))
                    )
                }
                else -> listOf()
            }.plus(
                listOf(
                    listOf(InlineButton("Назад", "back"))
                )
            )

            BotButtonMessage(
                it.description(),
                keyboard,
                null,
                user.getMessengerId()
            )
        } ?: BotTextMessage("Здание не найдено", user.getMessengerId())
    }

    companion object {
        const val ID = "dab4397b-a411-445c-baa2-213087bae78a"
    }
}