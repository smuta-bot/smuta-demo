package ru.yan.database.model.smuta.dto

import ru.yan.database.model.nsi.dto.toDto
import ru.yan.database.model.smuta.*

fun User.toDto() = DtoUser(
    this.id,
    this.telegramId,
    this.vkId,
    this.userActivity,
    this.createdAt,
    this.activityAt,
    this.deletedAt
)

fun Country.toDto() = DtoCountry(
    this.id,
    this.name,
    this.area,
    this.morbidity,
    this.mortality,
    this.mobilization,
    this.populations.map { it.toDto() },
    this.buildings.map { it.toDto() },
    this.storageResources.map { it.value.toDto() },
    this.resourceEffects.map { it.toDto() },
    this.marketProducts.map { it.toDto() },
    this.createdAt,
    this.deletedAt
)

fun Population.toDto() = DtoPopulation(
    this.id,
    this.healthStatus,
    this.quantity,
    this.gender,
    this.ageGroup
)

fun CountryBuilding.toDto() = DtoCountryBuilding(
    this.id,
    this.status,
    this.level,
    this.efficiency,
    this.nsiBuilding.toDto()
)

fun Product.toDto() = DtoProduct(
    this.id,
    this.buildingProduct.toDto()
)

fun StorageResource.toDto() = DtoStorageResource(
    this.id,
    this.quantity,
    this.resource.toDto()
)

fun CountryResourceEffect.toDto() = DtoCountryResourceEffect(
    this.id,
    this.quantity,
    this.nsiResourceEffect.toDto()
)

fun MarketProduct.toDto() = DtoMarketProduct(
    this.id,
    this.price,
    this.quantity,
    this.resource.toDto()
)

fun Notification.toDto() = DtoNotification(
    this.id,
    this.message,
    this.isImportant,
    this.notificationCategory.toDto()
)

fun CombatUnit.toDto() = DtoCombatUnit(
    this.id,
    this.status,
    this.quantity,
    this.experience,
    this.nsiCombatUnit.toDto()
)