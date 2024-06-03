package ru.yan.database.model.nsi

import io.hypersistence.utils.hibernate.type.basic.PostgreSQLEnumType
import jakarta.persistence.*
import org.hibernate.annotations.Type
import ru.yan.database.model.nsi.types.DayTime
import ru.yan.database.model.nsi.types.Weather
import java.util.*

/**
 * Сущность "Поле боя"
 */
@Entity
@Table(schema = "nsi", name = "combat_unit_resource")
class Battlefield(
    /**
     * Время суток
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "day_time", columnDefinition = "nsi.DayTime")
    @Type(PostgreSQLEnumType::class)
    val dayTime: DayTime,

    /**
     * Погода
     */
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "nsi.Weather")
    @Type(PostgreSQLEnumType::class)
    val weather: Weather,
) {
    @Id
    @Column(name = "id", updatable = false)
    val id: UUID = UUID.randomUUID()

    /**
     * Эффекты, которые несёт поле боя
     */
    @OneToMany(mappedBy = "battlefield", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    val nsiBattlefieldEffects: List<NsiBattlefieldEffect> = listOf()
}