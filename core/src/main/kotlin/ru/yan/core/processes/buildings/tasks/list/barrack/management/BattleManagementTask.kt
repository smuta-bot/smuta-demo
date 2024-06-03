package ru.yan.core.processes.buildings.tasks.list.barrack.management

import org.springframework.stereotype.Component
import ru.yan.core.bot.BotTask
import ru.yan.core.bot.message.BotButtonMessage
import ru.yan.core.bot.message.InBotMessage
import ru.yan.core.bot.message.InlineButton
import ru.yan.core.bot.message.OutBotMessage
import ru.yan.core.description
import ru.yan.core.getMessengerId
import ru.yan.database.model.nsi.types.BattleStage
import ru.yan.database.model.smuta.dto.DtoUser
import ru.yan.database.service.BattleService
import java.util.HashMap

@Component
class BattleManagementTask(
    private val battleService: BattleService
): BotTask {

    override fun execute(message: InBotMessage, user: DtoUser, params: HashMap<String, Any>): OutBotMessage {
        val battle = battleService.getCurrentBattle(user)

        return battle?.let {
            BotButtonMessage(
                "Текущая стадия боя: ${it.battleStage.description()}",
                when(it.battleStage) {
                    BattleStage.Search -> {
                        listOf(
                            listOf(InlineButton("Отменить поиск", "search_cancel")),
                            listOf(InlineButton("Назад", "back"))
                        )
                    }
                    BattleStage.Preparation -> {
                        if (battle.isAttacker) {
                            listOf(
                                listOf(InlineButton("Отправить подразделение", "send_unit")),
                                listOf(InlineButton("Отозвать подразделение", "revoke_unit")),
                                listOf(InlineButton("Прекратить атаку", "stop_attack")),
                                listOf(InlineButton("Назад", "back"))
                            )
                        } else {
                            listOf(
                                listOf(InlineButton("Отправить подразделение", "send_unit")),
                                listOf(InlineButton("Отозвать подразделение", "revoke_unit")),
                                listOf(InlineButton("Капитулировать", "surrender")),
                                listOf(InlineButton("Назад", "back"))
                            )
                        }
                    }
                    BattleStage.Battle -> {
                        listOf(
                            listOf(InlineButton("Назад", "back"))
                        )
                    }
                    BattleStage.Result -> {
                        if (battle.isAttacker) {
                            listOf(
                                listOf(InlineButton("Прекратить атаку", "stop_attack")),
                                listOf(InlineButton("Назад", "back"))
                            )
                        } else {
                            listOf(
                                listOf(InlineButton("Капитулировать", "surrender")),
                                listOf(InlineButton("Назад", "back"))
                            )
                        }
                    }
                },
                null,
                user.getMessengerId()
            )
        } ?: BotButtonMessage(
            "Боёв нет",
            listOf(
                listOf(InlineButton("Поиск противника", "enemy_search")),
                listOf(InlineButton("Назад", "back"))
            ),
            null,
            user.getMessengerId()
        )
    }

    companion object {
        const val ID = "8a89fe8f-596e-45b1-8f95-6ce24376b79a"
    }
}