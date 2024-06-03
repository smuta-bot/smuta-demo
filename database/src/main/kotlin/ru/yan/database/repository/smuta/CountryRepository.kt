package ru.yan.database.repository.smuta

import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import ru.yan.database.model.smuta.Country
import java.util.*

@Repository
interface CountryRepository: JpaRepository<Country, UUID> {
    /**
     * Получить страну, которой управляет пользователь
     */
    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("SELECT c FROM Country c WHERE c.user.id = :userId AND c.deletedAt IS NULL")
    fun findByUserId(userId: UUID): Country?

    /**
     * Найти одну случайную страну за исключением одной страны
     */
    @Query("SELECT c.* FROM smuta.country as c TABLESAMPLE SYSTEM (10) WHERE c.id != :countryId AND c.deleted_at IS NULL LIMIT 1", nativeQuery = true)
    fun findRandomOneWithExclude(countryId: UUID): Country?
}