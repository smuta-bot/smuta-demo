package ru.yan.database.repository.nsi.custom

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import ru.yan.database.model.nsi.NsiCombatUnit

class CustomNsiCombatUnitRepositoryImpl: CustomNsiCombatUnitRepository {
    @PersistenceContext
    private lateinit var em: EntityManager

    override fun findRandom(limit: Int): List<NsiCombatUnit> {
        val query = em.createQuery("SELECT r FROM Resource r ORDER BY random()")
            .setMaxResults(limit)

        return query.resultList as List<NsiCombatUnit>
    }
}