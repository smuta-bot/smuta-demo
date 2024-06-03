package ru.yan.database.model.task

import jakarta.persistence.*
import ru.yan.database.model.operation.PopulationOperation
import ru.yan.database.model.operation.StorageResourceOperation

/**
 * Сущность "Задача на лечение населения"
 */
@Entity
@Table(schema = "task", name = "treatment_task")
class TreatmentTask(
    /**
     * Медикаменты, взятые из хранилища
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storage_resource_operation_uuid")
    val storageResourceOperation: StorageResourceOperation?,

    /**
     * Список операций на лечение(что бы понять кого мы лечим и в каком количестве)
     */
    @OneToMany(mappedBy = "treatmentTask", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    val populationOperations: List<PopulationOperation>,

    task: Task
): AbstractTask(task)