package ru.yan.core

import ru.yan.database.model.nsi.dto.DtoBuildingProduct
import ru.yan.database.model.nsi.dto.DtoByproduct
import ru.yan.database.model.nsi.dto.DtoNsiBuilding
import ru.yan.database.model.nsi.dto.DtoNsiCombatUnit
import ru.yan.database.model.nsi.types.BattleStage
import ru.yan.database.model.nsi.types.BuildingStatus
import ru.yan.database.model.smuta.dto.DtoCombatUnit
import ru.yan.database.model.smuta.dto.DtoCountry
import ru.yan.database.model.smuta.dto.DtoCountryBuilding
import ru.yan.database.model.smuta.dto.DtoStorageResource

/**
 * User readable information
 */

/**
 * Конвертация статуса здания
 */
fun BuildingStatus.description() = when(this) {
    BuildingStatus.ConstructionProcess,
    BuildingStatus.RenovationProcess -> "Строительство"
    BuildingStatus.ProductionProcess -> "Производство"
    BuildingStatus.ConservationProcess -> "Консервация"
    BuildingStatus.ReactivationProcess -> "Расконсервация"
    BuildingStatus.DestroyProcess -> "Разрушение"
    BuildingStatus.Destroyed -> "Разрушено"
    BuildingStatus.Mothballed -> "Законсервировано"
    BuildingStatus.Ready -> "Готово"
}

/**
 * Конвертация стадии боя
 */
fun BattleStage.description() = when(this) {
    BattleStage.Search -> "Поиск противника"
    BattleStage.Preparation -> "Подготовка к сражению"
    BattleStage.Battle -> "Идет бой"
    BattleStage.Result -> "Бой окончен"
}

/**
 * Описание страны
 */
fun DtoCountry.description() =
    "Название ${this.name}\n" +
    "Площадь ${this.area}\n"

/**
 * Описание здания из справочника
 */
fun DtoNsiBuilding.description() =
    "Название: ${this.name}\n" +
    "Рабочих мест(за уровень): ${this.workplacesByLevel}\n" +
    "Зарплата рабочему(за рабочее место): ${this.employeeSalary}\n" +
    "Занимаемая площадь(за уровень): ${this.areaByLevel}\n" +
    "Время строительства(за уровень): ${this.constructionTimeByLevel} сек.\n" +
    "Цена строительства(за уровень): ${this.priceForLevel}\n" +
    "Время производства: ${this.productionTime} сек.\n\n" +
    "Описание: ${this.description}"

/**
 * Описание построенного здания
 */
fun DtoCountryBuilding.description() =
    "Уровень: ${this.level}\n" +
    "Износ здания: ${this.efficiency}\n" +
    "Статус: ${this.status.description()}\n\n" +
    this.nsiBuilding.description()

/**
 * Описание производимой продукции
 */
fun DtoBuildingProduct.description() =
    "Название: ${this.resource.name}\n" +
    "Описание: ${this.resource.description}\n" +
    "Кол-во товара за уровень: ${this.quantityPerLevel}\n\n" +
    (
        this.byproducts.foldOrNull("Побочные продукты:\n\n") { a, b ->
            a + b.description() + "\n\n"
        } ?: "Побочные продукты: Отсутствуют"
    )

/**
 * Описание побочной продукции
 */
fun DtoByproduct.description() =
    "Название: ${this.resource.name}\n" +
    "Описание: ${this.resource.description}\n" +
    "Кол-во товара за единицу основного товара: ${this.quantityPerProduct}"

/**
 * Описание боевого подразделения из справочника
 */
fun DtoNsiCombatUnit.description() =
    "Название: ${this.name}\n" +
    "Описание: ${this.description}\n" +
    "Урон: ${this.damage}\n" +
    "Радиус атаки: ${this.attackRange}\n" +
    "Броня: ${this.armor}\n" +
    "Скорость: ${this.speed}\n"

/**
 * Описание боевого подразделения
 */
fun DtoCombatUnit.description() =
    "Название: ${this.nsiCombatUnit.name}\n" +
    "Опыт: ${this.experience}"