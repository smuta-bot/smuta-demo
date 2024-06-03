package ru.yan.database.repository.task

import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import ru.yan.database.model.nsi.NsiBuilding
import ru.yan.database.model.smuta.Country
import ru.yan.database.model.task.BuildingSalaryTask
import java.util.*

@Repository
interface BuildingSalaryTaskRepository: JpaRepository<BuildingSalaryTask, UUID> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT bst FROM BuildingSalaryTask bst WHERE bst.nsiBuilding = :building AND bst.task.country = :country AND bst.task.deletedAt IS NULL")
    fun findByCountryAndBuilding(country: Country, building: NsiBuilding): BuildingSalaryTask?
}