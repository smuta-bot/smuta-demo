package ru.yan.database.model.smuta

import io.hypersistence.utils.hibernate.type.basic.PostgreSQLEnumType
import jakarta.persistence.*
import org.hibernate.annotations.Type
import ru.yan.database.model.nsi.types.AgeGroup
import ru.yan.database.model.nsi.types.Gender
import ru.yan.database.model.nsi.types.HealthStatus
import java.util.*

@MappedSuperclass
abstract class AbstractPopulation(
    /**
     * Пол
     */
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "nsi.Gender")
    @Type(PostgreSQLEnumType::class)
    val gender: Gender,

    /**
     * Возрастная группа
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "age_group", columnDefinition = "nsi.AgeGroup")
    @Type(PostgreSQLEnumType::class)
    val ageGroup: AgeGroup,

    /**
     * Состояние здоровья
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "health_status", columnDefinition = "nsi.HealthStatus")
    @Type(PostgreSQLEnumType::class)
    val healthStatus: HealthStatus,

    /**
     * См. [Population] или [PopulationOperation]
     */
    var quantity: Long,

    /**
     * См. [Population] или [PopulationOperation]
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_uuid")
    var country: Country
) {
    /**
     * Идентификатор
     */
    @Id
    @Column(name = "id", updatable = false)
    val id: UUID = UUID.randomUUID()
}