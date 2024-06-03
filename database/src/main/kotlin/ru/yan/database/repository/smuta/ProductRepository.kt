package ru.yan.database.repository.smuta

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import ru.yan.database.model.nsi.NsiBuilding
import ru.yan.database.model.smuta.Country
import ru.yan.database.model.smuta.Product
import java.util.*

@Repository
interface ProductRepository: JpaRepository<Product, UUID> {

    fun findByCountryAndNsiBuilding(country: Country, nsiBuilding: NsiBuilding): List<Product>

    @Query("SELECT p FROM Product p " +
            "WHERE p.nsiBuilding.id = :nsiBuildingId AND " +
            "p.country.user.id = :userId")
    fun findByUserAndNsiBuilding(userId: UUID, nsiBuildingId: UUID, pageable: Pageable): Page<Product>
}