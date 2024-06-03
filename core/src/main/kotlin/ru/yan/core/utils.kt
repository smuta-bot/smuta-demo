package ru.yan.core

import org.springframework.data.domain.Page
import ru.yan.core.bot.message.InlineButton
import ru.yan.database.model.smuta.dto.DtoUser
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

/**
 * Получение Id пользователя в мессенджере
 */
fun DtoUser.getMessengerId() = this.telegramId ?: this.vkId!!

/**
 * Получение кнопок управления страницами(для того что бы листать вперед/назад)
 */
fun <T> Page<T>.getControlButtons(): List<List<InlineButton>>? {
    return if (this.number == this.totalPages - 1 && this.totalPages > 1) {
        listOf(
            listOf(InlineButton("<", "page_back"))
        )
    } else if (this.number == 0 && this.totalPages > 1) {
        listOf(
            listOf(InlineButton(">", "page_forward"))
        )
    } else if (this.totalPages > 1 && this.number > 0 && this.number < this.totalPages)  {
        listOf(
            listOf(InlineButton("<", "page_back"), InlineButton(">", "page_forward"))
        )
    } else {
        null
    }
}

/**
 * Конвертация страницы в клавиатуру под сообщением
 */
fun <T> Page<T>.toKeyboard(converter: (item: T) -> InlineButton) =
    this.mapIndexed { _, item -> listOf(converter(item)) }
        .let {
            val controlButtons = this.getControlButtons()
            if (controlButtons != null) {
                it.plus(controlButtons)
            } else {
                it
            }
        }

/**
 * Конвертирование объекта с типом Any в объект пользовательского типа.
 * Расширение создано для того что бы использовать его в задачах при конвертации параметров в нужный тип
 */
fun <T: Any> Any?.convertTo(obj: KClass<T>): T? {
    if (this == null) {
        return null
    }

    if (this is HashMap<*, *>) {
        val v = obj.primaryConstructor!!.parameters.map { this[it.name] }
        return obj.primaryConstructor?.call(
            *v.toTypedArray()
        )
    }

    if (this::class.isData) {
        return this as T?
    }

    return null
}

/**
 * Слияние итерированной структуры в один элемент или возврат null если структура пуста
 */
fun <T, R> Iterable<T>.foldOrNull(initial: R, operation: (R, T) -> R): R? {
    return this.firstOrNull()?.let {
        this.fold(initial, operation)
    }
}

/**
 * Валидация имени страны
 */
fun countryNameValidate(text: String?): Boolean {
    if (!text.isNullOrEmpty()) {
        if (!text.startsWith("/")) {
            return true
        }
    }

    return false
}