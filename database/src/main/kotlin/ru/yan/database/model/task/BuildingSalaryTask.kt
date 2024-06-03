package ru.yan.database.model.task

import jakarta.persistence.*
import ru.yan.database.model.nsi.NsiBuilding

/**
 * Сущность "Задача на выплату зарплаты зданием"
 */
@Entity
@Table(schema = "task", name = "building_salary_task")
class BuildingSalaryTask(
    /**
     * Здание, работникам которого пришло время выплачивать зарплату
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "building_uuid")
    val nsiBuilding: NsiBuilding,

    task: Task
): AbstractTask(task)