package ru.yan.database.repository.task

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import ru.yan.database.model.nsi.NsiCombatUnit
import ru.yan.database.model.smuta.Country
import ru.yan.database.model.task.CombatUnitDecayTask
import ru.yan.database.model.task.CombatUnitSalaryTask
import java.util.*

@Repository
interface CombatUnitSalaryTaskRepository: JpaRepository<CombatUnitSalaryTask, UUID> {
    @Query("SELECT cust FROM CombatUnitSalaryTask cust WHERE " +
            "cust.nsiCombatUnit =: nsiCombatUnit AND " +
            "cust.task.country =: country AND " +
            "cust.task.deletedAt IS NULL")
    fun findByCountryAndUnit(nsiCombatUnit: NsiCombatUnit, country: Country): CombatUnitSalaryTask?
}