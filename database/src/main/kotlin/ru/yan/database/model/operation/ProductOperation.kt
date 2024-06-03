package ru.yan.database.model.operation

import io.hypersistence.utils.hibernate.type.basic.PostgreSQLEnumType
import jakarta.persistence.*
import org.hibernate.annotations.Type
import ru.yan.database.model.nsi.BuildingProduct
import ru.yan.database.model.nsi.NsiBuilding
import ru.yan.database.model.nsi.types.ProductOperationType
import ru.yan.database.model.smuta.Country
import java.util.*

/**
 * Сущность "Операция над продукцией производимой зданием"
 */
@Entity
@Table(schema = "operation", name = "product_operation")
class ProductOperation(
    /**
     * Тип операции
     */
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "nsi.ProductOperationType")
    @Type(PostgreSQLEnumType::class)
    val type: ProductOperationType,

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

    /**
     * Время создания операции
     */
    @Column(name = "created_at")
    val createdAt: Date = Date()
}