package ru.yan.database.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import ru.yan.database.exceptions.*
import ru.yan.database.model.nsi.dto.DtoNsiBuilding
import ru.yan.database.model.smuta.dto.DtoCountryBuilding
import ru.yan.database.model.operation.dto.DtoBuildingOperation
import ru.yan.database.model.smuta.dto.DtoUser
import java.util.*

/**
 * Сервис для работы со зданиями
 */
interface BuildingService {
    /**
     * Получить список зданий из справочника которые можно построить в стране.
     * Здания уже построенные должны быть исключены из списка
     *
     * @param user Пользователь, запросивший данные
     * @param pageRequest Номер страницы и кол-во элементов на ней
     */
    fun getNsiBuildingsForBuild(user: DtoUser, pageRequest: PageRequest): Page<DtoNsiBuilding>

    /**
     * Получить здание из справочника по id
     *
     * @param nsiBuildingId Id здания в справочнике
     */
    fun getNsiBuilding(nsiBuildingId: UUID): DtoNsiBuilding?

    /**
     * Получить функционирующие здания в стране
     *
     * @param user Пользователь, запросивший данные
     * @param pageRequest Номер страницы и кол-во элементов на ней
     */
    fun getCountryBuildings(user: DtoUser, pageRequest: PageRequest): Page<DtoCountryBuilding>

    /**
     * Получить здание страны
     *
     * @param user Пользователь, запросивший данные
     * @param nsiBuildingId Id здания из справочника
     */
    fun getCountryBuilding(user: DtoUser, nsiBuildingId: UUID): DtoCountryBuilding?

    /**
     * Строительство здания
     *
     * @param user Пользователь, который строит здание
     * @param nsiBuildingId Id здания из справочника для постройки
     * @param level Какого уровня будет построенное здание
     *
     * @throws AlreadyExistsException
     * @throws NotFoundException
     * @throws NotEnoughMoneyException
     * @throws NotEnoughAreaException
     * @throws NotEnoughResourceException
     */
    fun buildBuilding(user: DtoUser, nsiBuildingId: UUID, level: Int = 1): DtoBuildingOperation

    /**
     * Изменение уровня здания страны
     *
     * @param user Пользователь, который управляет зданием
     * @param nsiBuildingId Id здания из справочника
     * @param level Новый уровень
     *
     * @throws AlreadyExistsException
     * @throws NotFoundException
     * @throws NotEnoughMoneyException
     * @throws NotEnoughAreaException
     * @throws NotEnoughResourceException
     */
    fun changeBuildingLevel(user: DtoUser, nsiBuildingId: UUID, level: Int): DtoBuildingOperation

    /**
     * Консервация здания
     *
     * @param user Пользователь, который управляет зданием
     * @param nsiBuildingId Id здания из справочника
     *
     * @throws AlreadyExistsException
     * @throws NotFoundException
     * @throws NotEnoughMoneyException
     * @throws NotEnoughResourceException
     */
    fun conservationBuilding(user: DtoUser, nsiBuildingId: UUID): DtoBuildingOperation

    /**
     * Консервация здания
     *
     * @param user Пользователь, который управляет зданием
     * @param nsiBuildingId Id здания из справочника
     *
     * @throws AlreadyExistsException
     * @throws NotFoundException
     * @throws NotEnoughMoneyException
     * @throws NotEnoughResourceException
     */
    fun reactivateBuilding(user: DtoUser, nsiBuildingId: UUID): DtoBuildingOperation

    /**
     * Уничтожение здания
     *
     * @param user Пользователь, который управляет зданием
     * @param nsiBuildingId Id здания из справочника
     *
     * @throws AlreadyExistsException
     * @throws NotFoundException
     * @throws NotEnoughMoneyException
     */
    fun destroyBuilding(user: DtoUser, nsiBuildingId: UUID): DtoBuildingOperation

    /**
     * Остановка строительства
     *
     * @param user Пользователь, который управляет зданием
     * @param nsiBuildingId Id здания из справочника
     *
     * @throws AlreadyExistsException
     * @throws NotFoundException
     */
    fun stopConstruction(user: DtoUser, nsiBuildingId: UUID): DtoBuildingOperation
}