package ru.yan.database.model.operation

import io.hypersistence.utils.hibernate.type.basic.PostgreSQLEnumType
import jakarta.persistence.*
import org.hibernate.annotations.Type
import ru.yan.database.model.nsi.Resource
import ru.yan.database.model.nsi.types.StorageResourceOperationType
import ru.yan.database.model.smuta.Country
import ru.yan.database.model.task.BattleTask
import ru.yan.database.model.task.CombatUnitTask
import ru.yan.database.model.task.ConstructionTask
import ru.yan.database.model.task.ProductionTask
import java.util.*

/**
 * Сущность "Операция над ресурсом в хранилище"
 */
@Entity
@Table(schema = "operation", name = "storage_resource_operation")
class StorageResourceOperation(
    /**
     * Тип операции
     */
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "nsi.StorageResourceOperationType")
    @Type(PostgreSQLEnumType::class)
    val type: StorageResourceOperationType,

    /**
     * Количественные изменения по ресурсу(больше 0 = поступление, меньше 0 = извлечение)
     */
    val quantity: Double,

    /**
     * Страна
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_uuid")
    val country: Country,

    /**
     * Ресурс
     */
    @OneToOne
    @JoinColumn(name = "resource_uuid")
    val resource: Resource
) {

    /**
     * Идентификатор
     */
    @Id
    @Column(name = "id", updatable = false)
    val id: UUID = UUID.randomUUID()

    /**
     * Задача на строительство, списавшая ресурсы
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "construction_task_uuid")
    var constructionTask: ConstructionTask? = null

    /**
     * Задача на производство, списавшая ресурсы(или добавившая)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "production_task_uuid")
    var productionTask: ProductionTask? = null

    /**
     * Задача на сражение, списавшая ресурсы(или добавившая)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "battle_task_uuid")
    var battleTask: BattleTask? = null

    /**
     * Задача на учения, списавшая ресурсы(или добавившая)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "combat_unit_task_uuid")
    var combatUnitTask: CombatUnitTask? = null

    /**
     * Дата создания
     */
    @Column(name = "created_at")
    val createdAt: Date = Date()
}