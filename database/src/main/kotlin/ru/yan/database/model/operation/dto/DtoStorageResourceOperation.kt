package ru.yan.database.model.operation.dto

import ru.yan.database.model.nsi.dto.DtoSimpleDictionary
import ru.yan.database.model.operation.StorageResourceOperation
import ru.yan.database.model.smuta.dto.DtoCountry
import java.util.*

/**
 * DTO для [StorageResourceOperation]
 */
data class DtoStorageResourceOperation(
    val id: UUID,
    val quantity: Double,
    val resource: DtoSimpleDictionary,
    val createdAt: Date
)
