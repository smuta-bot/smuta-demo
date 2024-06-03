package ru.yan.database.model.task

import jakarta.persistence.*
import ru.yan.database.model.nsi.Resource
import java.util.*

/**
 * Сущность "Задача на производство товара"
 */
@Entity
@Table(schema = "task", name = "production_product")
class ProductionProduct(
    /**
     * Кол-во производимого продукта
     */
    val quantity: Double,

    /**
     * Производимый продукт
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resource_uuid")
    val resource: Resource,

    /**
     * Задача, в результате которой должен быть произведен продукт
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "production_task_uuid")
    val productionTask: ProductionTask
) {
    /**
     * Идентификатор
     */
    @Id
    @Column(name = "id", updatable = false)
    val id: UUID = UUID.randomUUID()

    /**
     * Время остановки производства
     */
    @Column(name = "deleted_at")
    var deletedAt: Date? = null
}