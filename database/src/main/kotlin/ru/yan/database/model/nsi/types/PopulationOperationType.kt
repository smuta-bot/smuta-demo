package ru.yan.database.model.nsi.types

/**
 * Тип операции, проводимой над структурой населения
 */
enum class PopulationOperationType {
    /**
     * Инициализация
     */
    Initialize,

    /**
     * Лечение
     */
    Treatment,

    /**
     * Выздоровление
     */
    Convalescence,

    /**
     * Рождение
     */
    Born,

    /**
     * Взросление
     */
    GrowUp,

    /**
     * Заражение
     */
    Infection,

    /**
     * Смерть от болезни
     */
    InfectionDied,

    /**
     * Отправлены на войну
     */
    WarTaken,

    /**
     * Вернулись с войны
     */
    WarReturn,

    /**
     * Смерть
     */
    Died
}