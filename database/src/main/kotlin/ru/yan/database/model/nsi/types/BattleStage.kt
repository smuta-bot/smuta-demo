package ru.yan.database.model.nsi.types

/**
 * Стадии сражений
 */
enum class BattleStage {
    /**
     * Поиск противника
     */
    Search,

    /**
     * Подготовка к сражению
     */
    Preparation,

    /**
     * Сражение
     */
    Battle,

    /**
     * Результат боя
     */
    Result
}