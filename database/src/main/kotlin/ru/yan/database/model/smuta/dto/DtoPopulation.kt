package ru.yan.database.model.smuta.dto

import ru.yan.database.model.nsi.types.AgeGroup
import ru.yan.database.model.nsi.types.Gender
import ru.yan.database.model.nsi.types.HealthStatus
import ru.yan.database.model.smuta.Population
import java.util.*

/**
 * DTO для [Population]
 */
data class DtoPopulation(
    val id: UUID,
    val healthStatus: HealthStatus,
    val quantity: Long,
    val gender: Gender,
    val ageGroup: AgeGroup
)
