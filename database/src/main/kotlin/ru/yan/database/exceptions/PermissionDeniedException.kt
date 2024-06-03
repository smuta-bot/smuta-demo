package ru.yan.database.exceptions

/**
 * Исключение, сигнализирующее о том что не хватает прав
 */
class PermissionDeniedException: RuntimeException("You have no rights")

fun permissionDenied(): Nothing = throw PermissionDeniedException()