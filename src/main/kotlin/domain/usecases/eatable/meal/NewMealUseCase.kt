package com.cessup.domain.usecases.eatable.meal

import com.cessup.domain.repositories.EatableRepository

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
class NewMealUseCase(val repository: EatableRepository) {

    /**
     * Returns new user.
     *
     * @param drink this object got information about any drink
     * @return A new [Boolean] from the previously entered credentials
     */
    suspend fun <T, R> execute(drink: T, insertMeal: suspend EatableRepository.(T) -> R): R {
        return repository.insertMeal(drink)
    }

}