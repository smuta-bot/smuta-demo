package ru.yan.database.model.nsi.types

/**
 * Тип операции, проводимой над показателями страны
 */
enum class CountryStateOperationType {
    /**
     * Операция над площадью страны
     */
    AreaType,

    /**
     * Операция над уровнем заболеваемости
     */
    MorbidityType,

    /**
     * Операция над уровнем смертности
     */
    MortalityType
}