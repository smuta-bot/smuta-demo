package ru.yan.core.processes.buildings.tasks.list.barrack.create

import org.springframework.stereotype.Component
import ru.yan.core.bot.BotTask
import ru.yan.core.bot.message.*
import ru.yan.core.getMessengerId
import ru.yan.database.model.smuta.dto.DtoUser
import java.util.HashMap

@Component
class ChooseNumberPeopleTask: BotTask {

    override fun execute(message: InBotMessage, user: DtoUser, params: HashMap<String, Any>): OutBotMessage {
        return BotButtonMessage(
            "Введите кол-во людей в подразделении",
            listOf(
                listOf(InlineButton("Назад", "back"))
            ),
            null,
            user.getMessengerId()
        )
    }

    companion object {
        const val ID = "7688a495-b7c8-4c8c-ac1a-afb9d61acb45"
    }
}