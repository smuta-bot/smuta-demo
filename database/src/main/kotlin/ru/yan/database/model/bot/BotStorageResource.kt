package ru.yan.database.model.bot

import jakarta.persistence.*
import ru.yan.database.model.nsi.Resource
import java.util.*

/**
 * Сущность "Ресурс страны(бота)"
 */
@Entity
@Table(schema = "bot", name = "bot_storage_resource")
class BotStorageResource(
    /**
     * Кол-во ресурса на хранении
     */
    val quantity: Double,

    /**
     * Страна(бот)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bot_country_uuid")
    val botCountry: BotCountry,

    /**
     * Ресурс
     */
    @OneToOne
    @JoinColumn(name = "resource_uuid")
    val resource: Resource
) {
    /**
     * Идентификатор
     */
    @Id
    @Column(name = "id", updatable = false)
    val id: UUID = UUID.randomUUID()
}