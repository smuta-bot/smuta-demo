package ru.yan.database.repository.task

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import ru.yan.database.model.task.Task
import java.util.*

@Repository
interface TaskRepository: JpaRepository<Task, UUID> {
    @Query("SELECT DISTINCT ON(country_uuid) task.* FROM task.task as task " +
            "INNER JOIN smuta.country as country ON task.country_uuid = country.id " +
            "WHERE task.started_at + make_interval(0, 0, 0, 0, 0, 0, task.duration) <= now() " +
            "AND task.paused = false AND task.deleted_at IS NULL AND country.deleted_at IS NULL", nativeQuery = true)
    fun findToComplete(): List<Task>
}