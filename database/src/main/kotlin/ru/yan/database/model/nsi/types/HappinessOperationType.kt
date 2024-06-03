package ru.yan.database.model.nsi.types

/**
 * Тип операции над уровнем счастья
 */
enum class HappinessOperationType {
    /**
     * Здание построено
     */
    BuildingBuilt,

    /**
     * Здание уничтожено
     */
    BuildingDestroyed,

    /**
     * Здание улучшено
     */
    BuildingUpgrade,

    /**
     * Здание разрушено
     */
    BuildingDowngrade,

    /**
     * Выплата зарплаты
     */
    SalaryPayment,

    /**
     * Задержка зарплаты
     */
    SalaryDelay,

    /**
     * Люди вылечены
     */
    PeopleCured,

    /**
     * Люде не вылечены
     */
    PeopleNotCured,

    /**
     * Здание законсервировано
     */
    BuildingMothballed,

    /**
     * Здание расконсервировано
     */
    BuildingReactivated,

    /**
     * Еды достаточно
     */
    FoodEnough,

    /**
     * Еды не хватает
     */
    FoodNotEnough,

    /**
     * Воды достаточно
     */
    WaterEnough,

    /**
     * Воды недостаточно
     */
    WaterNotEnough,

    /**
     * Не хватает рабочих мест
     */
    JobsNotEnough,

    /**
     * Выплата пенсии
     */
    PensionPayment,

    /**
     * Задержка пенсии
     */
    PensionDelay,

    /**
     * Страна создана
     */
    CountryCreated,

    /**
     * Война выиграна
     */
    WarWin,

    /**
     * Война проиграна
     */
    WarLose,

    /**
     * Война завершена
     */
    WarCompleted,

    /**
     * Сражение выиграно
     */
    BattleWin,

    /**
     * Сражение проиграно
     */
    BattleLose
}