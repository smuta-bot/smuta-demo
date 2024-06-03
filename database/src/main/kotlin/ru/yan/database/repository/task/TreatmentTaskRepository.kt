package ru.yan.database.repository.task

import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import ru.yan.database.model.smuta.Country
import ru.yan.database.model.task.TreatmentTask
import java.util.*

@Repository
interface TreatmentTaskRepository: JpaRepository<TreatmentTask, UUID> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT tt FROM TreatmentTask tt WHERE tt.task.country = :country AND tt.task.deletedAt IS NULL")
    fun findByCountry(country: Country): TreatmentTask?
}