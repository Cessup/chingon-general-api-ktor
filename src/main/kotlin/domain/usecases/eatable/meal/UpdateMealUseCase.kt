package com.cessup.domain.usecases.eatable.meal

import com.cessup.domain.repositories.EatableRepository
import com.google.inject.Inject

/**
 * Update meal by id in the system.
 *
 * This class update meal information.
 *
 * @constructor Receiver a [EatableRepository]
 * @author
 *     Cessup
 * @since 1.0
 */
class UpdateMealUseCase @Inject constructor(private val eatableRepository: EatableRepository) {

    /**
     * Update a product.
     *
     * @param obj this object got information about the product.
     * @return A [Boolean] that is the result about operation.
     */
    suspend fun <T, R> execute(obj: T, updateMeal: suspend EatableRepository.(T) -> R): R {
        return eatableRepository.updateMeal(obj)
    }
}