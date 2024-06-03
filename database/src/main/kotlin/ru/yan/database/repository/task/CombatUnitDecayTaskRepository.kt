package ru.yan.database.repository.task

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import ru.yan.database.model.nsi.NsiCombatUnit
import ru.yan.database.model.smuta.Country
import ru.yan.database.model.task.CombatUnitDecayTask
import java.util.*

@Repository
interface CombatUnitDecayTaskRepository: JpaRepository<CombatUnitDecayTask, UUID> {
    @Query("SELECT cudt FROM CombatUnitDecayTask cudt WHERE " +
            "cudt.nsiCombatUnit =: nsiCombatUnit AND " +
            "cudt.task.country =: country AND " +
            "cudt.task.deletedAt IS NULL")
    fun findByCountryAndUnit(nsiCombatUnit: NsiCombatUnit, country: Country): CombatUnitDecayTask?
}