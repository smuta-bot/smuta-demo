package ru.yan.database.model.nsi.types

/**
 * Тип операции, проводимой над товаром на рынке
 */
enum class MarketProductOperationType {
    /**
     * Добавление
     */
    Add,

    /**
     * Покупка(какого то количества)
     */
    Purchased,

    /**
     * Покупка всего товара
     */
    PurchasedAll,

    /**
     * Изъятие из продажи
     */
    Remove
}