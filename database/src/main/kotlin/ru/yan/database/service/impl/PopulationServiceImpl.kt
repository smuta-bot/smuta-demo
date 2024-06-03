package ru.yan.database.service.impl

import org.springframework.stereotype.Service
import ru.yan.database.exceptions.notFoundBy
import ru.yan.database.model.smuta.Country
import ru.yan.database.model.smuta.User
import ru.yan.database.model.smuta.dto.DtoPopulation
import ru.yan.database.model.smuta.dto.DtoUser
import ru.yan.database.model.smuta.dto.toDto
import ru.yan.database.repository.smuta.CountryRepository
import ru.yan.database.repository.smuta.PopulationRepository
import ru.yan.database.service.PopulationService

@Service
class PopulationServiceImpl(
    private val countryRepository: CountryRepository,
    private val populationRepository: PopulationRepository
): PopulationService {

    override fun getPopulation(user: DtoUser): List<DtoPopulation> {
        countryRepository.findByUserId(user.id)?.let { country ->
            return populationRepository.findByCountry(country).map {
                it.toDto()
            }
        } ?: notFoundBy(Country::class, User::class, user.id)
    }
}