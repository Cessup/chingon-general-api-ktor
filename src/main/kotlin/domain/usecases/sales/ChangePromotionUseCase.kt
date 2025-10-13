package com.cessup.domain.usecases.sales

import com.cessup.domain.models.sales.Promotion
import com.cessup.domain.repositories.SalesRepository

/**
 * Change of Promotion for anything.
 *
 * This class change price for all things to sale
 *
 * @constructor Receiver a [SalesRepository]
 * @author
 *     Cessup
 * @since 1.0
 */
class ChangePromotionUseCase(private val salesRepository: SalesRepository) {
    /**
     * Update a price.
     *
     * @param promotion this object got information about the price.
     * @return A [Boolean] that is the result about operation.
     */
    suspend fun execute(promotion: Promotion): Boolean = salesRepository.updatePromotion(promotion)
}