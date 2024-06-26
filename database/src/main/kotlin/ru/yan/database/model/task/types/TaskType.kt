package ru.yan.database.model.task.types

/**
 * Тип задачи
 */
enum class TaskType {
    /**
     * Задача на лечение
     */
    Treatment,

    /**
     * Задача на потребление еды
     */
    Food,

    /**
     * Задача на потребление воды
     */
    Thirst,

    /**
     * Задача на инфицирование
     */
    Infection,

    /**
     * Задача на размножение
     */
    Born,

    /**
     * Задача на смертность
     */
    Death,

    /**
     * Задача на взросление
     */
    GrowUp,

    /**
     * Задача на производство
     */
    Production,

    /**
     * Задача на выплату зарплаты зданием
     */
    BuildingSalary,

    /**
     * Задача на выплату пенсий
     */
    Pension,

    /**
     * Задача на строительство
     */
    Construction,

    /**
     * Задача на гниение здания
     */
    BuildingDecay,

    /**
     * Задача на получение прибыли
     */
    Profit,

    /**
     * Задача на сражение
     */
    Battle,

    /**
     * Задача на работу с боевым подразделением
     */
    CombatUnit,

    /**
     * Задача на разложение боевого подразделения(потерю опыта)
     */
    CombatUnitDecay,

    /**
     * Задача на выплату зарплат бойцам подразделения
     */
    CombatUnitSalary
}