package ru.yan.database.model.nsi

import jakarta.persistence.*
import java.util.*

/**
 * Сущность "Ресурс, который требуется подразделению"
 */
@Entity
@Table(schema = "nsi", name = "combat_unit_resource")
class CombatUnitResource(
    /**
     * Кол-во ресурса
     */
    val quantity: Int,

    /**
     * Подразделение, которому предназначен ресурс
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "combat_unit_uuid")
    val combatUnit: NsiCombatUnit?,

    /**
     * Ресурс
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resource_uuid")
    val resource: Resource
) {
    @Id
    @Column(name = "id", updatable = false)
    val id: UUID = UUID.randomUUID()
}