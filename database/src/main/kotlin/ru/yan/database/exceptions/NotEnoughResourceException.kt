package ru.yan.database.exceptions

import ru.yan.database.model.nsi.dto.DtoSimpleDictionary

/**
 * Исключение, сигнализирующее о том что не хватает ресурса для строительства/производства и т.п.
 *
 * @param resource Описание ресурса
 * @param quantity Уровень недостачи
 */
class NotEnoughResourceException(
    resource: DtoSimpleDictionary,
    quantity: Double
): RuntimeException("Missing $quantity ${resource.name}")

fun notEnoughResource(
    resource: DtoSimpleDictionary,
    quantity: Double
): Nothing = throw NotEnoughResourceException(resource, quantity)

fun notEnoughResource(
    resource: DtoSimpleDictionary,
    quantity: Long
): Nothing = throw NotEnoughResourceException(resource, quantity.toDouble())