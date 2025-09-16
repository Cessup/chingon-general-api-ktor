package com.cessup.domain.usecases.sales

import com.cessup.domain.models.sales.Price
import com.cessup.domain.repositories.SalesRepository
import com.google.inject.Inject

/**
 * Change of Price for anything.
 *
 * This class change price for all things to sale
 *
 * @constructor Receiver a [SalesRepository]
 * @author
 *     Cessup
 * @since 1.0
 */
class ChangePriceUseCase@Inject constructor(private val salesRepository: SalesRepository) {
    /**
     * Update a price.
     *
     * @param price this object got information about the price.
     * @return A [Boolean] that is the result about operation.
     */
    suspend fun execute(price: Price): Boolean = salesRepository.updatePrice(price)
}