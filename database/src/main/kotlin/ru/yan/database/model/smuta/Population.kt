package ru.yan.database.model.smuta

import jakarta.persistence.*
import ru.yan.database.model.nsi.types.AgeGroup
import ru.yan.database.model.nsi.types.Gender
import ru.yan.database.model.nsi.types.HealthStatus
import ru.yan.database.model.operation.PopulationOperation

/**
 * Сущность "Население"
 *
 * Это актуальная структура населения
 * Только для чтения. Изменения производить через [PopulationOperation]
 * В бд есть триггер, который при добавлении записи в [PopulationOperation] изменит численность населения в [Population]
 */
@Entity
@Table(schema = "smuta", name = "population")
class Population(
    /**
     * Кол-во людей гендера[gender] и возрастной группы[ageGroup]
     */
    quantity: Long,

    /**
     * Страна, которой принадлежит эта группа людей
     */
    country: Country,

    gender: Gender,
    ageGroup: AgeGroup,
    healthStatus: HealthStatus
): AbstractPopulation (gender, ageGroup, healthStatus, quantity, country)