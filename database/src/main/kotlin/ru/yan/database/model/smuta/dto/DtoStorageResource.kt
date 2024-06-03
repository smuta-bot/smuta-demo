package ru.yan.database.model.smuta.dto

import ru.yan.database.model.nsi.dto.DtoSimpleDictionary
import ru.yan.database.model.smuta.StorageResource
import java.util.*

/**
 * DTO для [StorageResource]
 */
data class DtoStorageResource(
    val id: UUID,
    val quantity: Double,
    val resource: DtoSimpleDictionary
)
