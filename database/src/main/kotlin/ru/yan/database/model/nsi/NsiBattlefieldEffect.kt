package ru.yan.database.model.nsi

import io.hypersistence.utils.hibernate.type.basic.PostgreSQLEnumType
import jakarta.persistence.*
import org.hibernate.annotations.Type
import ru.yan.database.model.nsi.types.CombatUnitAttribute
import java.util.*

/**
 * Сущность "Эффект, накладываемый поле боя"
 */
@Entity
@Table(schema = "nsi", name = "battlefield_effect")
class NsiBattlefieldEffect(
    /**
     * Атрибут, на который влияет поле боя
     */
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "nsi.CombatUnitAttribute")
    @Type(PostgreSQLEnumType::class)
    val attribute: CombatUnitAttribute,

    /**
     * Минимальное влияние
     */
    val min: Double,

    /**
     * Максимальное влияние
     */
    val max: Double,

    /**
     * Поле боя, на которое влияет эффект
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "battlefield_uuid")
    val battlefield: Battlefield,
) {
    @Id
    @Column(name = "id", updatable = false)
    val id: UUID = UUID.randomUUID()
}