package ru.yan.database.model.nsi

import jakarta.persistence.*
import ru.yan.database.model.operation.BotFightingUnit

/**
 * Сущность "Боевое подразделение"
 */
@Entity
@Table(schema = "nsi", name = "combat_unit")
class NsiCombatUnit(
    /**
     * Урон
     */
    val damage: Int,

    /**
     * Скорость
     */
    val speed: Int,

    /**
     * Броня
     */
    val armor: Int,

    /**
     * Радиус атаки
     */
    @Column(name = "attack_range")
    val attackRange: Int,

    /**
     * Зарплата одного воина
     */
    val salary: Double,

    /**
     * Боевое подразделение, против которого это подразделение наиболее эффективно
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "combat_unit_uuid")
    val weekCombatUnit: NsiCombatUnit?,

    name: String,
    description: String
): AbstractSimpleDictionary(name, description) {

    /**
     * Ресурсы, необходимые подразделению
     */
    @OneToMany(mappedBy = "combatUnit", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    val combatUnitResources: List<CombatUnitResource> = listOf()
}