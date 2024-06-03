package ru.yan.database.model.smuta

import io.hypersistence.utils.hibernate.type.basic.PostgreSQLEnumType
import jakarta.persistence.*
import org.hibernate.annotations.Type
import ru.yan.database.model.nsi.NsiCombatUnit
import ru.yan.database.model.nsi.types.CombatUnitStatus
import ru.yan.database.model.operation.CombatUnitOperation
import java.util.*

/**
 * Сущность "Боевое подразделение страны"
 *
 * Это актуальные подразделения страны
 * Только для чтения. Изменения производить через [CombatUnitOperation]
 * В бд есть триггер, который при добавлении записи в [CombatUnitOperation] изменит подразделение в [CombatUnit]
 */
@Entity
@Table(schema = "smuta", name = "combat_unit")
class CombatUnit(
    /**
     * Статус подразделения
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "nsi.CombatUnitStatus")
    @Type(PostgreSQLEnumType::class)
    val status: CombatUnitStatus,

    /**
     * Кол-во людей
     */
    val quantity: Long,

    /**
     * Опыт подразделения
     */
    val experience: Double,

    /**
     * Страна
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_uuid")
    val country: Country,

    /**
     * Описание подразделение из справочника
     */
    @OneToOne
    @JoinColumn(name = "combat_unit_uuid")
    val nsiCombatUnit: NsiCombatUnit,

    /**
     * Операция, породившая подразделение
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "combat_unit_operation_uuid")
    val combatUnitOperation: CombatUnitOperation
) {
    @Id
    @Column(name = "id", updatable = false)
    val id: UUID = UUID.randomUUID()
}