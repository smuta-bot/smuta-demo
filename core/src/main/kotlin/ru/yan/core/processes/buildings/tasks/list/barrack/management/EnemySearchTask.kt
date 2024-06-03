package ru.yan.core.processes.buildings.tasks.list.barrack.management

import org.springframework.stereotype.Component
import ru.yan.core.bot.BotTask
import ru.yan.core.bot.message.BotButtonMessage
import ru.yan.core.bot.message.InBotMessage
import ru.yan.core.bot.message.InlineButton
import ru.yan.core.bot.message.OutBotMessage
import ru.yan.core.getMessengerId
import ru.yan.database.exceptions.AlreadyExistsException
import ru.yan.database.exceptions.PermissionDeniedException
import ru.yan.database.model.smuta.dto.DtoUser
import ru.yan.database.service.BattleService
import java.util.HashMap

@Component
class EnemySearchTask(
    private val battleService: BattleService
): BotTask {

    override fun execute(message: InBotMessage, user: DtoUser, params: HashMap<String, Any>): OutBotMessage {
        return try {
            battleService.startBattle(user)

            BotButtonMessage(
                "Поиск противника запущен",
                listOf(listOf(InlineButton("Понял", "got_it"))),
                null,
                user.getMessengerId()
            )
        } catch (ex: PermissionDeniedException) {
            BotButtonMessage(
                "Поиск противника не возможен",
                listOf(listOf(InlineButton("Понял", "got_it"))),
                null,
                user.getMessengerId()
            )
        } catch (ex: AlreadyExistsException) {
            BotButtonMessage(
                "Поиск противника уже запущен",
                listOf(listOf(InlineButton("Понял", "got_it"))),
                null,
                user.getMessengerId()
            )
        } catch (ex: Throwable) {
            BotButtonMessage(
                "Что то пошло не так",
                listOf(listOf(InlineButton("Назад", "got_it"))),
                null,
                user.getMessengerId()
            )
        }
    }

    companion object {
        const val ID = "1a1d2e4e-2919-400a-ae7a-631243cad238"
    }
}