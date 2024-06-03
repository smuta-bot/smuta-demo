package ru.yan.database.model.operation

import jakarta.persistence.*
import ru.yan.database.model.nsi.NsiCombatUnit
import java.util.*

/**
 * Сущность "Боевое подразделение бота, участвующее в сражении"
 */
@Entity
@Table(schema = "operation", name = "bot_combat_unit")
class BotFightingUnit(
    /**
     * Кол-во людей, выставленных для сражения
     */
    val quantity: Long,

    /**
     * Операция, в процессе которой идёт сражение
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "battle_operation_uuid")
    val battleOperation: BattleOperation,

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