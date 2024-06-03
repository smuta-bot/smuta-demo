package ru.yan.database.model.operation

import io.hypersistence.utils.hibernate.type.basic.PostgreSQLEnumType
import jakarta.persistence.*
import org.hibernate.annotations.Type
import ru.yan.database.model.nsi.types.CountryStateOperationType
import ru.yan.database.model.smuta.Country
import java.util.*

/**
 * Сущность "Операция над показателями страны"
 */
@Entity
@Table(schema = "operation", name = "country_state_operation")
class CountryStateOperation(
    /**
     * Тип операции
     */
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "nsi.CountryStateOperationType")
    @Type(PostgreSQLEnumType::class)
    val type: CountryStateOperationType,

    /**
     * Количественные изменения(больше 0 то значение увеличится на quantity, меньше 0 значение уменьшится на quantity).
     * Только для [type] = AreaType.
     * Для остальных просто выставляется значение quantity.
     */
    val quantity: Double,

    /**
     * Страна, над показателями которой производится операция
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
     * Дата создания
     */
    @Column(name = "created_at")
    val createdAt: Date = Date()
}