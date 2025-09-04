package com.cessup.domain.usecases.eatable.drink

import com.cessup.domain.repositories.eatable.DrinkRepository
import com.google.inject.Inject

/**
 * Find of drink in the system.
 *
 * This class find a product.
 *
 * @constructor Receiver a [DrinkRepository] object because use it to origin data.
 * @author
 *     Cessup
 * @since 1.0
 */
class GetDrinksUseCase @Inject constructor(val drinkRepository: DrinkRepository) {

    /**
     * Returns the sum of two integers.
     *
     * @param any a kind of object
     *
     * @return The [Any] found in this search.
     */
    suspend fun <R> execute(getDrinks: suspend DrinkRepository.() -> R): R {
        return drinkRepository.getDrinks()
    }
}