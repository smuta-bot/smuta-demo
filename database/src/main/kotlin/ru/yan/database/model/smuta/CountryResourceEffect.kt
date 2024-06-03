package ru.yan.database.model.smuta

import jakarta.persistence.*
import ru.yan.database.model.nsi.NsiResourceEffect
import java.util.*

/**
 * Сущность "Эффект, наложенный на ресурс добываемый страной"
 */
@Entity
@Table(schema = "smuta", name = "country_resource_effect")
class CountryResourceEffect(
    /**
     * Количественное значение эффекта
     */
    val quantity: Double,

    /**
     * Страна
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_uuid")
    val country: Country,

    /**
     * Описание эффекта из справочника
     */
    @OneToOne
    @JoinColumn(name = "resource_effect_uuid")
    val nsiResourceEffect: NsiResourceEffect
) {
    /**
     * Идентификатор
     */
    @Id
    @Column(name = "id", updatable = false)
    val id: UUID = UUID.randomUUID()
}