package ru.yan.database.repository.nsi

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import ru.yan.database.model.nsi.NotificationCategory
import java.util.*

@Repository
interface NotificationCategoryRepository: JpaRepository<NotificationCategory, UUID> {
    @Query("SELECT DISTINCT ON(name) nc.* FROM nsi.notification_category as nc " +
            "INNER JOIN smuta.notification as n ON n.notification_category_uuid = nc.id AND n.deleted_at IS NULL " +
            "WHERE n.country_uuid = :countryId", nativeQuery = true)
    fun findUnreadNotificationCategory(countryId: UUID, pageable: Pageable): Page<NotificationCategory>
}