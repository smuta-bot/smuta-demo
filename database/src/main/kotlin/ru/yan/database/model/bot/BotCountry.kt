package ru.yan.database.model.bot

import jakarta.persistence.*
import java.util.*

/**
 * Сущность "Страна(бот)"
 */
@Entity
@Table(schema = "bot", name = "bot_country")
class BotCountry(
    /**
     * Название страны
     */
    val name: String,

    /**
     * Площадь страны
     */
    var area: Double
) {
    /**
     * Идентификатор
     */
    @Id
    @Column(name = "id", updatable = false)
    val id: UUID = UUID.randomUUID()

    /**
     * Боевые подразделения страны(бота)
     */
    @OneToMany(mappedBy = "botCountry", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    val botCombatUnits: List<BotCombatUnit> = listOf()

    /**
     * Ресурсы страны(бота)
     */
    @OneToMany(mappedBy = "botCountry", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    val botStorageResources: List<BotStorageResource> = listOf()
}