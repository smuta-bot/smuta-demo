package ru.yan.database.components

import ru.yan.database.model.nsi.types.*
import ru.yan.database.model.operation.*
import kotlin.random.Random

/**
 * Базовый калькулятор, с помощью которого рассчитываются шансы на победу сторон и конечный победитель
 */
abstract class BattleCalculator {

    protected fun isWin(attackForce: Double, defendForce: Double): Boolean {
        // Реализация закрыта
        val chance = 0

        return when(chance) {
            0 -> false
            100 -> true
            else -> {
                val t = Random.nextInt(1, 100)
                t <= chance
            }
        }
    }

    protected fun force(unit: CountryFightingUnit, effects: List<BattlefieldEffectOperation>): Double {
        // Реализация закрыта

        return 0.0
    }
}