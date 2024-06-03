package ru.yan.database.model.operation

import io.hypersistence.utils.hibernate.type.basic.PostgreSQLEnumType
import jakarta.persistence.*
import org.hibernate.annotations.Type
import ru.yan.database.model.nsi.types.AgeGroup
import ru.yan.database.model.nsi.types.Gender
import ru.yan.database.model.nsi.types.HealthStatus
import ru.yan.database.model.nsi.types.PopulationOperationType
import ru.yan.database.model.smuta.AbstractPopulation
import ru.yan.database.model.smuta.Country
import ru.yan.database.model.task.TreatmentTask
import java.util.*

/**
 * Сущность "Операция над структурой населения"
 */
@Entity
@Table(schema = "operation", name = "population_operation")
class PopulationOperation(
    /**
     * Тип операции
     */
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "nsi.PopulationOperationType")
    @Type(PostgreSQLEnumType::class)
    val type: PopulationOperationType,

    /**
     * Если происходило лечение, то должна быть ссылка на задачу
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "treatment_task_uuid")
    val treatmentTask: TreatmentTask?,

    /**
     * Количественные изменения людей гендера[gender] и возрастной группы[ageGroup]
     * Если больше 0 то идет прибавление, если меньше то уменьшение
     */
    quantity: Long,

    /**
     * Страна, над населением которой производится операция
     */
    country: Country,

    gender: Gender,
    ageGroup: AgeGroup,
    healthStatus: HealthStatus
): AbstractPopulation(gender, ageGroup, healthStatus, quantity, country) {

    /**
     * Дата создания
     */
    @Column(name = "created_at")
    val createdAt: Date = Date()
}