package ru.yan.database.model.nsi

import jakarta.persistence.*

/**
 * Сущность "Описание возможного эффекта, накладываемого на добычу ресурса"
 */
@Entity
@Table(schema = "nsi", name = "resource_effect")
class NsiResourceEffect(
    /**
     * Описание ресурса
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resource_uuid")
    val resource: Resource,

    name: String,
    description: String
): AbstractSimpleDictionary(name, description)