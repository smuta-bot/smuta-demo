package ru.yan.database.components

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import ru.yan.database.model.nsi.types.BattleStage
import ru.yan.database.model.nsi.types.BattleType
import ru.yan.database.model.nsi.types.CountryStateOperationType
import ru.yan.database.model.operation.BattleOperation
import ru.yan.database.model.operation.CountryStateOperation
import ru.yan.database.model.smuta.Country
import ru.yan.database.model.smuta.Notification
import ru.yan.database.model.task.BattleTask
import ru.yan.database.model.task.Task
import ru.yan.database.model.task.types.TaskType
import ru.yan.database.model.warUUID
import ru.yan.database.repository.bot.BotCountryRepository
import ru.yan.database.repository.nsi.BattlefieldRepository
import ru.yan.database.repository.nsi.NotificationCategoryRepository
import ru.yan.database.repository.operation.BattleOperationRepository
import ru.yan.database.repository.operation.CountryStateOperationRepository
import ru.yan.database.repository.smuta.CountryRepository
import ru.yan.database.repository.smuta.NotificationRepository
import ru.yan.database.repository.task.BattleTaskRepository
import ru.yan.database.repository.task.TaskRepository
import java.util.*

/**
 * Калькулятор, рассчитывающий результаты сражения
 */
@Component
class BattleResultCalculator(
    private val battlefieldRepository: BattlefieldRepository,
    private val battleOperationRepository: BattleOperationRepository,
    private val battleTaskRepository: BattleTaskRepository,
    private val countryStateOperationRepository: CountryStateOperationRepository,
    private val taskRepository: TaskRepository,
    private val countryRepository: CountryRepository,
    private val notificationRepository: NotificationRepository,
    private val notificationCategoryRepository: NotificationCategoryRepository,
    private val botCountryRepository: BotCountryRepository
) {
    fun calculate(country: Country, battleOperation: BattleOperation) {
        // Реализация закрыта
    }
}