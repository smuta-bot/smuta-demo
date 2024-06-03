package ru.yan.database.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import ru.yan.database.exceptions.AlreadyExistsException
import ru.yan.database.exceptions.NotEnoughMoneyException
import ru.yan.database.exceptions.NotEnoughResourceException
import ru.yan.database.exceptions.NotFoundException
import ru.yan.database.model.operation.dto.DtoMarketProductOperation
import ru.yan.database.model.smuta.dto.DtoMarketProduct
import ru.yan.database.model.smuta.dto.DtoStorageResource
import ru.yan.database.model.smuta.dto.DtoUser
import java.util.*

/**
 * Сервис для работы с рынком(выставление/изъятие/покупка товара)
 */
interface MarketService {
    /**
     * Получение товара на хранении
     *
     * @param storageResourceId Id товара на хранении
     */
    fun getStorageResource(storageResourceId: UUID): DtoStorageResource

    /**
     * Получение товара с рынка
     *
     * @param marketProductId Id товара на рынке
     */
    fun getMarketProduct(marketProductId: UUID): DtoMarketProduct

    /**
     * Получение товара с рынка, которыми владеет пользователь
     *
     * @param user Пользователь
     * @param pageRequest Страница
     */
    fun getMarketProductByUser(user: DtoUser, pageRequest: PageRequest): Page<DtoMarketProduct>

    /**
     * Получение списка товаров на хранении
     *
     * @param user Пользователь, запросивший данные
     * @param pageRequest Страница
     */
    fun getStorageResources(user: DtoUser, pageRequest: PageRequest): Page<DtoStorageResource>

    /**
     * Получение списка товаров на продаже, за исключением товаров пользователя
     *
     * @param user Пользователь, запросивший информацию и чьи товары должны быть исключены
     * @param pageRequest Страница
     */
    fun  getMarketProductsExcludeUser(user: DtoUser, pageRequest: PageRequest): Page<DtoMarketProduct>

    /**
     * Добавление товара на рынок
     *
     * @param storageResourceId Id ресурса в хранилище
     * @param quantity Кол-во ресурса для продажи
     * @param price Цена за единицу
     *
     * @throws NotFoundException
     * @throws NotEnoughMoneyException
     * @throws NotEnoughResourceException
     */
    fun sellStorageResource(storageResourceId: UUID, quantity: Long, price: Double): DtoMarketProductOperation

    /**
     * Покупка товара
     *
     * @param user Пользователь, покупающий товар
     * @param marketProductId Id товара на рынке
     * @param quantity Кол-во покупаемого товара
     *
     * @throws NotFoundException
     * @throws NotEnoughMoneyException
     * @throws NotEnoughResourceException
     * @throws AlreadyExistsException
     */
    fun buyMarketProduct(user: DtoUser, marketProductId: UUID, quantity: Long): DtoMarketProductOperation

    /**
     * Изъятие товара с продажи
     *
     * @param marketProductId Id товара на рынке
     *
     * @throws NotFoundException
     */
    fun withdrawMarketProduct(marketProductId: UUID): DtoMarketProductOperation
}