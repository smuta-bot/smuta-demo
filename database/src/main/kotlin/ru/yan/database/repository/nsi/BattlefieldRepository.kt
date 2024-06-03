package ru.yan.database.repository.nsi

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import ru.yan.database.model.nsi.Battlefield
import java.util.*

@Repository
interface BattlefieldRepository: JpaRepository<Battlefield, UUID> {
    /**
     * Найти случайное поле боя
     */
    @Query("SELECT r FROM Resource r ORDER BY random() LIMIT 1")
    fun findOneRandom(): Battlefield
}