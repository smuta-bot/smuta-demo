package ru.yan.database.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import ru.yan.database.exceptions.AlreadyExistsException
import ru.yan.database.exceptions.NotFoundException
import ru.yan.database.model.nsi.dto.DtoBuildingProduct
import ru.yan.database.model.operation.dto.DtoProductOperation
import ru.yan.database.model.smuta.dto.DtoProduct
import ru.yan.database.model.smuta.dto.DtoUser
import java.util.*

/**
 * Сервис для работы с продукцией, выпускаемой зданиями
 */
interface ProductionService {
    /**
     * Получить список текущей продукции выпускаемой зданием из справочника
     *
     * @param user Пользователь, запросивший данные
     * @param nsiBuildingId Id здания из справочника
     * @param pageRequest Номер страницы и кол-во элементов на ней
     */
    fun getProducts(user: DtoUser, nsiBuildingId: UUID, pageRequest: PageRequest): Page<DtoProduct>

    /**
     * Список продукции, которую здание может выпускать
     *
     * @param buildingId Id здания
     * @param pageRequest Номер страницы и кол-во элементов на ней
     */
    fun getBuildingProducts(buildingId: UUID, pageRequest: PageRequest): Page<DtoBuildingProduct>

    /**
     * Получить описание продукта по id
     *
     * @param buildingProductId Id продукта
     *
     * @throws NotFoundException
     */
    fun getBuildingProduct(buildingProductId: UUID): DtoBuildingProduct

    /**
     * Допустить продукт к производству
     *
     * @param user Пользователь
     * @param nsiBuildingId Id здания из справочника
     * @param buildingProductId Id продукта, производимого зданием
     *
     * @throws NotFoundException
     */
    fun addProductToProduction(user: DtoUser, nsiBuildingId: UUID, buildingProductId: UUID): DtoProductOperation

    /**
     * Изъять продукт из производства
     *
     * @param user Пользователь
     * @param nsiBuildingId Id здания из справочника
     * @param buildingProductId Id продукта, производимого зданием
     *
     * @throws NotFoundException
     */
    fun removeProductFromProduction(user: DtoUser, nsiBuildingId: UUID, buildingProductId: UUID): DtoProductOperation

    /**
     * Запуск производства в здании
     *
     * @param user Пользователь
     * @param nsiBuildingId Id здания из справочника
     *
     * @throws AlreadyExistsException
     * @throws NotFoundException
     */
    fun startProduction(user: DtoUser, nsiBuildingId: UUID)

    /**
     * Остановка производства в здании
     *
     * @param user Пользователь
     * @param nsiBuildingId Id здания из справочника
     *
     * @throws AlreadyExistsException
     * @throws NotFoundException
     */
    fun stopProduction(user: DtoUser, nsiBuildingId: UUID)
}