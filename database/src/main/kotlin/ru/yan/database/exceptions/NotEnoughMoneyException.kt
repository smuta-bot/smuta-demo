package ru.yan.database.exceptions

/**
 * Исключение, сигнализирующее о том что не хватает денег
 *
 * @param quantity Кол-во денег
 */
class NotEnoughMoneyException(
    quantity: Double
): RuntimeException("$quantity money missing")

fun notEnoughMoney(
    quantity: Double
): Nothing = throw NotEnoughMoneyException(quantity)