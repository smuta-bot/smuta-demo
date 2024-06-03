package ru.yan.database.repository.operation.custom

import java.util.*

interface CustomHappinessOperationRepository {
    /**
     * Расчет коэффициента счастья в стране
     */
    fun happiness(countryId: UUID): Double
}