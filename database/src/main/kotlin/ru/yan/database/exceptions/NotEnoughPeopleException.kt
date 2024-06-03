package ru.yan.database.exceptions

/**
 * Исключение, сигнализирующее о том что не хватает людей
 *
 * @param quantity Кол-во людей
 */
class NotEnoughPeopleException(
    quantity: Long
): RuntimeException("$quantity people missing")

fun notEnoughPeople(
    quantity: Long
): Nothing = throw NotEnoughPeopleException(quantity)