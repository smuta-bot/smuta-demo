package ru.yan.database.repository.task

import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import ru.yan.database.model.operation.CombatUnitOperation
import ru.yan.database.model.task.CombatUnitTask
import java.util.*

@Repository
interface CombatUnitTaskRepository: JpaRepository<CombatUnitTask, UUID> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT cu FROM CombatUnitTask cu WHERE cu.toCombatUnitOperation = :combatUnitOperation AND cu.task.deletedAt IS NULL")
    fun findByToBuildingOperation(combatUnitOperation: CombatUnitOperation): CombatUnitTask?
}