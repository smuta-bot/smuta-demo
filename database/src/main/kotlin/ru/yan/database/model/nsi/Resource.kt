package ru.yan.database.model.nsi

import jakarta.persistence.*

/**
 * Сущность "Ресурсы"
 */
@Entity
@Table(schema = "nsi", name = "resource")
class Resource(
    /**
     * Категория ресурса
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resource_category_uuid")
    val resourceCategory: ResourceCategory,

    name: String,
    description: String
): AbstractSimpleDictionary(name, description)