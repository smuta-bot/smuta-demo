package ru.yan.database.repository.task

import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import ru.yan.database.model.operation.BuildingOperation
import ru.yan.database.model.task.ConstructionTask
import java.util.*

@Repository
interface ConstructionTaskRepository: JpaRepository<ConstructionTask, UUID> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT ct FROM ConstructionTask ct WHERE ct.toBuildingOperation = :buildingOperation AND ct.task.deletedAt IS NULL")
    fun findByToBuildingOperation(buildingOperation: BuildingOperation): ConstructionTask?
}