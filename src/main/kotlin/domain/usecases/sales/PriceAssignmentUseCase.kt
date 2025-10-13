package com.cessup.domain.usecases.sales

import com.cessup.domain.models.sales.Price
import com.cessup.domain.repositories.SalesRepository

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
class PriceAssignmentUseCase(private val salesRepository: SalesRepository) {
    /**
     * Returns new user.
     *
     * @param price this object got information about things for sale
     * @return a [Boolean] for the result of function
     */
    suspend fun execute(price: Price): Boolean {
        return salesRepository.insertPrice(price)
    }

}