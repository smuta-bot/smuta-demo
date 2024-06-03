package ru.yan.database.model.nsi.dto

import ru.yan.database.model.nsi.NsiResourceEffect
import java.util.*

/**
 * DTO для [NsiResourceEffect]
 */
data class DtoNsiResourceEffect(
    val id: UUID,
    val name: String,
    val description: String,
    val resource: DtoSimpleDictionary
)
