package ru.yan.core.general

/**
 * Тип подтверждения, которое запрашивается у пользователя
 */
enum class ConfirmationType {
    /**
     * На запуск производства
     */
    StartProduction,

    /**
     * На запуск уничтожения здания
     */
    StartDestroy,

    /**
     * На запуск консервации
     */
    StartConservation,

    /**
     * На остановку производства
     */
    StopProduction,

    /**
     * На остановку строительства
     */
    StopConstruction,

    /**
     * На отмену уничтожения здания
     */
    StopDestroy,

    /**
     * На отмену консервации
     */
    StopConservation,

    /**
     * На отмену расконсервации
     */
    StopReactivation,

    /**
     * На расконсервацию
     */
    Reactivate,

    /**
     * На исключение продукта из списка производимых товаров
     */
    ExcludeProduct,

    /**
     * На открытие больницы(начало процесса лечения населения)
     */
    OpenHospital,

    /**
     * На закрытие больницы(прекращение лечения населения)
     */
    CloseHospital,

    /**
     * На остановку атаки
     */
    StopAttack,

    /**
     * На капитуляцию
     */
    Surrender
}