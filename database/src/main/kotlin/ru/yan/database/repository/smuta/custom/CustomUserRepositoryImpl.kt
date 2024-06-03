package ru.yan.database.repository.smuta.custom

import jakarta.persistence.EntityManager
import jakarta.persistence.LockModeType
import jakarta.persistence.PersistenceContext
import ru.yan.database.model.smuta.User

class CustomUserRepositoryImpl: CustomUserRepository {
    @PersistenceContext
    private lateinit var em: EntityManager

    override fun findOutOfTelegramLimit(telegramIds: List<Long>, limit: Int): List<User> {
        val query = em.createQuery("SELECT u FROM User u " +
                "WHERE u.telegramId IN (:telegramIds) AND " +
                "now() - u.activityAt > make_interval(0, 0, 0, 0, 0, 0, 1) AND " +
                "u.deletedAt IS NULL ORDER BY u.activityAt DESC")
            .setParameter("telegramIds", telegramIds)
            .setLockMode(LockModeType.PESSIMISTIC_READ)
            .setMaxResults(limit)

        return query.resultList as List<User>
    }

    override fun findOutOfVkLimit(vkIds: List<Long>, limit: Int): List<User> {
        val query = em.createQuery("SELECT u FROM User u " +
                "WHERE u.vkId IN (:vkIds) AND " +
                "now() - u.activityAt > make_interval(0, 0, 0, 0, 0, 0, 1) AND " +
                "u.deletedAt IS NULL ORDER BY u.activityAt DESC")
            .setParameter("vkIds", vkIds)
            .setLockMode(LockModeType.PESSIMISTIC_READ)
            .setMaxResults(limit)

        return query.resultList as List<User>
    }
}