package ru.yan.database.model.smuta

import io.hypersistence.utils.hibernate.type.basic.PostgreSQLEnumType
import jakarta.persistence.*
import org.hibernate.annotations.Type
import ru.yan.database.model.nsi.NsiBuilding
import ru.yan.database.model.nsi.types.BuildingStatus
import ru.yan.database.model.operation.BuildingOperation
import java.util.*

/**
 * Сущность "Здания страны"
 *
 * Это актуальные здания страны
 * Только для чтения. Изменения производить через [BuildingOperation]
 * В бд есть триггер, который при добавлении записи в [BuildingOperation] изменит здание в [CountryBuilding]
 */
@Entity
@Table(schema = "smuta", name = "country_building")
class CountryBuilding(
    /**
     * Текущий статус
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "nsi.BuildingStatus")
    @Type(PostgreSQLEnumType::class)
    val status: BuildingStatus,

    /**
     * Текущий уровень
     */
    val level: Int,

    /**
     * Эффективность производства
     *
     * С каждым произведенным товаром эффективность падает(Происходит деградация производственных мощностей. Нужен ремонт)
     */
    val efficiency: Double,

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
    val nsiBuilding: NsiBuilding,

    /**
     * Операция, породившая здание
     */
    @OneToOne
    @JoinColumn(name = "building_operation_uuid")
    val buildingOperation: BuildingOperation
) {
    /**
     * Идентификатор
     */
    @Id
    @Column(name = "id", updatable = false)
    val id: UUID = UUID.randomUUID()
}