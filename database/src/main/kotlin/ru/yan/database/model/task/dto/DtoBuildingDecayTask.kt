package ru.yan.database.model.task.dto

import ru.yan.database.model.nsi.dto.DtoNsiBuilding
import ru.yan.database.model.task.BuildingDecayTask
import java.util.*

/**
 * DTO для [BuildingDecayTask]
 */
data class DtoBuildingDecayTask (
    val id: UUID,
    val nsiBuilding: DtoNsiBuilding
)