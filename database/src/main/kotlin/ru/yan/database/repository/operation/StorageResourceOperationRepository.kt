package ru.yan.database.repository.operation

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.yan.database.model.operation.StorageResourceOperation
import java.util.*

@Repository
interface StorageResourceOperationRepository: JpaRepository<StorageResourceOperation, UUID>