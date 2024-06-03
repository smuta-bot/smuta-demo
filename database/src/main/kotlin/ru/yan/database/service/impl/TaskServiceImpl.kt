package ru.yan.database.service.impl

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.yan.database.components.task.*
import ru.yan.database.model.task.types.TaskType
import ru.yan.database.repository.task.TaskRepository
import ru.yan.database.service.TaskService
import java.util.*

@Service
class TaskServiceImpl(
    private val taskRepository: TaskRepository,
    private val constructionTaskPerformer: ConstructionTaskPerformer,
    private val productionTaskPerformer: ProductionTaskPerformer,
    private val pensionTaskPerformer: PensionTaskPerformer,
    private val treatmentTaskPerformer: TreatmentTaskPerformer,
    private val buildingSalaryTaskPerformer: BuildingSalaryTaskPerformer,
    private val bornTaskPerformer: BornTaskPerformer,
    private val foodTaskPerformer: FoodTaskPerformer,
    private val deathTaskPerformer: DeathTaskPerformer,
    private val infectionTaskPerformer: InfectionTaskPerformer,
    private val growUpTaskPerformer: GrowUpTaskPerformer,
    private val thirstTaskPerformer: ThirstTaskPerformer,
    private val buildingDecayTaskPerformer: BuildingDecayTaskPerformer,
    private val profitTaskPerformer: ProfitTaskPerformer,
    private val battleTaskPerformer: BattleTaskPerformer,
    private val combatUnitTaskPerformer: CombatUnitTaskPerformer,
    private val combatUnitSalaryTaskPerformer: CombatUnitSalaryTaskPerformer,
    private val combatUnitDecayTaskPerformer: CombatUnitDecayTaskPerformer
): TaskService {

    @Transactional
    override fun processing() {
        val tasks = taskRepository.findToComplete().map { task ->
            when (task.type) {
                TaskType.Construction -> constructionTaskPerformer.execute(task.constructionTask!!, task.country)
                TaskType.BuildingSalary -> buildingSalaryTaskPerformer.execute(task.buildingSalaryTask!!, task.country)
                TaskType.Production -> productionTaskPerformer.execute(task.productionTask!!, task.country)
                TaskType.Treatment -> treatmentTaskPerformer.execute(task.treatmentTask!!, task.country)
                TaskType.BuildingDecay -> buildingDecayTaskPerformer.execute(task.buildingDecayTask!!, task.country)
                TaskType.Battle -> battleTaskPerformer.execute(task.battleTask!!, task.country)
                TaskType.CombatUnit -> combatUnitTaskPerformer.execute(task.combatUnitTask!!, task.country)
                TaskType.CombatUnitSalary -> combatUnitSalaryTaskPerformer.execute(task.combatUnitSalaryTask!!, task.country)
                TaskType.CombatUnitDecay -> combatUnitDecayTaskPerformer.execute(task.combatUnitDecayTask!!, task.country)
                TaskType.Born -> bornTaskPerformer.execute(null, task.country)
                TaskType.Food -> foodTaskPerformer.execute(null, task.country)
                TaskType.Death -> deathTaskPerformer.execute(null, task.country)
                TaskType.Infection -> infectionTaskPerformer.execute(null, task.country)
                TaskType.GrowUp -> growUpTaskPerformer.execute(null, task.country)
                TaskType.Thirst -> thirstTaskPerformer.execute(null, task.country)
                TaskType.Pension -> pensionTaskPerformer.execute(null, task.country)
                TaskType.Profit -> profitTaskPerformer.execute(null, task.country)
            }

            task.apply { deletedAt = Date() }
        }

        taskRepository.saveAll(tasks)
    }
}