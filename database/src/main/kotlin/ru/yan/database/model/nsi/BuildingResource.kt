package ru.yan.database.model.nsi

import jakarta.persistence.Entity
import jakarta.persistence.Table

/**
 * Сущность "Ресурс, который необходим для постройки/улучшения здания"
 */
@Entity
@Table(schema = "nsi", name = "building_resource")
class BuildingResource(
    /**
     * Кол-во ресурса необходимое для здания на уровень
     */
    quantityPerLevel: Double,
    building: NsiBuilding,
    resource: Resource
): AbstractBuildingComponent(building, resource, quantityPerLevel)