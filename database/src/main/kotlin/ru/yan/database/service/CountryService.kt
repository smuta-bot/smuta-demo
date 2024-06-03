package ru.yan.database.service

import ru.yan.database.model.smuta.dto.DtoUser
import ru.yan.database.model.smuta.dto.DtoCountry

/**
 * Сервис для работы с сущностью "Страна"
 */
interface CountryService {
    /**
     * Создание страны
     *
     * @param user Зарегистрированный пользователь
     * @param name Название страны
     */
    fun create(user: DtoUser, name: String): DtoCountry

    /**
     * Получить страну, которой управляет пользователь
     *
     * @param user Зарегистрированный пользователь
     */
    fun getByUser(user: DtoUser): DtoCountry?
}