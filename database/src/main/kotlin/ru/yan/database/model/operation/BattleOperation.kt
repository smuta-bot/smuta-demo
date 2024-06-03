package ru.yan.database.model.operation

import io.hypersistence.utils.hibernate.type.basic.PostgreSQLEnumType
import jakarta.persistence.*
import org.hibernate.annotations.Type
import ru.yan.database.model.bot.BotCountry
import ru.yan.database.model.nsi.Battlefield
import ru.yan.database.model.nsi.types.BattleType
import ru.yan.database.model.smuta.Country
import java.util.*

/**
 * Сущность "Операция над сражением"(за какую территорию идёт сражение, какие подразделения участвуют, поле боя, эффекты поля боя и т.п.)
 */
@Entity
@Table(schema = "operation", name = "battle_operation")
class BattleOperation(
    /**
     * Тип сражения
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "battle_type", columnDefinition = "nsi.BattleType")
    @Type(PostgreSQLEnumType::class)
    val battleType: BattleType,

    /**
     * Площадь, за которую идёт сражение
     */
    val area: Double,

    /**
     * Нападающая страна
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attacking_country_uuid")
    val attackingCountry: Country,

    /**
     * Защищающаяся страна.
     * В зависимости от типа сражения [battleType] это либо бот [BotCountry], либо страна [Country]
     */
    @Column(name = "defending_country_uuid")
    val defendingCountry: UUID,

    /**
     * Поле боя
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "battlefield_uuid")
    val battlefield: Battlefield
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

    /**
     * Подразделение бота, участвующее в сражении.
     * Только если [battleType] = PvE
     */
    @OneToOne(mappedBy = "battleOperation", fetch = FetchType.LAZY)
    val botFightingUnit: BotFightingUnit? = null

    /**
     * Боевые подразделения страны, участвующие в сражении
     * Только если [battleType] = PvP
     */
    @OneToMany(mappedBy = "battleOperation", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    val fightingUnits: List<CountryFightingUnit> = listOf()

    /**
     * Эффекты поля боя
     */
    @OneToMany(mappedBy = "battleOperation", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    val battlefieldEffectOperations: List<BattlefieldEffectOperation> = listOf()
}