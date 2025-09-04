package com.cessup.domain.usecases.eatable.meal

import com.cessup.domain.repositories.eatable.MealRepository
import com.google.inject.Inject

/**
 * Register a new Meal in the system.
 *
 * This class make register of user information such as name, email, and age.
 *
 * @constructor Receiver a [MealRepository]
 * @author
 *     Cessup
 * @since 1.0
 */
class NewMealUseCase @Inject constructor(private val mealRepository: MealRepository) {
    /**
     * Returns new user.
     *
     * @param drink this object got information about any drink
     * @return A new [Boolean] from the previously entered credentials
     */
    suspend fun <T, R> execute(drink: T, insertMeal: suspend MealRepository.(T) -> R): R {
        return mealRepository.insertMeal(drink)
    }

}