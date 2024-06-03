package ru.yan.database.repository.smuta

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.yan.database.model.smuta.Country
import ru.yan.database.model.smuta.StorageResource
import ru.yan.database.repository.smuta.custom.CustomStorageResourceRepository
import java.util.*

@Repository
interface StorageResourceRepository: JpaRepository<StorageResource, UUID>, CustomStorageResourceRepository {
    /**
     * Найти ресурс в хранилище страны
     */
    fun findByCountryAndResourceId(country: Country, resourceId: UUID): StorageResource?

    /**
     * Найти ресурсы в хранилище страны
     */
    fun findByCountryAndResourceIdIn(country: Country, ids: List<UUID>): List<StorageResource>

    /**
     * Найти все ресурсы в хранилище страны
     */
    fun findByCountry(country: Country): List<StorageResource>
}