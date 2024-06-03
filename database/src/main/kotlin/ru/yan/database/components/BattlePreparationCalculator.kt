package ru.yan.database.components

import org.springframework.stereotype.Component
import ru.yan.database.model.nsi.types.*
import ru.yan.database.model.operation.*
import ru.yan.database.model.smuta.Country
import ru.yan.database.model.smuta.workplacesRate
import ru.yan.database.model.task.BattleTask
import ru.yan.database.model.task.Task
import ru.yan.database.model.task.types.TaskType
import ru.yan.database.repository.bot.BotCombatUnitRepository
import ru.yan.database.repository.operation.*
import ru.yan.database.repository.smuta.CombatUnitRepository
import ru.yan.database.repository.smuta.CountryRepository
import ru.yan.database.repository.smuta.PopulationRepository
import ru.yan.database.repository.smuta.StorageResourceRepository
import ru.yan.database.repository.task.BattleTaskRepository
import ru.yan.database.repository.task.TaskRepository
import kotlin.math.min
import kotlin.random.Random

/**
 * Калькулятор, производящий предварительные вычисления перед началом сражения
 */
@Component
class BattlePreparationCalculator(
    private val countryRepository: CountryRepository,
    private val countryFightingUnitRepository: CountryFightingUnitRepository,
    private val botCombatUnitRepository: BotCombatUnitRepository,
    private val combatUnitRepository: CombatUnitRepository,
    private val storageResourceRepository: StorageResourceRepository,
    private val storageResourceOperationRepository: StorageResourceOperationRepository,
    private val populationRepository: PopulationRepository,
    private val populationOperationRepository: PopulationOperationRepository,
    private val botFightingUnitRepository: BotFightingUnitRepository,
    private val combatUnitOperationRepository: CombatUnitOperationRepository,
    private val taskRepository: TaskRepository,
    private val battleTaskRepository: BattleTaskRepository
) {

    fun calculate(country: Country, battleTask: BattleTask) {
        // Реализация закрыта
    }
}