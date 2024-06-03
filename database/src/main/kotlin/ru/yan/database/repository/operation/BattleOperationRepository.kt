package ru.yan.database.repository.operation

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import ru.yan.database.model.operation.BattleOperation
import java.util.*

@Repository
interface BattleOperationRepository: JpaRepository<BattleOperation, UUID> {
    /**
     * Найти актуальную запись по id участника
     */
    @Query("SELECT bo FROM BattleOperation bo JOIN BattleTask bt ON bt.battleOperation = bo " +
            "WHERE bo.attackingCountry.id = :memberId OR " +
            "bo.defendingCountry = :memberId AND " +
            "bt.task.deletedAt IS NULL")
    fun findActualByMemberId(memberId: UUID): BattleOperation?
}