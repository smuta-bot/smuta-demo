package ru.yan.database.repository.smuta

import jakarta.persistence.LockModeType
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import ru.yan.database.model.nsi.NsiCombatUnit
import ru.yan.database.model.smuta.CombatUnit
import ru.yan.database.model.smuta.Country
import java.util.*

@Repository
interface CombatUnitRepository: JpaRepository<CombatUnit, UUID> {

    @Lock(LockModeType.PESSIMISTIC_READ)
    fun findByCountry(country: Country): List<CombatUnit>

    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("SELECT cu FROM CombatUnit cu WHERE cu.country.user.id = :userId")
    fun findByUserId(userId: UUID, pageable: Pageable): Page<CombatUnit>

    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("SELECT cu FROM CombatUnit cu WHERE cu.id = :combatUnitId AND cu.country.user.id = :userId")
    fun findByUserIdAndId(userId: UUID, combatUnitId: UUID): CombatUnit?

    @Lock(LockModeType.PESSIMISTIC_READ)
    fun findByCountryAndId(country: Country, combatUnitId: UUID): CombatUnit?

    @Lock(LockModeType.PESSIMISTIC_READ)
    fun findByCountryAndNsiCombatUnitId(country: Country, nsiCombatUnitId: UUID): CombatUnit?

    @Lock(LockModeType.PESSIMISTIC_READ)
    fun findByCountryAndNsiCombatUnit(country: Country, nsiCombatUnit: NsiCombatUnit): CombatUnit?
}