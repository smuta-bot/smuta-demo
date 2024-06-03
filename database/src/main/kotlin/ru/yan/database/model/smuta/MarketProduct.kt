package ru.yan.database.model.smuta

import jakarta.persistence.*
import ru.yan.database.model.nsi.Resource
import ru.yan.database.model.operation.MarketProductOperation
import java.util.*

/**
 * Сущность "Продукт, выставленный на продажу"
 *
 * Это сущность с актуальными данными.
 * Только для чтения. Все изменения проводить через [MarketProductOperation]
 * В бд есть триггер, который при добавлении записи в [MarketProductOperation] изменит численность населения в [MarketProduct]
 */
@Entity
@Table(schema = "smuta", name = "market_product")
class MarketProduct(
    /**
     * Цена товара на рынке(за единицу)
     */
    val price: Double,

    /**
     * Кол-во продаваемого товара
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
}