package com.cessup.domain.usecases.eatable.drink

import com.cessup.domain.models.eatable.drink.Beer
import com.cessup.domain.repositories.eatable.DrinkRepository
import com.google.inject.Inject


/**
 * Update of product by id of the product in the system.
 *
 * This class update of details product information such as name, description, etc..
 *
 * @constructor Receiver a [DrinkRepository]
 * @author
 *     Cessup
 * @since 1.0
 */
class UpdateDrinkUseCase @Inject constructor(private val drinkRepository: DrinkRepository) {

    /**
     * Update a product.
     *
     * @param Beer this object got information about the product.
     * @return A [Boolean] that is the result about operation.
     */
    suspend fun <T, R> execute(obj: T, updateDrink: suspend DrinkRepository.(T) -> R): R {
        return drinkRepository.updateDrink(obj)
    }
}