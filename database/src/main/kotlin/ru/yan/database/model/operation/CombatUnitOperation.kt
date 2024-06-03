package ru.yan.database.model.operation

import io.hypersistence.utils.hibernate.type.basic.PostgreSQLEnumType
import jakarta.persistence.*
import org.hibernate.annotations.Type
import ru.yan.database.model.nsi.NsiCombatUnit
import ru.yan.database.model.nsi.types.CombatUnitStatus
import ru.yan.database.model.smuta.Country
import java.util.*

/**
 * Сущность "Операция над боевым подразделением"
 */
@Entity
@Table(schema = "operation", name = "combat_unit_operation")
class CombatUnitOperation(
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
    val nsiCombatUnit: NsiCombatUnit
) {
    /**
     * Идентификатор
     */
    @Id
    @Column(name = "id", updatable = false)
    val id: UUID = UUID.randomUUID()

    /**
     * Время создания операции
     */
    @Column(name = "created_at")
    val createdAt: Date = Date()
}