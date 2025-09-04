package com.cessup.domain.usecases.eatable.drink

import com.cessup.domain.repositories.eatable.DrinkRepository
import com.google.inject.Inject

/**
 * Register of beers in the system.
 *
 * This class make register of user information such as name, email, and age.
 *
 * @constructor Receiver a [DrinkRepository]
 * @author
 *     Cessup
 * @since 1.0
 */
class NewDrinkUseCase @Inject constructor(private val drinkRepository: DrinkRepository) {
    /**
     * Returns new user.
     *
     * @param drink this object got information about any drink
     * @return A new [Boolean] from the previously entered credentials
     */
    suspend fun <T, R> execute(drink: T, insertDrink: suspend DrinkRepository.(T) -> R): R {
        return drinkRepository.insertDrink(drink)
    }

}