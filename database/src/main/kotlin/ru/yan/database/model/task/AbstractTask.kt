package ru.yan.database.model.task

import jakarta.persistence.*
import java.util.*

@MappedSuperclass
sealed class AbstractTask(
    /**
     * Ссылка на задачу
     */
    @OneToOne
    @JoinColumn(name = "task_uuid")
    var task: Task
) {
    /**
     * Идентификатор
     */
    @Id
    @Column(name = "id", updatable = false)
    val id: UUID = UUID.randomUUID()
}