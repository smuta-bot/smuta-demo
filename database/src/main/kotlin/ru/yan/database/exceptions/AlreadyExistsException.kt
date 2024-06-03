package ru.yan.database.exceptions

import kotlin.reflect.KClass
import kotlin.reflect.KProperty

/**
 * Исключение, сигнализирующее о том что сущность уже существует
 */
class AlreadyExistsException: RuntimeException {
    constructor(entity: String, id: Any): super("Entity $entity with id $id already exists")

    constructor(message: String): super(message)
}

fun alreadyExists(entity: String, id: Any): Nothing =
    throw AlreadyExistsException(entity, id)

fun alreadyExists(entity: KClass<*>, id: Any): Nothing =
    throw AlreadyExistsException(entity.simpleName ?: "Entity", id)

fun alreadyExistsWith(entity: KClass<*>, with:  KClass<*>, id: Any): Nothing =
    throw AlreadyExistsException("${entity.simpleName ?: "Entity"} with ${with.simpleName ?: "Entity"} id $id already exists")

fun alreadyExistsWith(entity: KClass<*>, field: KProperty<*>, fieldValue: Any): Nothing =
    throw AlreadyExistsException("${entity.simpleName ?: "Entity"} with ${field.name} = $fieldValue already exists")
