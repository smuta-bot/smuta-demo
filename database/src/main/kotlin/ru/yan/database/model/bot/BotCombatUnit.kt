package ru.yan.database.model.bot

import jakarta.persistence.*
import ru.yan.database.model.nsi.NsiCombatUnit
import java.util.*


/**
 * Сущность "Боевое подразделение страны(бота)"
 */
@Entity
@Table(schema = "bot", name = "bot_combat_unit")
class BotCombatUnit(
    /**
     * Кол-во людей
     */
    var quantity: Long,

    /**
     * Опыт подразделения
     */
    val experience: Double,

    /**
     * Страна(бот)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bot_country_uuid")
    val botCountry: BotCountry,

    /**
     * Описание подразделение из справочника
     */
    @OneToOne
    @JoinColumn(name = "combat_unit_uuid")
    val nsiCombatUnit: NsiCombatUnit
) {
    /**
     * Идентификатор
     */
    @Id
    @Column(name = "id", updatable = false)
    val id: UUID = UUID.randomUUID()
}