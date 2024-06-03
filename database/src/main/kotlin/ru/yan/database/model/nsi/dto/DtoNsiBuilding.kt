package ru.yan.database.model.nsi.dto

import ru.yan.database.model.nsi.NsiBuilding
import java.util.*

/**
 * DTO для [NsiBuilding]
 */
data class DtoNsiBuilding(
    val id: UUID,
    val workplacesByLevel: Int,
    val areaByLevel: Double,
    val priceForLevel: Double,
    val constructionTimeByLevel: Int,
    val destroyTime: Int,
    val destroyPriceForLevel: Double,
    val mothballedPriceByLevel: Double,
    val mothballedTime: Int,
    val employeeSalary: Double,
    val productionTime: Int,
    val name: String,
    val description: String
)
