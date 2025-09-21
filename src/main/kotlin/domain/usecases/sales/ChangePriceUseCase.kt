package com.cessup.domain.usecases.sales

import com.cessup.domain.models.sales.Sale
import com.cessup.domain.repositories.SalesRepository
import com.google.inject.Inject

/**
 * Change of Sale for anything.
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
     * Update a sale.
     *
     * @param sale this object got information about the sale.
     * @return A [Boolean] that is the result about operation.
     */
    suspend fun execute(sale: Sale): Boolean = salesRepository.updatePrice(sale)
}