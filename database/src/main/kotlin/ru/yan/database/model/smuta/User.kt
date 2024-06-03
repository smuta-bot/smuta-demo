package ru.yan.database.model.smuta

import io.hypersistence.utils.hibernate.type.json.JsonType
import jakarta.persistence.*
import org.hibernate.annotations.Type
import java.util.*

/**
 * Сущность "Пользователь"
 */
@Entity
@Table(schema = "smuta", name = "user")
class User(
    /**
     * Id пользователя в телеграмме
     */
    @Column(name = "telegram_id")
    val telegramId: Long?,

    /**
     * Id пользователя в вк
     */
    @Column(name = "vk_id")
    val vkId: Long?,

    /**
     * Активность пользователя
     */
    @Type(JsonType::class)
    @Column(name = "user_activity", columnDefinition = "jsonb")
    var userActivity: UserActivity?,

    /**
     * Дата последней активности
     */
    @Column(name = "activity_at")
    var activityAt: Date = Date()
) {
    /**
     * Идентификатор
     */
    @Id
    @Column(name = "id", updatable = false)
    val id: UUID = UUID.randomUUID()

    /**
     * Версия записи
     */
    @Version
    val version: Long = 0L

    /**
     * Страны, которыми управлял и управляет этот пользователь
     */
    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    val countries: MutableList<Country> = mutableListOf()

    /**
     * Дата регистрации
     */
    @Column(name = "created_at")
    val createdAt: Date = Date()

    /**
     * Дата дерегистрации
     */
    @Column(name = "deleted_at")
    val deletedAt: Date? = null
}