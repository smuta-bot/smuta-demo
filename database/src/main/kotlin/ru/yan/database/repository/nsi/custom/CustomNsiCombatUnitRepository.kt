package ru.yan.database.repository.nsi.custom

import ru.yan.database.model.nsi.NsiCombatUnit

interface CustomNsiCombatUnitRepository {
    /**
     * Найти определенное кол-во случайных подразделений
     */
    fun findRandom(limit: Int): List<NsiCombatUnit>
}