package ru.yan.database.model.nsi.types

/**
 * Статус боевого подразделения
 */
enum class CombatUnitStatus {
    /**
     * Готово
     */
    Ready,

    /**
     * Сражается
     */
    Battle,

    /**
     * На учениях
     */
    Exercise,

    /**
     * Формирование
     */
    Formation,

    /**
     * Расформирование
     */
    Disbandment,

    /**
     * Расформировано
     */
    Disbanded
}