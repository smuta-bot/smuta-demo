package ru.yan.database.exceptions

/**
 * Исключение, сигнализирующее о том что не хватает территории
 *
 * @param quantity Кол-во территории
 */
class NotEnoughAreaException(
    quantity: Double
): RuntimeException("$quantity area missing")

fun notEnoughArea(
    quantity: Double
): Nothing = throw NotEnoughAreaException(quantity)