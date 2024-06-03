package ru.yan.database.service.impl

import org.springframework.stereotype.Service
import ru.yan.database.exceptions.notFoundBy
import ru.yan.database.model.smuta.Country
import ru.yan.database.model.smuta.User
import ru.yan.database.model.smuta.dto.DtoStorageResource
import ru.yan.database.model.smuta.dto.DtoUser
import ru.yan.database.model.smuta.dto.toDto
import ru.yan.database.repository.smuta.CountryRepository
import ru.yan.database.repository.smuta.StorageResourceRepository
import ru.yan.database.service.StorageService

@Service
class StorageServiceImpl(
    private val countryRepository: CountryRepository,
    private val storageResourceRepository: StorageResourceRepository
): StorageService {

    override fun getStorageResources(user: DtoUser): List<DtoStorageResource> {
        countryRepository.findByUserId(user.id)?.let { country ->
            return storageResourceRepository.findByCountry(country).map {
                it.toDto()
            }
        } ?: notFoundBy(Country::class, User::class, user.id)
    }
}