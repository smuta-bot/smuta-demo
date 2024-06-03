package ru.yan.database.model.task

import jakarta.persistence.*
import ru.yan.database.model.nsi.NsiCombatUnit

/**
 * Сущность "Задача на гниение боевого подразделения"
 */
@Entity
@Table(schema = "task", name = "combat_unit_decay_task")
class CombatUnitDecayTask(
    /**
     * Подразделение, которое "гниет"(теряет опыт)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "combat_unit_uuid")
    val nsiCombatUnit: NsiCombatUnit,

    task: Task
): AbstractTask(task)