package ru.yan.database.model.nsi.types

/**
 * Тип операции над продукцией здания
 */
enum class ProductOperationType {
    /**
     * Продукция добавлено к производству
     */
    Add,

    /**
     * Продукция изъята из производства
     */
    Remove
}