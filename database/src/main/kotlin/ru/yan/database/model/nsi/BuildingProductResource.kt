package ru.yan.database.model.nsi

import jakarta.persistence.*
import java.util.*

/**
 * Сущность "Ресурс, необходимый для производства продукта"
 */
@Entity
@Table(schema = "nsi", name = "building_product_resource")
class BuildingProductResource(
    /**
     * Кол-во ресурса, необходимого для производства одного товара [BuildingProduct]
     */
    var quantity: Double,

    /**
     * Ресурс, который необходим для производства продукта
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resource_uuid")
    var resource: Resource,

    /**
     * Продукт, для которого необходим ресурс
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "building_product_uuid")
    var buildingProduct: BuildingProduct
) {
    @Id
    @Column(name = "id", updatable = false)
    var id: UUID = UUID.randomUUID()
}