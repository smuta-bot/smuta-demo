package ru.yan.database.repository.operation

import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import ru.yan.database.model.nsi.types.CombatUnitPurpose
import ru.yan.database.model.operation.BattleOperation
import ru.yan.database.model.operation.CountryFightingUnit
import java.util.*

@Repository
interface CountryFightingUnitRepository: JpaRepository<CountryFightingUnit, UUID> {

    @Lock(LockModeType.PESSIMISTIC_READ)
    fun findByBattleOperation(battleOperation: BattleOperation): List<CountryFightingUnit>

    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("SELECT cfu FROM CountryFightingUnit cfu WHERE cfu.battleOperation = :battleOperation AND cfu.combatUnitPurpose = :purpose")
    fun findByOperationAndPurpose(battleOperation: BattleOperation, purpose: CombatUnitPurpose): CountryFightingUnit?
}