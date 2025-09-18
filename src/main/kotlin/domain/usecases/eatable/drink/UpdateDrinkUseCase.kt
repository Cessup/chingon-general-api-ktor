package com.cessup.domain.usecases.eatable.drink

import com.cessup.domain.repositories.EatableRepository
import com.google.inject.Inject


/**
 * Update of drink by id in the system.
 *
 * This class update of details product information such as name, description, etc..
 *
 * @constructor Receiver a [EatableRepository]
 * @author
 *     Cessup
 * @since 1.0
 */
class UpdateDrinkUseCase @Inject constructor(private val eatableRepository: EatableRepository) {

    /**
     * Update a product.
     *
     * @param Beer this object got information about the product.
     * @return A [Boolean] that is the result about operation.
     */
    suspend fun <T, R> execute(obj: T, updateDrink: suspend EatableRepository.(T) -> R): R {
        return eatableRepository.updateDrink(obj)
    }
}