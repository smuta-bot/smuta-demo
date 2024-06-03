package ru.yan.database.repository.operation

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.yan.database.model.operation.HappinessOperation
import ru.yan.database.repository.operation.custom.CustomHappinessOperationRepository
import java.util.*

@Repository
interface HappinessOperationRepository: JpaRepository<HappinessOperation, UUID>, CustomHappinessOperationRepository