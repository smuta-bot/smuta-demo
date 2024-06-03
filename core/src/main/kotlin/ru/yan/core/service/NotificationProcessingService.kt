package ru.yan.core.service

/**
 * Сервис для работы с состоянием сервиса
 * Задачами
 * Подписками
 */
interface NotificationProcessingService {
    fun task()

    fun notificateUsers()

    fun importantNotificateUsers()
}