package ru.yan.database.model.task.dto

import ru.yan.database.model.nsi.dto.DtoNsiBuilding
import ru.yan.database.model.task.BuildingSalaryTask
import java.util.*

/**
 * DTO для [BuildingSalaryTask]
 */
data class DtoBuildingSalaryTask(
    val id: UUID,
    val nsiBuilding: DtoNsiBuilding,
)
