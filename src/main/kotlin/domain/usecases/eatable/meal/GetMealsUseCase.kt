package com.cessup.domain.usecases.eatable.meal

import com.cessup.domain.repositories.eatable.MealRepository
import com.google.inject.Inject

/**
 * Find of meal in the system .
 *
 * This class find a product.
 *
 * @constructor Receiver a [MealRepository] object because use it to origin data.
 * @author
 *     Cessup
 * @since 1.0
 */
class GetMealsUseCase @Inject constructor(val mealRepository: MealRepository) {

    /**
     * Returns the sum of two integers.
     *
     * @param any a kind of object
     *
     * @return The [Any] found in this search.
     */
    suspend fun <R> execute(getMeals: suspend MealRepository.() -> R): R {
        return mealRepository.getMeals()
    }
}