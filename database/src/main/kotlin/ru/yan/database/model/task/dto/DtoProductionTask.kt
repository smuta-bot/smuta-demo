package ru.yan.database.model.task.dto

import ru.yan.database.model.nsi.dto.DtoNsiBuilding
import ru.yan.database.model.task.ProductionTask
import java.util.UUID

/**
 * DTO для [ProductionTask]
 */
data class DtoProductionTask(
    val id: UUID,
    val nsiBuilding: DtoNsiBuilding,
    val productionProducts: List<DtoProductionProduct>
)