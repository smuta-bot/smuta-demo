package ru.yan.database.model.smuta

import ru.yan.database.model.nsi.types.AgeGroup
import ru.yan.database.model.nsi.types.HealthStatus

/**
 * Подсчет свободной территории в стране
 */
fun Country.freeTerritory(): Double {
    val builtUpArea = this.buildings.fold(0.0) { a, b ->
        a + (b.nsiBuilding.areaByLevel * b.level)
    }

    return this.area - builtUpArea
}

/**
 * Подсчет занятой территории
 */
fun Country.occupiedTerritory(): Double {
    val builtUpArea = this.buildings.fold(0.0) { a, b ->
        a + (b.nsiBuilding.areaByLevel * b.level)
    }

    return builtUpArea
}

/**
 * Эффективность использования площади
 */
fun Country.areaEfficiency(): Double {
    val occupiedTerritory = this.occupiedTerritory()
    return if (occupiedTerritory <= 0.0) {
        0.0
    } else {
        with(this.area / occupiedTerritory) {
            if (this > 1.0) {
                1.0
            } else {
                this
            }
        }
    }
}

/**
 * Уровень занятости
 */
fun Country.employmentRate(): Double {
    val workingClass = this.populations
        .filter { it.ageGroup == AgeGroup.MiddleAge && it.healthStatus == HealthStatus.Healthy }
        .fold(0L) { a, b -> a + b.quantity}

    val workplaces = this.buildings.fold(0) { a, b -> a + b.level * b.nsiBuilding.workplacesByLevel } +
            this.combatUnits.fold(0L) { a, b -> a + b.quantity }

    return if (workingClass <= 0) {
        0.0
    } else {
        with(workplaces / workingClass.toDouble()) {
            if (this >= 1.0) {
                1.0
            } else {
                this
            }
        }
    }
}

/**
 * Коэффициент, показывающий отношение кол-во рабочих к кол-ву рабочих мест
 */
fun Country.workplacesRate(): Double {
    val workingClass = this.populations
        .filter { it.ageGroup == AgeGroup.MiddleAge && it.healthStatus == HealthStatus.Healthy }
        .fold(0L) { a, b -> a + b.quantity}

    val workplaces = this.buildings.fold(0) { a, b -> a + b.level * b.nsiBuilding.workplacesByLevel } +
            this.combatUnits.fold(0L) { a, b -> a + b.quantity }

    return if (workingClass <= 0) {
        0.0
    } else {
        with(workingClass / workplaces.toDouble()) {
            if (this >= 1.0) {
                1.0
            } else {
                this
            }
        }
    }
}