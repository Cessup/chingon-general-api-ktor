package com.cessup.domain.usecases.sales

import com.cessup.domain.models.sales.Merchant
import com.cessup.domain.repositories.SalesRepository

/**
 * Update of Merchant for anything.
 *
 * This class update merchant information
 *
 * @constructor Receiver a [SalesRepository]
 * @author
 *     Cessup
 * @since 1.0
 */
class UpdateMerchantUseCase(private val salesRepository: SalesRepository) {
    /**
     * Update a price.
     *
     * @param updateMerchant this object got information about the price.
     * @return A [Boolean] that is the result about operation.
     */
    suspend fun execute(updateMerchant: Merchant): Boolean = salesRepository.updateMerchant(updateMerchant)
}