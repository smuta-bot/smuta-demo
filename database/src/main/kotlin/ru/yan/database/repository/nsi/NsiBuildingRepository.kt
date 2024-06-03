package ru.yan.database.repository.nsi

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import ru.yan.database.model.nsi.NsiBuilding
import java.util.*

@Repository
interface NsiBuildingRepository: JpaRepository<NsiBuilding, UUID> {
    /**
     * Получить список зданий для постройки.
     * Уже построенные здания в стране исключаются
     */
    @Query("SELECT nb FROM NsiBuilding nb WHERE nb.id not in (SELECT cb.nsiBuilding FROM CountryBuilding cb WHERE cb.country.user.id = :userId)")
    fun findForBuild(userId: UUID, pageable: Pageable): Page<NsiBuilding>
}