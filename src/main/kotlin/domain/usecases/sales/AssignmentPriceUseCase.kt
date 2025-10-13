package com.cessup.domain.usecases.sales

import com.cessup.domain.models.sales.Price
import com.cessup.domain.repositories.SalesRepository
import org.bson.types.ObjectId

/**
 * Assignment of Price for anything.
 *
 * This class make price for all things to sale
 *
 * @constructor Receiver a [SalesRepository]
 * @author
 *     Cessup
 * @since 1.0
 */
class AssignmentPriceUseCase (private val repository: SalesRepository) {
    /**
     * Returns new user.
     *
     * @param price this object got information about things for sale
     * @return a [Boolean] for the result of function
     */
    suspend fun execute(price: Price, idMerch: ObjectId): Boolean {
        repository.getPriceByIdMerchantAndIdProduct(idMerch,price.item)?.let {
            throw IllegalArgumentException("Product already in use")
        }

        return repository.insertPrice(price)
    }

}