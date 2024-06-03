package ru.yan.database.repository.task

import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import ru.yan.database.model.nsi.NsiBuilding
import ru.yan.database.model.smuta.Country
import ru.yan.database.model.task.ProductionTask
import java.util.*

@Repository
interface ProductionTaskRepository: JpaRepository<ProductionTask, UUID> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT pt FROM ProductionTask pt WHERE pt.nsiBuilding = :building AND pt.task.country = :country AND pt.task.deletedAt IS NULL")
    fun findByCountryAndBuilding(country: Country, building: NsiBuilding): ProductionTask?
}