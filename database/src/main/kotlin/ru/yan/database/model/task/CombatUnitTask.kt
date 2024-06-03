package ru.yan.database.model.task

import jakarta.persistence.*
import ru.yan.database.model.operation.CombatUnitOperation
import ru.yan.database.model.operation.StorageResourceOperation

/**
 * Сущность "Задача на формирование/учения/расформирование боевого подразделения"
 */
@Entity
@Table(schema = "task", name = "combat_unit_task")
class CombatUnitTask(

    /**
     * Операция, породившая боевое подразделение
     */
    @OneToOne
    @JoinColumn(name = "from_combat_unit_operation")
    val fromCombatUnitOperation: CombatUnitOperation?,

    /**
     * Операция, изменяющая боевое подразделение
     */
    @OneToOne
    @JoinColumn(name = "to_combat_unit_operation")
    val toCombatUnitOperation: CombatUnitOperation,

    task: Task
): AbstractTask(task) {

    /**
     * Ресурсы, взятые из хранилища
     */
    @OneToMany(mappedBy = "combatUnitTask", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    val storageResourceOperation: MutableList<StorageResourceOperation> = mutableListOf()
}