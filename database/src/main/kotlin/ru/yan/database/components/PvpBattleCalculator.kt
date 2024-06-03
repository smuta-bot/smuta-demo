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
import ru.yan.database.repository.nsi.NotificationCategoryRepository
import ru.yan.database.repository.operation.*
import ru.yan.database.repository.smuta.CountryRepository
import ru.yan.database.repository.smuta.NotificationRepository
import ru.yan.database.repository.task.BattleTaskRepository
import ru.yan.database.repository.task.TaskRepository
import java.util.*
import kotlin.math.abs

/**
 * Калькулятор, рассчитывающий результат PVP сражения
 */
@Component
class PvpBattleCalculator(
    private val countryFightingUnitRepository: CountryFightingUnitRepository,
    private val populationOperationRepository: PopulationOperationRepository,
    private val countryStateOperationRepository: CountryStateOperationRepository,
    private val happinessOperationRepository: HappinessOperationRepository,
    private val notificationRepository: NotificationRepository,
    private val notificationCategoryRepository: NotificationCategoryRepository,
    private val combatUnitOperationRepository: CombatUnitOperationRepository,
    private val battleTaskRepository: BattleTaskRepository,
    private val countryRepository: CountryRepository,
    private val taskRepository: TaskRepository
): BattleCalculator() {

    fun calculate(country: Country, battleOperation: BattleOperation) {
        // Реализация закрыта
    }
}