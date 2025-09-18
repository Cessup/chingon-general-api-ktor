package com.cessup.domain.usecases.eatable.meal

import com.cessup.domain.repositories.EatableRepository
import com.google.inject.Inject

/**
 * Find of meal in the system .
 *
 * This class find a meal.
 *
 * @constructor Receiver a [EatableRepository] object because use it to origin data.
 * @author
 *     Cessup
 * @since 1.0
 */
class GetMealsUseCase @Inject constructor(val eatableRepository: EatableRepository) {

    /**
     * Returns the sum of two integers.
     *
     * @param any a kind of object
     *
     * @return The [Any] found in this search.
     */
    suspend fun <R> execute(getMeals: suspend EatableRepository.() -> R): R {
        return eatableRepository.getMeals() as R
    }
}