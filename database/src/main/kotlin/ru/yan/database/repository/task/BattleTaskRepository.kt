package ru.yan.database.repository.task

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import ru.yan.database.model.operation.BattleOperation
import ru.yan.database.model.smuta.Country
import ru.yan.database.model.task.BattleTask
import java.util.*

@Repository
interface BattleTaskRepository: JpaRepository<BattleTask, UUID> {

    /**
     * Найти сражение, в котором участвует страна
     */
    @Query("SELECT bt FROM BattleTask bt " +
            "LEFT JOIN BattleOperation bo ON bo = bt.battleOperation " +
            "WHERE bo.attackingCountry.id = :countryId OR " +
            "bo.defendingCountry = :countryId OR " +
            "bt.task.country.id = :countryId AND " +
            "bt.task.deletedAt IS NULL")
    fun findByBattleMember(countryId: UUID): BattleTask?

    /**
     * Найти исполняющуюся задачу
     */
    @Query("SELECT bt FROM BattleTask bt WHERE bt.task.country = :country AND bt.task.deletedAt IS NULL")
    fun findActualByCountry(country: Country): BattleTask?

    fun findByBattleOperation(battleOperation: BattleOperation): BattleTask
}