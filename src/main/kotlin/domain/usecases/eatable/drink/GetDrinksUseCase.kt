package com.cessup.domain.usecases.eatable.drink

import com.cessup.domain.repositories.EatableRepository

/**
 * Find of drink in the system.
 *
 * This class find a drink.
 *
 * @constructor Receiver a [EatableRepository] object because use it to origin data.
 * @author
 *     Cessup
 * @since 1.0
 */
@Suppress("UNCHECKED_CAST")
class GetDrinksUseCase(val repository: EatableRepository) {

    /**
     * Returns the sum of two integers.
     *
     * @param any a kind of object
     *
     * @return The [Any] found in this search.
     */
    suspend fun <R> execute(getDrinks: suspend EatableRepository.() -> R): R {
        return repository.getDrinks() as R
    }
}