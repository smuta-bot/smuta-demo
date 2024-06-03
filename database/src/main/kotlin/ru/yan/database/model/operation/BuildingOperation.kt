package ru.yan.database.model.operation

import io.hypersistence.utils.hibernate.type.basic.PostgreSQLEnumType
import jakarta.persistence.*
import org.hibernate.annotations.Type
import ru.yan.database.model.nsi.NsiBuilding
import ru.yan.database.model.nsi.types.BuildingStatus
import ru.yan.database.model.smuta.Country
import java.util.*

/**
 * Сущность "Операция над зданием в стране"
 */
@Entity
@Table(schema = "operation", name = "building_operation")
class BuildingOperation(
    /**
     * Статус здания
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "nsi.BuildingStatus")
    @Type(PostgreSQLEnumType::class)
    val status: BuildingStatus?,

    /**
     * Уровень здания
     */
    val level: Int?,

    /**
     * Эффективность производства здания
     */
    val efficiency: Double?,

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