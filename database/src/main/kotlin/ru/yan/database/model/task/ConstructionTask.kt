package ru.yan.database.model.task

import jakarta.persistence.*
import ru.yan.database.model.operation.BuildingOperation
import ru.yan.database.model.operation.StorageResourceOperation

/**
 * Сущность "Задача на постройку/ремонт/разрушение здания"
 */
@Entity
@Table(schema = "task", name = "construction_task")
class ConstructionTask(

    /**
     * Операция, породившая здание
     */
    @OneToOne
    @JoinColumn(name = "from_building_operation_uuid")
    val fromBuildingOperation: BuildingOperation?,

    /**
     * Операция, изменяющая здание
     */
    @OneToOne
    @JoinColumn(name = "to_building_operation_uuid")
    val toBuildingOperation: BuildingOperation,

    task: Task
) : AbstractTask(task) {

    /**
     * Ресурсы, взятые из хранилища
     */
    @OneToMany(mappedBy = "constructionTask", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    val storageResourceOperation: MutableList<StorageResourceOperation> = mutableListOf()
}