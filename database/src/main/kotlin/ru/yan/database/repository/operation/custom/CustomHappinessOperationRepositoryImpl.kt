package ru.yan.database.repository.operation.custom

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import ru.yan.database.model.nsi.types.HappinessOperationType
import java.util.*

class CustomHappinessOperationRepositoryImpl: CustomHappinessOperationRepository {

    @PersistenceContext
    private lateinit var em: EntityManager

    override fun happiness(countryId: UUID): Double {
        val query = em.createNativeQuery(
            "SELECT COALESCE(SUM(ho.quantity), 0.0) / (SELECT COALESCE(SUM(ho1.quantity), 1.0) " +
                    "    FROM operation.happiness_operation as ho1 " +
                    "    WHERE ho1.country_uuid = :countryId " +
                    "    AND ho1.type in ('${HappinessOperationType.BuildingDestroyed}'," +
                    "                     '${HappinessOperationType.BuildingDowngrade}'," +
                    "                     '${HappinessOperationType.SalaryDelay}'," +
                    "                     '${HappinessOperationType.PeopleNotCured}'," +
                    "                     '${HappinessOperationType.BuildingMothballed}'," +
                    "                     '${HappinessOperationType.FoodNotEnough}'," +
                    "                     '${HappinessOperationType.WaterNotEnough}'," +
                    "                     '${HappinessOperationType.JobsNotEnough}'," +
                    "                     '${HappinessOperationType.PensionDelay}'," +
                    "                     '${HappinessOperationType.WarLose}'," +
                    "                     '${HappinessOperationType.BattleLose}') " +
                    "    AND ho1.created_at > now() - make_interval(0, 0, 0, 1, 0, 0, 0)) " +
                    "FROM operation.happiness_operation as ho " +
                    "WHERE ho.country_uuid = :countryId AND " +
                    "ho.type in ('${HappinessOperationType.BuildingBuilt}'," +
                    "            '${HappinessOperationType.BuildingUpgrade}'," +
                    "            '${HappinessOperationType.SalaryPayment}'," +
                    "            '${HappinessOperationType.PeopleCured}'," +
                    "            '${HappinessOperationType.BuildingReactivated}'," +
                    "            '${HappinessOperationType.FoodEnough}'," +
                    "            '${HappinessOperationType.WaterEnough}'," +
                    "            '${HappinessOperationType.CountryCreated}'," +
                    "            '${HappinessOperationType.PensionPayment}'," +
                    "            '${HappinessOperationType.WarWin}'," +
                    "            '${HappinessOperationType.BattleWin}'" +
                    "            '${HappinessOperationType.WarCompleted}') AND " +
                    "ho.created_at > now() - make_interval(0, 0, 0, 1, 0, 0, 0)"
        )

        query.setParameter("countryId", countryId)

        return query.singleResult as Double
    }
}