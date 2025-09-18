package com.cessup.domain.usecases.eatable.meal

import com.cessup.domain.repositories.EatableRepository
import com.google.inject.Inject

/**
 * Delete a meal of the system.
 *
 * This class delete any meal of the system.
 *
 * @constructor Receiver a [EatableRepository] object because use it to origin data.
 * @author
 *     Cessup
 * @since 1.0
 */
class DeleteMealUseCase @Inject constructor(private val eatableRepository: EatableRepository) {

    /**
     * Delete a meal.
     *
     * @param id this object got information about any meal
     * @return A new [Boolean] from the previously entered credentials
     */
    suspend fun <T, R> execute(id: T, deleteMeal: suspend EatableRepository.(T) -> R): R {
        return eatableRepository.deleteMeal(id)
    }
}
