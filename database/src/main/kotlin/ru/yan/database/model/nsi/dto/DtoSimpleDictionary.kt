package ru.yan.database.model.nsi.dto

import java.util.UUID

/**
 * DTO, описывающая обычную запись из справочника
 */
data class DtoSimpleDictionary(
    val id: UUID,
    val name: String,
    val description: String
)
