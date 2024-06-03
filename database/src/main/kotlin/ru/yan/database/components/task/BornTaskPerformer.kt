package ru.yan.database.components.task

import org.springframework.stereotype.Component
import ru.yan.database.model.houseUUID
import ru.yan.database.model.nsi.types.*
import ru.yan.database.model.operation.PopulationOperation
import ru.yan.database.model.populationUUID
import ru.yan.database.model.smuta.Country
import ru.yan.database.model.smuta.Notification
import ru.yan.database.model.smuta.areaEfficiency
import ru.yan.database.model.task.Task
import ru.yan.database.model.task.types.TaskType
import ru.yan.database.repository.nsi.NotificationCategoryRepository
import ru.yan.database.repository.operation.HappinessOperationRepository
import ru.yan.database.repository.operation.PopulationOperationRepository
import ru.yan.database.repository.smuta.CountryBuildingRepository
import ru.yan.database.repository.smuta.NotificationRepository
import ru.yan.database.repository.task.TaskRepository
import java.util.UUID
import kotlin.math.floor
import kotlin.random.Random

/**
 * Исполнитель, который рассчитывает кол-во рожденных детей
 */
@Component
class BornTaskPerformer(
    private val happinessOperationRepository: HappinessOperationRepository,
    private val populationOperationRepository: PopulationOperationRepository,
    private val countryBuildingRepository: CountryBuildingRepository,
    private val notificationRepository: NotificationRepository,
    private val notificationCategoryRepository: NotificationCategoryRepository,
    private val taskRepository: TaskRepository
): TaskPerformer<Nothing?> {

    override fun execute(task: Nothing?, country: Country) {
        // Реализация закрыта
    }
}