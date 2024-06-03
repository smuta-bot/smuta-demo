package ru.yan.database.model.nsi

import jakarta.persistence.*

/**
 * Сущность "Строение"
 */
@Entity
@Table(schema = "nsi", name = "building")
class NsiBuilding(
    /**
     * Кол-во рабочих мест на уровень
     */
    @Column(name = "workplaces_by_level")
    val workplacesByLevel: Int,

    /**
     * Площадь здания на уровень
     */
    @Column(name = "area_by_level")
    val areaByLevel: Double,

    /**
     * Цена за уровень
     */
    @Column(name = "price_for_level")
    val priceForLevel: Double,

    /**
     * Время строительства(в секундах)
     */
    @Column(name = "construction_time_by_level")
    val constructionTimeByLevel: Int,

    /**
     * Время разрушения(в секундах)
     */
    @Column(name = "destroy_time")
    val destroyTime: Int,

    /**
     * Цена разрушения за уровень
     */
    @Column(name = "destroy_price_for_level")
    val destroyPriceForLevel: Double,

    /**
     * Цена консервации за уровень
     */
    @Column(name = "mothballed_price_by_level")
    val mothballedPriceByLevel: Double,

    /**
     * Время консервации
     */
    @Column(name = "mothballed_time")
    val mothballedTime: Int,

    /**
     * Зарплата одного сотрудника
     */
    @Column(name = "employee_salary")
    var employeeSalary: Double,

    /**
     * Время производства продукции(в секундах)
     */
    @Column(name = "production_time")
    var productionTime: Int,

    name: String,
    description: String
): AbstractSimpleDictionary(name, description) {
    /**
     * Ресурсы, необходимые для строительства
     */
    @OneToMany(mappedBy = "building", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    val buildingResources: MutableList<BuildingResource> = mutableListOf()

    /**
     * Производимая продукция
     */
    @OneToMany(mappedBy = "building", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    val buildingProducts: MutableList<BuildingProduct> = mutableListOf()
}