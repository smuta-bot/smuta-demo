package ru.yan.database.model.operation.dto

import ru.yan.database.model.nsi.dto.toDto
import ru.yan.database.model.operation.*

fun PopulationOperation.toDto() = DtoPopulationOperation(
    this.id,
    this.type,
    this.quantity,
    this.gender,
    this.ageGroup,
    this.createdAt
)

fun StorageResourceOperation.toDto() = DtoStorageResourceOperation(
    this.id,
    this.quantity,
    this.resource.toDto(),
    this.createdAt
)

fun BuildingOperation.toDto() = DtoBuildingOperation(
    this.id,
    this.status,
    this.level,
    this.efficiency,
    this.createdAt
)

fun CountryStateOperation.toDto() = DtoCountryStateOperation(
    this.id,
    this.type,
    this.quantity,
    this.createdAt
)

fun ProductOperation.toDto() = DtoProductOperation(
    this.id,
    this.type,
    this.buildingProduct.toDto(),
    this.createdAt
)

fun HappinessOperation.toDto() = DtoHappinessOperation(
    this.id,
    this.type,
    this.quantity,
    this.createdAt
)

fun MarketProductOperation.toDto() = DtoMarketProductOperation(
    this.id,
    this.type,
    this.price,
    this.quantity,
    this.resource.toDto(),
    this.createdAt
)

fun CountryFightingUnit.toDto() = DtoCountryFightingUnit(
    this.id,
    this.combatUnitPurpose,
    this.nsiCombatUnit.toDto()
)

fun BattlefieldEffectOperation.toDto() = DtoBattlefieldEffectOperation(
    this.id,
    this.attribute,
    this.quantity
)

fun BattleOperation.toDto() = DtoBattleOperation(
    this.id,
    this.battleType,
    this.area,
    this.attackingCountry.id,
    this.defendingCountry,
    this.battlefield.toDto(),
    this.fightingUnits.map { it.toDto() },
    this.battlefieldEffectOperations.map { it.toDto() },
    this.createdAt
)

fun CombatUnitOperation.toDto() = DtoCombatUnitOperation(
    this.id,
    this.status,
    this.quantity,
    this.experience,
    this.nsiCombatUnit.toDto(),
    this.createdAt
)