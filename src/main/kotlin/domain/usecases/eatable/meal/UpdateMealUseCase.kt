package com.cessup.domain.usecases.eatable.meal

import com.cessup.domain.models.eatable.drink.Beer
import com.cessup.domain.repositories.eatable.DrinkRepository
import com.cessup.domain.repositories.eatable.MealRepository
import com.google.inject.Inject

/**
 * Update of product by id of the product in the system.
 *
 * This class update of details product information such as name, description, etc..
 *
 * @constructor Receiver a [DrinkRepository]
 * @author
 *     Cessup
 * @since 1.0
 */
class UpdateMealUseCase @Inject constructor(private val mealRepository: MealRepository) {

    /**
     * Update a product.
     *
     * @param Beer this object got information about the product.
     * @return A [Boolean] that is the result about operation.
     */
    suspend fun <T, R> execute(obj: T, updateMeal: suspend MealRepository.(T) -> R): R {
        return mealRepository.updateMeal(obj)
    }
}