package ru.yan.database.repository.smuta

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import ru.yan.database.model.smuta.Country
import ru.yan.database.model.smuta.MarketProduct
import java.util.*

@Repository
interface MarketProductRepository: JpaRepository<MarketProduct, UUID> {
    @Query("SELECT mp FROM MarketProduct mp WHERE mp.country.user.id = :userId AND mp.country.deletedAt IS NULL")
    fun findAllByUser(userId: UUID, pageable: Pageable): Page<MarketProduct>

    @Query("SELECT mp FROM MarketProduct mp WHERE mp.country.user.id != :userId AND mp.country.deletedAt IS NULL ORDER BY mp.price")
    fun findAllSortedByPrice(userId: UUID, pageable: Pageable): Page<MarketProduct>

    fun findAllByCountry(country: Country): List<MarketProduct>
}