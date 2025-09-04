package com.cessup.domain.usecases.eatable.meal

import com.cessup.domain.repositories.eatable.MealRepository
import com.google.inject.Inject

/**
 * Delete s beer of the system.
 *
 * This class delete any product of the system.
 *
 * @constructor Receiver a [MealRepository] object because use it to origin data.
 * @author
 *     Cessup
 * @since 1.0
 */
class DeleteMealUseCase @Inject constructor(private val mealRepository: MealRepository) {

    /**
     * Delete a meal.
     *
     * @param id this object got information about any meal
     * @return A new [Boolean] from the previously entered credentials
     */
    suspend fun <T, R> execute(id: T, deleteMeal: suspend MealRepository.(T) -> R): R {
        return mealRepository.deleteMeal(id)
    }
}
