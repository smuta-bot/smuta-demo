package ru.yan.database.model.nsi.dto

import ru.yan.database.model.nsi.Byproduct
import java.util.*

/**
 * DTO для [Byproduct]
 */
data class DtoByproduct(
    val id: UUID,
    val quantityPerProduct: Double,
    val resource: DtoSimpleDictionary
)
