package ru.yan.database.model.smuta

import jakarta.persistence.*
import ru.yan.database.model.nsi.BuildingProduct
import ru.yan.database.model.nsi.NsiBuilding
import java.util.*
import ru.yan.database.model.operation.ProductOperation

/**
 * Сущность "Производимая продукция"
 *
 * Это актуальная производимая продукция
 * Только для чтения. Изменения производить через [ProductOperation]
 * В бд есть триггер, который при добавлении записи в [ProductOperation] изменит продукцию в [Product]
 */
@Entity
@Table(schema = "smuta", name = "product")
class Product(
    /**
     * Производимый продукт
     */
    @OneToOne
    @JoinColumn(name = "building_product_uuid")
    val buildingProduct: BuildingProduct,

    /**
     * Страна
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_uuid")
    val country: Country,

    /**
     * Описание здания из справочника
     */
    @OneToOne
    @JoinColumn(name = "building_uuid")
    val nsiBuilding: NsiBuilding
) {

    /**
     * Идентификатор
     */
    @Id
    @Column(name = "id", updatable = false)
    val id: UUID = UUID.randomUUID()
}