package com.cessup.domain.usecases.eatable.meal

import com.cessup.domain.repositories.EatableRepository
import com.google.inject.Inject

/**
 * Register a new Meal in the system.
 *
 * This class make to register of meal information.
 *
 * @constructor Receiver a [EatableRepository]
 * @author
 *     Cessup
 * @since 1.0
 */
class NewMealUseCase @Inject constructor(private val eatableRepository: EatableRepository) {
    /**
     * Returns new user.
     *
     * @param drink this object got information about any drink
     * @return A new [Boolean] from the previously entered credentials
     */
    suspend fun <T, R> execute(drink: T, insertMeal: suspend EatableRepository.(T) -> R): R {
        return eatableRepository.insertMeal(drink)
    }

}