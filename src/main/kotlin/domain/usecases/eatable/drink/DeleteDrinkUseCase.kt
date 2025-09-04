package com.cessup.domain.usecases.eatable.drink

import com.cessup.domain.repositories.eatable.DrinkRepository
import com.google.inject.Inject

/**
 * Delete s beer of the system.
 *
 * This class delete any product of the system.
 *
 * @constructor Receiver a [DrinkRepository] object because use it to origin data.
 * @author
 *     Cessup
 * @since 1.0
 */
class DeleteDrinkUseCase @Inject constructor(private val drinkRepository: DrinkRepository) {
    /**
     * Delete a drink.
     *
     * @param id this object got information about any drink
     * @return A new [Boolean] from the previously entered credentials
     */
    suspend fun <T, R> execute(id: T, deleteDrink: suspend DrinkRepository.(T) -> R): R {
        return drinkRepository.deleteDrink(id)
    }
}
