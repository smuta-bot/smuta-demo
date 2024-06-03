package ru.yan.database.model.task

import jakarta.persistence.*
import ru.yan.database.model.nsi.NsiBuilding


/**
 * Сущность "Задача на гниение здания"
 */
@Entity
@Table(schema = "task", name = "building_decay_task")
class BuildingDecayTask(
    /**
     * Здание, которое гниет
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "building_uuid")
    val nsiBuilding: NsiBuilding,

    task: Task
): AbstractTask(task)