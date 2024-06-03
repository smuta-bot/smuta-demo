package ru.yan.database.service

import ru.yan.database.exceptions.AlreadyExistsException
import ru.yan.database.exceptions.NotFoundException
import ru.yan.database.model.smuta.dto.DtoCountryBuilding
import ru.yan.database.model.smuta.dto.DtoUser

/**
 * Сервис для работы со зданием "Больница"
 */
interface HospitalService {
    /**
     * Получить здание "Больница"
     *
     * @param user Пользователь, запросивший данные
     */
    fun getCountryHospital(user: DtoUser): DtoCountryBuilding?

    /**
     * Запустить лечение населения
     *
     * @param user Пользователь
     *
     * @throws AlreadyExistsException
     * @throws NotFoundException
     */
    fun startTreatment(user: DtoUser)

    /**
     * Прекратить лечение населения
     *
     * @param user Пользователь
     *
     * @throws AlreadyExistsException
     * @throws NotFoundException
     */
    fun stopTreatment(user: DtoUser)
}