package ru.yan.database.model.nsi

import jakarta.persistence.*
import java.util.*

/**
 * Компоненты, потребляемые/производимые зданием
 */
@MappedSuperclass
abstract class AbstractBuildingComponent(
    /**
     * Здание, для которого необходим ресурс
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "building_uuid")
    val building: NsiBuilding,

    /**
     * Ресурс, который необходим зданию
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resource_uuid")
    val resource: Resource,

    @Column(name = "quantity_per_level")
    val quantityPerLevel: Double,
) {
    @Id
    @Column(name = "id", updatable = false)
    val id: UUID = UUID.randomUUID()
}