package ru.yan.database.repository.nsi

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import ru.yan.database.model.nsi.BuildingProduct
import java.util.*

@Repository
interface BuildingProductRepository: JpaRepository<BuildingProduct, UUID> {

    @Query("SELECT bp FROM BuildingProduct bp WHERE " +
            "bp.building.id = :nsiBuildingId AND " +
            "bp not in (SELECT p.buildingProduct FROM Product p WHERE p.nsiBuilding.id = :nsiBuildingId)")
    fun findBuildingProductByNsiBuilding(nsiBuildingId: UUID, pageable: Pageable): Page<BuildingProduct>
}