package ru.yan.database.service.impl

import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.yan.database.exceptions.*
import ru.yan.database.model.moneyUUID
import ru.yan.database.model.nsi.dto.toDto
import ru.yan.database.model.nsi.types.MarketProductOperationType
import ru.yan.database.model.nsi.types.StorageResourceOperationType
import ru.yan.database.model.operation.MarketProductOperation
import ru.yan.database.model.operation.StorageResourceOperation
import ru.yan.database.model.operation.dto.DtoMarketProductOperation
import ru.yan.database.model.operation.dto.toDto
import ru.yan.database.model.smuta.Country
import ru.yan.database.model.smuta.MarketProduct
import ru.yan.database.model.smuta.StorageResource
import ru.yan.database.model.smuta.User
import ru.yan.database.model.smuta.dto.DtoUser
import ru.yan.database.model.smuta.dto.toDto
import ru.yan.database.repository.operation.MarketProductOperationRepository
import ru.yan.database.repository.operation.StorageResourceOperationRepository
import ru.yan.database.repository.smuta.CountryRepository
import ru.yan.database.repository.smuta.MarketProductRepository
import ru.yan.database.repository.smuta.StorageResourceRepository
import ru.yan.database.service.MarketService
import java.util.*

@Service
class MarketServiceImpl(
    private val marketProductRepository: MarketProductRepository,
    private val storageResourceRepository: StorageResourceRepository,
    private val countryRepository: CountryRepository,
    private val storageResourceOperationRepository: StorageResourceOperationRepository,
    private val marketProductOperationRepository: MarketProductOperationRepository
): MarketService {

    @Transactional
    override fun getStorageResource(storageResourceId: UUID) =
        storageResourceRepository.findByIdOrNull(storageResourceId)?.toDto()
            ?: notFound(StorageResource::class, storageResourceId)

    @Transactional
    override fun getMarketProduct(marketProductId: UUID) =
        marketProductRepository.findByIdOrNull(marketProductId)?.toDto()
            ?: notFound(MarketProduct::class, marketProductId)

    @Transactional
    override fun getMarketProductByUser(user: DtoUser, pageRequest: PageRequest) =
        marketProductRepository.findAllByUser(user.id, pageRequest).map { it.toDto() }

    @Transactional
    override fun getStorageResources(user: DtoUser, pageRequest: PageRequest) =
        storageResourceRepository.findByUserForSell(user.id, pageRequest).map { it.toDto() }

    @Transactional
    override fun getMarketProductsExcludeUser(user: DtoUser, pageRequest: PageRequest) =
        marketProductRepository.findAllSortedByPrice(user.id, pageRequest).map { it.toDto() }

    @Transactional
    override fun sellStorageResource(storageResourceId: UUID, quantity: Long, price: Double): DtoMarketProductOperation {
        storageResourceRepository.findByIdOrNull(storageResourceId)?.let { storageResource ->
            if (storageResource.quantity < quantity) {
                notEnoughResource(storageResource.resource.toDto(), quantity - storageResource.quantity)
            }

            val marketProducts = marketProductRepository.findAllByCountry(storageResource.country)
            if (marketProducts.size >= 3) {
                alreadyExistsWith(marketProducts::class, marketProducts::size, 3)
            }

            storageResourceRepository.findByCountryAndResourceId(storageResource.country, UUID.fromString(moneyUUID))?.let { money ->
                val tax = (quantity * price) * 0.1
                if (money.quantity >= tax) {
                    storageResourceOperationRepository.saveAll(
                        listOf(
                            StorageResourceOperation(
                                StorageResourceOperationType.TakenForSale,
                                -quantity.toDouble(),
                                storageResource.country,
                                storageResource.resource
                            ),
                            StorageResourceOperation(
                                StorageResourceOperationType.Tax,
                                -tax,
                                storageResource.country,
                                money.resource
                            )
                        )
                    )

                    return marketProductOperationRepository.save(
                        MarketProductOperation(
                            MarketProductOperationType.Add,
                            price,
                            quantity,
                            storageResource.resource,
                            storageResource.country
                        )
                    ).toDto()
                } else {
                    notEnoughMoney(price - money.quantity)
                }
            } ?: notEnoughMoney(price)
        } ?: notFound(StorageResource::class, storageResourceId)
    }

    @Transactional
    override fun buyMarketProduct(user: DtoUser, marketProductId: UUID, quantity: Long): DtoMarketProductOperation {
        countryRepository.findByUserId(user.id)?.let { country ->
            marketProductRepository.findByIdOrNull(marketProductId)?.let { marketProduct ->

                if (marketProduct.quantity < quantity) {
                    notEnoughResource(marketProduct.resource.toDto(), quantity - marketProduct.quantity)
                }

                val money = country.storageResources[UUID.fromString(moneyUUID)]
                with(money?.quantity ?: 0.0) {
                    if (this < (quantity * marketProduct.price)) {
                        notEnoughMoney(quantity - this)
                    } else {
                        val fullPrice = quantity * marketProduct.price
                        storageResourceOperationRepository.saveAll(
                            listOf(
                                StorageResourceOperation(
                                    StorageResourceOperationType.Purchased,
                                    quantity.toDouble(),
                                    country,
                                    marketProduct.resource
                                ),
                                StorageResourceOperation(
                                    StorageResourceOperationType.Purchased,
                                    -fullPrice,
                                    country,
                                    money!!.resource
                                ),
                                StorageResourceOperation(
                                    StorageResourceOperationType.Sold,
                                    fullPrice,
                                    marketProduct.country,
                                    money.resource
                                )
                            )
                        )

                        return marketProductOperationRepository.save(
                            MarketProductOperation(
                                if (marketProduct.quantity > quantity) {
                                    MarketProductOperationType.Purchased
                                } else {
                                    MarketProductOperationType.PurchasedAll
                                },
                                fullPrice,
                                quantity,
                                marketProduct.resource,
                                marketProduct.country
                            )
                        ).toDto()
                    }
                }

            } ?: notFound(MarketProduct::class, marketProductId)
        } ?: notFoundBy(Country::class, User::class, user.id)
    }

    @Transactional
    override fun withdrawMarketProduct(marketProductId: UUID): DtoMarketProductOperation {
        marketProductRepository.findByIdOrNull(marketProductId)?.let { marketProduct ->
            storageResourceOperationRepository.saveAll(
                listOf(
                    StorageResourceOperation(
                        StorageResourceOperationType.ReturnFromSale,
                        marketProduct.quantity.toDouble(),
                        marketProduct.country,
                        marketProduct.resource
                    )
                )
            )

            return marketProductOperationRepository.save(
                MarketProductOperation(
                    MarketProductOperationType.Remove,
                    marketProduct.price,
                    marketProduct.quantity,
                    marketProduct.resource,
                    marketProduct.country
                )
            ).toDto()

        } ?: notFound(MarketProduct::class, marketProductId)
    }
}