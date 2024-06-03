package ru.yan.database.repository.task

import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import ru.yan.database.model.nsi.NsiBuilding
import ru.yan.database.model.smuta.Country
import ru.yan.database.model.task.BuildingDecayTask
import ru.yan.database.model.task.BuildingSalaryTask
import java.util.*

@Repository
interface BuildingDecayTaskRepository: JpaRepository<BuildingDecayTask, UUID> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT bdt FROM BuildingDecayTask bdt WHERE bdt.nsiBuilding = :building AND bdt.task.country = :country AND bdt.task.deletedAt IS NULL")
    fun findByCountryAndBuilding(country: Country, building: NsiBuilding): BuildingSalaryTask?
}