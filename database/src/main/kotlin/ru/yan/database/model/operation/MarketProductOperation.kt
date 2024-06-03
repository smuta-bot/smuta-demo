package ru.yan.database.model.operation

import io.hypersistence.utils.hibernate.type.basic.PostgreSQLEnumType
import jakarta.persistence.*
import org.hibernate.annotations.Type
import ru.yan.database.model.nsi.Resource
import ru.yan.database.model.nsi.types.MarketProductOperationType
import ru.yan.database.model.smuta.Country
import java.util.*

/**
 * Сущность "Операция над товаром на рынке"
 */
@Entity
@Table(schema = "operation", name = "market_product_operation")
class MarketProductOperation(
    /**
     * Тип операции
     */
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "nsi.MarketProductOperationType")
    @Type(PostgreSQLEnumType::class)
    val type: MarketProductOperationType,

    /**
     * Зависит от типа [type]
     * Если тип Add или Remove, то это цена за единицу товара.
     * Если Purchased или PurchasedAll, то это цена за кол-во купленного товара
     */
    val price: Double,

    /**
     * Зависит от типа [type]
     * Если тип Add или Remove, то это общее кол-во товара.
     * Если Purchased или PurchasedAll, то это кол-во купленного товара.
     */
    var quantity: Long,

    /**
     * Продаваемый ресурс
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resource_uuid")
    val resource: Resource,

    /**
     * Страна, выставившая товар на продажу
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_uuid")
    var country: Country
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