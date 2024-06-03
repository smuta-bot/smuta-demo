package ru.yan.database.repository.nsi

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import ru.yan.database.model.nsi.Resource
import java.util.*

@Repository
interface ResourceRepository: JpaRepository<Resource, UUID> {
    /**
     * Найти 5 случайных ресурсов
     */
    @Query("SELECT r FROM Resource r ORDER BY random() LIMIT 5")
    fun findFiveRandom(): List<Resource>
}