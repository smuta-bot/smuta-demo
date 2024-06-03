package ru.yan.database.model.operation

import io.hypersistence.utils.hibernate.type.basic.PostgreSQLEnumType
import jakarta.persistence.*
import org.hibernate.annotations.Type
import ru.yan.database.model.nsi.types.HappinessOperationType
import ru.yan.database.model.smuta.Country
import java.util.*

/**
 * Сущность "Операции над уровнем счастья"
 *
 * Есть позитивные события, у которых [quantity] содержит очки счастья, которые оно дает.
 * Есть негативные события, у которых [quantity] содержит очки несчастья, которые оно дает.
 *
 * Текущий уровень счастья равен отношению очков счастья к очкам несчастья за 24 часа
 */
@Entity
@Table(schema = "operation", name = "happiness_operation")
class HappinessOperation(
    /**
     * Тип операции
     */
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "nsi.HappinessOperationType")
    @Type(PostgreSQLEnumType::class)
    val type: HappinessOperationType,

    /**
     * Количественные изменения
     */
    val quantity: Double,

    /**
     * Страна
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_uuid")
    val country: Country
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