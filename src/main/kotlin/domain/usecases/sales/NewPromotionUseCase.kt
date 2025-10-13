package com.cessup.domain.usecases.sales

import com.cessup.domain.models.sales.Promotion
import com.cessup.domain.repositories.SalesRepository

/**
 * New of Promotion for some things.
 *
 * This class make promotions for sale
 *
 * @constructor Receiver a [SalesRepository]
 * @author
 *     Cessup
 * @since 1.0
 */
class NewPromotionUseCase (private val salesRepository: SalesRepository) {
    /**
     * Returns new promotion.
     *
     * @param promotion this object got information about promotion for sale
     * @return a [Boolean] for the result of function
     */
    suspend fun execute(promotion: Promotion): Boolean {
        return salesRepository.insertPromotion(promotion)
    }
}