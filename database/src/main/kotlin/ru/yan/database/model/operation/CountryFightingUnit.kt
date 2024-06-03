package ru.yan.database.model.operation

import io.hypersistence.utils.hibernate.type.basic.PostgreSQLEnumType
import jakarta.persistence.*
import org.hibernate.annotations.Type
import ru.yan.database.model.nsi.NsiCombatUnit
import ru.yan.database.model.nsi.types.CombatUnitPurpose
import java.util.*

/**
 * Сущность "Боевое подразделение страны, участвующее в сражении"
 */
@Entity
@Table(schema = "operation", name = "country_combat_unit")
class CountryFightingUnit(
    /**
     * Назначение боевого подразделения
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "combat_unit_purpose", columnDefinition = "nsi.CombatUnitPurpose")
    @Type(PostgreSQLEnumType::class)
    val combatUnitPurpose: CombatUnitPurpose,

    /**
     * Боевой опыт
     */
    val experience: Double,

    /**
     * Ресурсное покрытие.
     * Коэффициент, показывающий насколько хватило ресурсов подразделению
     */
    @Column(name = "resource_coverage")
    val resourceCoverage: Double,

    /**
     * Операция, в процессе которой идёт сражение
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "battle_operation_uuid")
    val battleOperation: BattleOperation,

    /**
     * Люди, завербованные в подразделение
     */
    @OneToOne
    @JoinColumn(name = "population_operation_uuid")
    val populationOperation: PopulationOperation,

    /**
     * Операция, которая перевела боевое подразделение в режим сражения
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "combat_unit_operation_uuid")
    val combatUnitOperation: CombatUnitOperation,

    /**
     * Описание подразделение из справочника
     */
    @OneToOne
    @JoinColumn(name = "combat_unit_uuid")
    val nsiCombatUnit: NsiCombatUnit
) {
    /**
     * Идентификатор
     */
    @Id
    @Column(name = "id", updatable = false)
    val id: UUID = UUID.randomUUID()
}