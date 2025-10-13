package com.cessup.domain.usecases.eatable.meal

import com.cessup.domain.repositories.EatableRepository

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
@Suppress("UNCHECKED_CAST")
class GetMealsUseCase(val repository: EatableRepository) {

    /**
     * Returns the sum of two integers.
     *
     * @param any a kind of object
     *
     * @return The [Any] found in this search.
     */
    suspend fun <R> execute(getMeals: suspend EatableRepository.() -> R): R {
        return repository.getMeals() as R
    }
}