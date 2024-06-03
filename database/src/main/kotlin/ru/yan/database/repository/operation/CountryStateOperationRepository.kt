package ru.yan.database.repository.operation

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import ru.yan.database.model.nsi.types.CountryStateOperationType
import ru.yan.database.model.operation.CountryStateOperation
import ru.yan.database.model.smuta.Country
import java.util.*

@Repository
interface CountryStateOperationRepository: JpaRepository<CountryStateOperation, UUID> {
    @Query("SELECT cso FROM CountryStateOperation cso WHERE cso.country = :country AND cso.type = :type ORDER BY cso.createdAt DESC LIMIT 1")
    fun findFirstByCountryAndType(country: Country, type: CountryStateOperationType): CountryStateOperation?
}