package ru.yan.database.model.nsi

import jakarta.persistence.*
import java.util.*

/**
 * Сущность "Побочный продукт, получаемый при производстве основного"
 */
@Entity
@Table(schema = "nsi", name = "byproduct")
class Byproduct(
    @Column(name = "quantity_per_product")
    val quantityPerProduct: Double,

    /**
     * Описание ресурса
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resource_uuid")
    val resource: Resource,

    /**
     * Основное продукт, при производстве которого получается этот
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "building_product_uuid")
    val buildingProduct: BuildingProduct
) {
    @Id
    @Column(name = "id", updatable = false)
    val id: UUID = UUID.randomUUID()
}