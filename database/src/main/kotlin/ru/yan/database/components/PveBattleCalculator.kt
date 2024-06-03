package ru.yan.database.components

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import ru.yan.database.model.nsi.types.*
import ru.yan.database.model.operation.*
import ru.yan.database.model.smuta.Country
import ru.yan.database.model.smuta.Notification
import ru.yan.database.model.task.BattleTask
import ru.yan.database.model.task.Task
import ru.yan.database.model.task.types.TaskType
import ru.yan.database.model.warUUID
import ru.yan.database.repository.bot.BotCombatUnitRepository
import ru.yan.database.repository.bot.BotCountryRepository
import ru.yan.database.repository.nsi.NotificationCategoryRepository
import ru.yan.database.repository.operation.CombatUnitOperationRepository
import ru.yan.database.repository.operation.CountryFightingUnitRepository
import ru.yan.database.repository.operation.HappinessOperationRepository
import ru.yan.database.repository.operation.PopulationOperationRepository
import ru.yan.database.repository.smuta.NotificationRepository
import ru.yan.database.repository.task.BattleTaskRepository
import ru.yan.database.repository.task.TaskRepository
import java.util.*
import kotlin.math.abs

/**
 * Калькулятор, рассчитывающий результат PVE сражения
 */
@Component
class PveBattleCalculator(
    private val countryFightingUnitRepository: CountryFightingUnitRepository,
    private val populationOperationRepository: PopulationOperationRepository,
    private val happinessOperationRepository: HappinessOperationRepository,
    private val combatUnitOperationRepository: CombatUnitOperationRepository,
    private val botCombatUnitRepository: BotCombatUnitRepository,
    private val botCountryRepository: BotCountryRepository,
    private val battleTaskRepository: BattleTaskRepository,
    private val notificationRepository: NotificationRepository,
    private val notificationCategoryRepository: NotificationCategoryRepository,
    private val taskRepository: TaskRepository
): BattleCalculator() {

    fun calculate(country: Country, battleOperation: BattleOperation) {
        // Реализация закрыта
    }

    private fun force(unit: BotFightingUnit, effects: List<BattlefieldEffectOperation>): Double {
        // Реализация закрыта
        return 0.0
    }
}