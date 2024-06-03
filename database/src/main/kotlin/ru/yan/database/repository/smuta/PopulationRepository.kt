package ru.yan.database.repository.smuta

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.yan.database.model.smuta.Country
import ru.yan.database.model.smuta.Population
import java.util.*

@Repository
interface PopulationRepository: JpaRepository<Population, UUID> {
    fun findByCountry(country: Country): List<Population>
}