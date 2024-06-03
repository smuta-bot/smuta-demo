package ru.yan.database.model.task.dto

import ru.yan.database.model.nsi.dto.toDto
import ru.yan.database.model.operation.dto.toDto
import ru.yan.database.model.task.*

fun ProductionTask.toDto() = DtoProductionTask(
    this.id,
    this.nsiBuilding.toDto(),
    this.productionProducts.map { it.toDto() }
)

fun ProductionProduct.toDto() = DtoProductionProduct(
    this.id,
    this.quantity,
    this.resource.toDto(),
    this.deletedAt
)

fun TreatmentTask.toDto() = DtoTreatmentTask(
    this.id,
    this.populationOperations.map { it.toDto() }
)

fun ConstructionTask.toDto() = DtoConstructionTask(
    this.id,
    this.fromBuildingOperation?.toDto(),
    this.toBuildingOperation.toDto()
)

fun BuildingSalaryTask.toDto() = DtoBuildingSalaryTask(
    this.id,
    this.nsiBuilding.toDto()
)

fun BuildingDecayTask.toDto() = DtoBuildingDecayTask(
    this.id,
    this.nsiBuilding.toDto()
)

fun BattleTask.toDto() = DtoBattleTask(
    this.id,
    this.stage
)

fun CombatUnitTask.toDto() = DtoCombatUnitTask(
    this.id,
    this.fromCombatUnitOperation?.toDto(),
    this.toCombatUnitOperation.toDto()
)

fun CombatUnitSalaryTask.toDto() = DtoCombatUnitSalaryTask(
    this.id,
    this.nsiCombatUnit.toDto()
)

fun CombatUnitDecayTask.toDto() = DtoCombatUnitDecayTask(
    this.id,
    this.nsiCombatUnit.toDto()
)

fun Task.toDto() = DtoTask(
    this.id,
    this.type,
    this.duration,
    this.startedAt,
    this.deletedAt
)