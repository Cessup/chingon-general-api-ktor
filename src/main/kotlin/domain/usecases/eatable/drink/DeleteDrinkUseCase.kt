package com.cessup.domain.usecases.eatable.drink

import com.cessup.domain.repositories.EatableRepository
import com.google.inject.Inject

/**
 * Delete a drink of the system.
 *
 * This class delete any drink of the system.
 *
 * @constructor Receiver a [EatableRepository] object because use it to origin data.
 * @author
 *     Cessup
 * @since 1.0
 */
class DeleteDrinkUseCase @Inject constructor(private val eatableRepository: EatableRepository) {
    /**
     * Delete a drink.
     *
     * @param id this object got information about any drink
     * @return A new [Boolean] from the previously entered credentials
     */
    suspend fun <T, R> execute(id: T, deleteDrink: suspend EatableRepository.(T) -> R): R {
        return eatableRepository.deleteDrink(id)
    }
}
