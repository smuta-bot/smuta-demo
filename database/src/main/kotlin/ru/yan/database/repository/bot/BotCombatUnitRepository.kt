package ru.yan.database.repository.bot

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import ru.yan.database.model.bot.BotCombatUnit
import ru.yan.database.model.nsi.NsiCombatUnit
import java.util.*

@Repository
interface BotCombatUnitRepository: JpaRepository<BotCombatUnit, UUID> {

    @Query("SELECT bcu FROM BotCombatUnit bcu WHERE bcu.botCountry.id = :botCountryId")
    fun findByBotCountryId(botCountryId: UUID): List<BotCombatUnit>

    @Query("SELECT bcu FROM BotCombatUnit bcu WHERE bcu.botCountry.id = :botCountryId AND bcu.quantity > 0")
    fun findExistByBotCountryId(botCountryId: UUID): List<BotCombatUnit>

    @Query("SELECT bcu FROM BotCombatUnit bcu WHERE bcu.botCountry.id = :botCountryId And bcu.nsiCombatUnit = :unit")
    fun findByBotCountryIdAndUnit(botCountryId: UUID, unit: NsiCombatUnit): BotCombatUnit?
}