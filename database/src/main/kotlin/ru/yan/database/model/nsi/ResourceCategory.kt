package ru.yan.database.model.nsi

import jakarta.persistence.*
import java.util.*

/**
 * Сущность "Категория ресурса"
 */
@Entity
@Table(schema = "nsi", name = "resource_category")
class ResourceCategory(
    /**
     * Название
     */
    val name: String,

    /**
     * Категория ресурса.
     * Для категории значение null.
     * Для подкатегории это id категории.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resource_category_uuid")
    val resourceCategory: ResourceCategory?
) {
    @Id
    @Column(name = "id", updatable = false)
    var id: UUID = UUID.randomUUID()

    /**
     * Подкатегории
     */
    @OneToMany(mappedBy = "resourceCategory", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    val resourceCategories: MutableList<ResourceCategory> = mutableListOf()
}