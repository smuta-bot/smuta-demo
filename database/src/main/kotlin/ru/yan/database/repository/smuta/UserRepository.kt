package ru.yan.database.repository.smuta

import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import ru.yan.database.model.smuta.User
import ru.yan.database.repository.smuta.custom.CustomUserRepository
import java.util.*

@Repository
interface UserRepository: JpaRepository<User, UUID>, CustomUserRepository {
    @Lock(LockModeType.OPTIMISTIC)
    @Query("SELECT u FROM User u WHERE u.id = :usedId")
    fun findByUserId(usedId: UUID): User?

    fun findByTelegramId(telegramId: Long): User?

    fun findByVkId(telegramId: Long): User?

    @Lock(LockModeType.OPTIMISTIC)
    fun findByTelegramIdIn(ids: List<Long>): List<User>

    @Lock(LockModeType.OPTIMISTIC)
    fun findByVkIdIn(ids: List<Long>): List<User>

    @Query("SELECT u FROM User u " +
            "JOIN Country c ON c.user = u AND c.deletedAt IS NULL AND size(c.notifications) > 0 " +
            "WHERE now() - u.activityAt > make_interval(0, 0, 0, 0, 0, 0, 1) AND u.deletedAt IS NULL")
    fun findToNotificate(): List<User>
}