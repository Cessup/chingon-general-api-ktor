package com.cessup.domain.usecases.eatable.drink

import com.cessup.domain.repositories.EatableRepository
import com.google.inject.Inject

/**
 * Register of drink in the system.
 *
 * This class make to register of drink information.
 *
 * @constructor Receiver a [EatableRepository]
 * @author
 *     Cessup
 * @since 1.0
 */
class NewDrinkUseCase @Inject constructor(private val eatableRepository: EatableRepository) {
    /**
     * Returns new user.
     *
     * @param drink this object got information about any drink
     * @return A new [Boolean] from the previously entered credentials
     */
    suspend fun <T, R> execute(drink: T, insertDrink: suspend EatableRepository.(T) -> R): R {
        return eatableRepository.insertDrink(drink)
    }

}