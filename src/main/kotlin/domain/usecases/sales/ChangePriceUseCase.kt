package com.cessup.domain.usecases.sales

import com.cessup.domain.models.sales.Price
import com.cessup.domain.repositories.SalesRepository

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
class ChangePriceUseCase(private val salesRepository: SalesRepository) {
    /**
     * Update a price.
     *
     * @param updatePrice this object got information about the price.
     * @return A [Boolean] that is the result about operation.
     */
    suspend fun execute(updatePrice: Price): Boolean = salesRepository.updatePrice(updatePrice)
}