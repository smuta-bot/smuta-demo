package ru.yan.database.model.nsi.types

/**
 * Статус здания
 */
enum class BuildingStatus {
    /**
     * Идет строительство здания
     */
    ConstructionProcess,

    /**
     * Идет ремонт здания
     */
    RenovationProcess,

    /**
     * Идет процесс консервации
     */
    ConservationProcess,

    /**
     * Идет процесс расконсервации
     */
    ReactivationProcess,

    /**
     * Здание в процессе уничтожения
     */
    DestroyProcess,

    /**
     * Идет производство продукции
     */
    ProductionProcess,

    /**
     * Здание готово
     */
    Ready,

    /**
     * Уничтожено
     */
    Destroyed,

    /**
     * Законсервировано
     */
    Mothballed,
}