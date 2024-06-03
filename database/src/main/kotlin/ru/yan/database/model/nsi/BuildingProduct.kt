package ru.yan.database.model.nsi

import jakarta.persistence.*

/**
 * Сущность "Товар, который производит здание"
 */
@Entity
@Table(schema = "nsi", name = "building_product")
class BuildingProduct(
    /**
     * Кол-во производимого товара за уровень здания
     */
    quantityPerLevel: Double,
    building: NsiBuilding,
    resource: Resource
): AbstractBuildingComponent(building, resource, quantityPerLevel) {

    /**
     * Ресурсы и их количество, необходимые для производства ОДНОЙ штуки товара
     */
    @OneToMany(mappedBy = "buildingProduct", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    val productResources: List<BuildingProductResource> = listOf()

    /**
     * Побочные продукты
     */
    @OneToMany(mappedBy = "buildingProduct", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    val byproducts: List<Byproduct> = listOf()
}