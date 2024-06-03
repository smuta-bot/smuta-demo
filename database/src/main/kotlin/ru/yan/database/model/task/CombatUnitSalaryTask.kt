package ru.yan.database.model.task

import jakarta.persistence.*
import ru.yan.database.model.nsi.NsiCombatUnit

/**
 * Сущность "Задача на выплату зарплаты воинам"
 */
@Entity
@Table(schema = "task", name = "combat_unit_salary_task")
class CombatUnitSalaryTask(
    /**
     * Подразделение, воинам которого пришло время выплачивать зарплату
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "combat_unit_uuid")
    val nsiCombatUnit: NsiCombatUnit,

    task: Task
): AbstractTask(task)