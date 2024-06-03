package ru.yan.database.components.task

import org.springframework.stereotype.Component
import ru.yan.database.components.*
import ru.yan.database.model.nsi.types.BattleStage
import ru.yan.database.model.nsi.types.BattleType
import ru.yan.database.model.smuta.Country
import ru.yan.database.model.task.BattleTask

/**
 * Исполнитель, который отвечает за сражения
 */
@Component
class BattleTaskPerformer(
    private val battleSearchCalculator: BattleSearchCalculator,
    private val battlePreparationCalculator: BattlePreparationCalculator,
    private val pvpBattleCalculator: PvpBattleCalculator,
    private val pveBattleCalculator: PveBattleCalculator,
    private val battleResultCalculator: BattleResultCalculator
): TaskPerformer<BattleTask> {

    override fun execute(task: BattleTask, country: Country) {
        // Реализация закрыта
    }
}