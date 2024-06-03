package ru.yan.database.model.nsi.dto

import ru.yan.database.model.nsi.*

fun Resource.toDto() = DtoSimpleDictionary(
    this.id,
    this.name,
    this.description
)

fun NsiBuilding.toDto() = DtoNsiBuilding(
    this.id,
    this.workplacesByLevel,
    this.areaByLevel,
    this.priceForLevel,
    this.constructionTimeByLevel,
    this.destroyTime,
    this.destroyPriceForLevel,
    this.mothballedPriceByLevel,
    this.mothballedTime,
    this.employeeSalary,
    this.productionTime,
    this.name,
    this.description
)

fun BuildingResource.toDto() = DtoBuildingResource(
    this.id,
    this.quantityPerLevel,
    this.resource.toDto()
)

fun Byproduct.toDto() = DtoByproduct(
    this.id,
    this.quantityPerProduct,
    this.resource.toDto()
)

fun BuildingProduct.toDto() = DtoBuildingProduct(
    this.id,
    this.quantityPerLevel,
    this.resource.toDto(),
    this.productResources.map { it.toDto() },
    this.byproducts.map { it.toDto() }
)

fun BuildingProductResource.toDto() = DtoBuildingProductResource(
    this.id,
    this.quantity,
    this.resource.toDto()
)

fun NsiResourceEffect.toDto() = DtoNsiResourceEffect(
    this.id,
    this.name,
    this.description,
    this.resource.toDto()
)

fun NotificationCategory.toDto() = DtoNotificationCategory(
    this.id,
    this.name
)

fun NsiCombatUnit.toDto(): DtoNsiCombatUnit = DtoNsiCombatUnit(
    this.id,
    this.name,
    this.description,
    this.damage,
    this.speed,
    this.armor,
    this.attackRange,
    this.salary
)

fun NsiBattlefieldEffect.toDto() = DtoNsiBattlefieldEffect(
    this.id,
    this.attribute,
    this.min,
    this.max
)

fun CombatUnitResource.toDto() = DtoCombatUnitResource(
    this.id,
    this.quantity,
    this.resource.toDto()
)

fun Battlefield.toDto() = DtoBattlefield(
    this.id,
    this.dayTime,
    this.weather,
    this.nsiBattlefieldEffects.map { it.toDto() }
)