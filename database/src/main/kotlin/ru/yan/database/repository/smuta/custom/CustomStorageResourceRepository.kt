package ru.yan.database.repository.smuta.custom

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import ru.yan.database.model.smuta.Country
import ru.yan.database.model.smuta.StorageResource
import java.util.*

interface CustomStorageResourceRepository {
    /**
     * Найти каждый ресурс из списка.
     * Если ресурса нет, то вернет null.
     */
    fun findByCountryAndAllResourceIdIn(country: Country, ids: List<UUID>): List<StorageResource?>

    /**
     * Получить список товаров для продажи(за исключением золотых монет)
     */
    fun findByUserForSell(userId: UUID, pageable: Pageable): Page<StorageResource>

    /**
     * Получить товары по категории и подкатегории
     */
    fun findByCategoryAndSubcategory(country: Country, categoryId: UUID, subcategoryId: UUID? = null): List<StorageResource>
}