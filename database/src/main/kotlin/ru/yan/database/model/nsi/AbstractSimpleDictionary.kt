package ru.yan.database.model.nsi

import jakarta.persistence.*
import java.util.*

/**
 * Элемент справочника
 */
@MappedSuperclass
abstract class AbstractSimpleDictionary(
    /**
     * Название
     */
    var name: String,

    /**
     * Описание
     */
    var description: String,
) {
    @Id
    @Column(name = "id", updatable = false)
    val id: UUID = UUID.randomUUID()
}