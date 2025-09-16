package com.cessup.domain.usecases.sales

import com.cessup.domain.repositories.SalesRepository
import com.google.inject.Inject
import org.bson.types.ObjectId

/**
 * Delete Promotion for a thing.
 *
 * This class delete price for all a things to sale
 *
 * @constructor Receiver a [SalesRepository]
 * @author
 *     Cessup
 * @since 1.0
 */
class DeletePromotionUseCase@Inject constructor(private val salesRepository: SalesRepository) {
    /**
     * Delete a price.
     *
     * @param id this is the identifier about the promotion to delete
     * @return A [Boolean] that is the result about operation.
     */
    suspend fun execute(id: ObjectId): Boolean = salesRepository.deletePromotion(id)
}