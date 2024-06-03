package ru.yan.database.model.task

import io.hypersistence.utils.hibernate.type.basic.PostgreSQLEnumType
import jakarta.persistence.*
import org.hibernate.annotations.Type
import ru.yan.database.model.smuta.Country
import ru.yan.database.model.task.types.TaskType
import java.util.*

/**
 * Сущность "Задача, выполняемая через определенный промежуток времени"
 */
@Entity
@Table(schema = "task", name = "task")
class Task(
    /**
     * Тип задачи
     */
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "task.TaskType")
    @Type(PostgreSQLEnumType::class)
    val type: TaskType,

    /**
     * Время выполнения задачи(в секундах)
     */
    val duration: Int,

    /**
     * Страна, которой принадлежит задача
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_uuid")
    var country: Country
) {
    /**
     * Идентификатор
     */
    @Id
    @Column(name = "id", updatable = false)
    val id: UUID = UUID.randomUUID()

    /**
     * Приостановлено ли выполнение задачи
     */
    var paused: Boolean = false

    /**
     * Задача на строительство
     */
    @OneToOne(mappedBy = "task", fetch = FetchType.LAZY)
    var constructionTask: ConstructionTask? = null

    /**
     * Задача на производство
     */
    @OneToOne(mappedBy = "task", fetch = FetchType.LAZY)
    var productionTask: ProductionTask? = null

    /**
     * Задача на лечение
     */
    @OneToOne(mappedBy = "task", fetch = FetchType.LAZY)
    var treatmentTask: TreatmentTask? = null

    /**
     * Задача на выплату зарплаты работникам
     */
    @OneToOne(mappedBy = "task", fetch = FetchType.LAZY)
    var buildingSalaryTask: BuildingSalaryTask? = null

    /**
     * Задача на гниение здания
     */
    @OneToOne(mappedBy = "task", fetch = FetchType.LAZY)
    var buildingDecayTask: BuildingDecayTask? = null

    /**
     * Задача на сражение
     */
    @OneToOne(mappedBy = "task", fetch = FetchType.LAZY)
    var battleTask: BattleTask? = null

    /**
     * Задача на работу с боевым подразделением
     */
    @OneToOne(mappedBy = "task", fetch = FetchType.LAZY)
    var combatUnitTask: CombatUnitTask? = null

    /**
     * Задача на выплату зарплат бойцам подразделения
     */
    @OneToOne(mappedBy = "task", fetch = FetchType.LAZY)
    var combatUnitSalaryTask: CombatUnitSalaryTask? = null

    /**
     * Задача на гниение подразделения(потерю опыта)
     */
    @OneToOne(mappedBy = "task", fetch = FetchType.LAZY)
    var combatUnitDecayTask: CombatUnitDecayTask? = null

    /**
     * Время запуска задачи
     */
    @Column(name = "started_at")
    var startedAt: Date = Date()

    /**
     * Время завершения задачи
     */
    @Column(name = "deleted_at")
    var deletedAt: Date? = null
}