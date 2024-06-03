package ru.yan.database.components

import org.springframework.stereotype.Component
import ru.yan.database.model.bot.BotCombatUnit
import ru.yan.database.model.bot.BotCountry
import ru.yan.database.model.bot.BotStorageResource
import ru.yan.database.model.nsi.types.BattleStage
import ru.yan.database.model.nsi.types.BattleType
import ru.yan.database.model.nsi.types.CountryStateOperationType
import ru.yan.database.model.operation.BattleOperation
import ru.yan.database.model.operation.BattlefieldEffectOperation
import ru.yan.database.model.operation.CountryStateOperation
import ru.yan.database.model.smuta.Country
import ru.yan.database.model.smuta.Notification
import ru.yan.database.model.task.BattleTask
import ru.yan.database.model.task.Task
import ru.yan.database.model.task.types.TaskType
import ru.yan.database.model.warUUID
import ru.yan.database.repository.bot.BotCombatUnitRepository
import ru.yan.database.repository.bot.BotCountryRepository
import ru.yan.database.repository.bot.BotStorageResourceRepository
import ru.yan.database.repository.nsi.BattlefieldRepository
import ru.yan.database.repository.nsi.NotificationCategoryRepository
import ru.yan.database.repository.nsi.NsiCombatUnitRepository
import ru.yan.database.repository.nsi.ResourceRepository
import ru.yan.database.repository.operation.BattleOperationRepository
import ru.yan.database.repository.operation.BattlefieldEffectOperationRepository
import ru.yan.database.repository.operation.CountryStateOperationRepository
import ru.yan.database.repository.smuta.CombatUnitRepository
import ru.yan.database.repository.smuta.CountryRepository
import ru.yan.database.repository.smuta.NotificationRepository
import ru.yan.database.repository.task.BattleTaskRepository
import ru.yan.database.repository.task.TaskRepository
import java.util.*
import kotlin.random.Random

/**
 * Калькулятор, рассчитывающий кто станет противником для страны
 */
@Component
class BattleSearchCalculator(
    private val countryRepository: CountryRepository,
    private val taskRepository: TaskRepository,
    private val battleOperationRepository: BattleOperationRepository,
    private val resourceRepository: ResourceRepository,
    private val nsiCombatUnitRepository: NsiCombatUnitRepository,
    private val combatUnitRepository: CombatUnitRepository,
    private val battlefieldRepository: BattlefieldRepository,
    private val botStorageResourceRepository: BotStorageResourceRepository,
    private val botCombatUnitRepository: BotCombatUnitRepository,
    private val botCountryRepository: BotCountryRepository,
    private val battlefieldEffectOperationRepository: BattlefieldEffectOperationRepository,
    private val battleTaskRepository: BattleTaskRepository,
    private val countryStateOperationRepository: CountryStateOperationRepository,
    private val notificationRepository: NotificationRepository,
    private val notificationCategoryRepository: NotificationCategoryRepository
) {

    fun calculate(country: Country) {
        // Реализация закрыта
    }
}