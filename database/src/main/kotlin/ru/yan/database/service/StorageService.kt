package ru.yan.database.service

import ru.yan.database.exceptions.NotFoundException
import ru.yan.database.model.smuta.dto.DtoStorageResource
import ru.yan.database.model.smuta.dto.DtoUser

/**
 * Сервис для работы с хранилищем
 */
interface StorageService {

    /**
     * Получить список ресурсов в хранилище
     *
     * @param user Пользователь, запросивший данные
     *
     * @throws NotFoundException
     */
    fun getStorageResources(user: DtoUser): List<DtoStorageResource>
}