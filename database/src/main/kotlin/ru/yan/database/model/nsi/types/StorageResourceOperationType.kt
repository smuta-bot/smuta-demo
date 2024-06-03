package ru.yan.database.model.nsi.types

/**
 * Тип операции над ресурсами в хранилище страны
 */
enum class StorageResourceOperationType {
    /**
     * Инициализация
     */
    Initialize,

    /**
     * Ресурсы изъяты для производства товара
     */
    ProductionTake,

    /**
     * Ресурсы возвращены при отмене производства товара
     */
    ProductionReturn,

    /**
     * Ресурсы взяты для строительства
     */
    ConstructionTake,

    /**
     * Ресурсы возвращены в результате отмены строительства
     */
    ConstructionReturn,

    /**
     * Ресурс взят для выплаты зарплаты
     */
    SalaryTake,

    /**
     * Ресурс взят для выплаты пенсии
     */
    PensionTake,

    /**
     * Ресурс взят для уплаты налога
     */
    Tax,

    /**
     * Ресурс взят для лечения
     */
    TreatmentTake,

    /**
     * Ресурс взят для войны
     */
    WarTake,

    /**
     * Ресурс вернулся с войны
     */
    WarReturn,

    /**
     * Ресурс взят для учений
     */
    ExerciseTake,

    /**
     * Ресурс вернулся с учений
     */
    ExerciseReturn,

    /**
     * Ресурс получен в результате войны
     */
    WarProfit,

    /**
     * Ресурс взят как контрибуция
     */
    Contribution,

    /**
     * Ресурс взят для внутреннего потребления
     */
    DomesticConsumption,

    /**
     * Ресурс взят для продажи
     */
    TakenForSale,

    /**
     * Ресурс изъят с продажи
     */
    ReturnFromSale,

    /**
     * Ресурс куплен
     */
    Purchased,

    /**
     * Ресурс продан
     */
    Sold,

    /**
     * Товар произведен
     */
    Produced,

    /**
     * Ресурс является прибылью
     */
    Profit
}