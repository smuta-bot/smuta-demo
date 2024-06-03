package ru.yan.database.repository.smuta.custom

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import ru.yan.database.model.moneyUUID
import ru.yan.database.model.smuta.Country
import ru.yan.database.model.smuta.StorageResource
import java.util.*

class CustomStorageResourceRepositoryImpl: CustomStorageResourceRepository {

    @PersistenceContext
    private lateinit var em: EntityManager

    override fun findByCountryAndAllResourceIdIn(country: Country, ids: List<UUID>): List<StorageResource?> {
        val query = em.createNativeQuery(
            "SELECT id, quantity, resource_uuid, country_uuid FROM unnest(ARRAY[${ids.foldIndexed("") { index, a, b ->  
                if (index == 0) {
                    "$a'$b'"
                } else {
                    "$a, '$b'"
                }
            }}]) as resources(resource) " +
            "LEFT JOIN smuta.storage_resource as sr ON sr.country_uuid = :country AND sr.resource_uuid = uuid(resources.resource)"
        )

        query.setParameter("country", country)

        return query.resultList as List<StorageResource?>
    }

    override fun findByUserForSell(userId: UUID, pageable: Pageable): Page<StorageResource> {
        val query = em.createQuery(
            "SELECT sr FROM StorageResource sr WHERE sr.country.user.id = :userId AND sr.resource.id != :moneyId"
        )
            .setFirstResult(pageable.pageNumber * pageable.pageSize)
            .setMaxResults(pageable.pageSize)

        query.setParameter("userId", userId)
        query.setParameter("moneyId", UUID.fromString(moneyUUID))

        val result = query.resultList as List<StorageResource>
        return PageImpl(
            result,
            pageable,
            result.size.toLong()
        )
    }

    override fun findByCategoryAndSubcategory(country: Country, categoryId: UUID, subcategoryId: UUID?): List<StorageResource> {
        val query = subcategoryId?.let { subcategory ->
            em.createQuery(
                "SELECT sr FROM StorageResource sr " +
                        "WHERE sr.country = :country " +
                        "AND sr.resource.resourceCategory.resourceCategory.id = :categoryId " +
                        "AND sr.resource.resourceCategory.id = :subcategoryId"
            )
                .setParameter("country", country)
                .setParameter("categoryId", categoryId)
                .setParameter("subcategoryId",subcategory)
        } ?: run {
            em.createQuery(
                "SELECT sr FROM StorageResource sr " +
                        "WHERE sr.country = :country " +
                        "AND sr.resource.resourceCategory.resourceCategory.id = :categoryId " +
                        "AND sr.resource.resourceCategory.id = :categoryId"
            )
                .setParameter("country", country)
                .setParameter("categoryId", categoryId)
        }

        return query.resultList as List<StorageResource>
    }
}