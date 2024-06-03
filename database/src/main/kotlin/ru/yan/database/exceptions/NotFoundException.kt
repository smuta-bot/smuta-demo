package ru.yan.database.exceptions

import kotlin.reflect.KClass

class NotFoundException: RuntimeException {
    constructor(entity: String, id: Any, message: String? = null): super("$entity with id '$id' not found${message?.let { " :$it" } ?: ""}")
    constructor(message:String): super(message)
}

fun notFound(entity: String, id: Any): Nothing = throw NotFoundException(entity, id)

fun notFoundBy(entity: KClass<*>, by: KClass<*>, id: Any): Nothing =
    throw NotFoundException("${entity.simpleName ?: "Entity"} by ${by.simpleName ?: "Entity"} wit id $id not found")

fun notFound(entity: KClass<*>, id: Any, message: String? = null): Nothing =
    throw NotFoundException(entity.simpleName ?: "Entity", id, message)
