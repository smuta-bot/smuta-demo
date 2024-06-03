package ru.yan.database.model.operation

import io.hypersistence.utils.hibernate.type.basic.PostgreSQLEnumType
import jakarta.persistence.*
import org.hibernate.annotations.Type
import ru.yan.database.model.nsi.types.CombatUnitAttribute
import java.util.*

/**
 * Сущность "Эффект поля боя"
 */
@Entity
@Table(schema = "operation", name = "battlefield_effect")
class BattlefieldEffectOperation(
    /**
     * Атрибут, на который идёт влияние
     */
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "nsi.CombatUnitAttribute")
    @Type(PostgreSQLEnumType::class)
    val attribute: CombatUnitAttribute,

    /**
     * Значение влияния
     */
    val quantity: Double,

    /**
     * Операция, в процессе которой идёт сражение
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "battle_operation_uuid")
    val battleOperation: BattleOperation
) {
    /**
     * Идентификатор
     */
    @Id
    @Column(name = "id", updatable = false)
    val id: UUID = UUID.randomUUID()
}