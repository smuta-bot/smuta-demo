package ru.yan.database.repository.smuta

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import ru.yan.database.model.nsi.NsiBuilding
import ru.yan.database.model.smuta.Country
import ru.yan.database.model.smuta.CountryBuilding
import java.util.*

@Repository
interface CountryBuildingRepository: JpaRepository<CountryBuilding, UUID> {

    @Query("SELECT cb FROM CountryBuilding cb WHERE cb.country.user.id = :userId")
    fun findByUserId(userId: UUID, pageable: Pageable): Page<CountryBuilding>

    @Query("SELECT cb FROM CountryBuilding cb " +
            "WHERE cb.country.user.id = :userId AND cb.nsiBuilding.id = :nsiBuildingId")
    fun findByUserIdAndNsiBuildingId(userId: UUID, nsiBuildingId: UUID): CountryBuilding?

    @Query("SELECT cb FROM CountryBuilding cb " +
            "WHERE cb.country = :country AND cb.nsiBuilding.id = :nsiBuildingId")
    fun findByCountryAndNsiBuildingId(country: Country, nsiBuildingId: UUID): CountryBuilding?

    @Query("SELECT cb FROM CountryBuilding cb " +
            "WHERE cb.country = :country AND cb.nsiBuilding = :nsiBuilding")
    fun findByCountryAndNsiBuilding(country: Country, nsiBuilding: NsiBuilding): CountryBuilding?
}