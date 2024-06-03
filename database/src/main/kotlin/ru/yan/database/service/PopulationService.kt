package ru.yan.database.service

import ru.yan.database.exceptions.NotFoundException
import ru.yan.database.model.smuta.dto.DtoPopulation
import ru.yan.database.model.smuta.dto.DtoUser

/**
 * Сервис для работы с населением
 */
interface PopulationService {

    /**
     * Получить список людей
     *
     * @param user Пользователь, запросивший данные
     *
     * @throws NotFoundException
     */
    fun getPopulation(user: DtoUser): List<DtoPopulation>
}