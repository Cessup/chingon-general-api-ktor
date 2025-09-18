package com.cessup.domain.repositories

import com.cessup.domain.models.eatable.Drink
import com.cessup.domain.models.eatable.Meal
import org.bson.types.ObjectId

/**
 * Eatable Repository have every data about the eatable.
 *
 * This class is a interface with all functions about eatable information
 * There are actions that the eatable can perform
 *
 * @author
 *     Cessup
 * @since 1.0
 */
interface EatableRepository {
    /*Functions about Drink Collection*/

    /**
     * This function insert a new drink in the database
     *
     * @param drink the drink is the object with the information about it
     * @return a drink
     */
    suspend fun insertDrink(drink: Drink): Boolean

    /**
     * This function update a drink document in the collection
     *
     * @param drink the drinkEntity is the object with the information about it
     * @return a boolean that is the result about the process in database
     */
    suspend fun updateDrink(drink: Drink): Boolean

    /**
     * This function delete a drink in the database
     *
     * @param id the id is the identifier to find it
     * @return a boolean that is the result about the process in database
     */
    suspend fun deleteDrink(id: ObjectId): Boolean

    /**
     * This function gives all drinks
     *
     * @return a list of drinks
     */
    suspend fun getDrinks(): List<Drink>
    /* MEAL STARTED HERE */

    /*Functions about Meal Collection*/

    /**
     * This function insert a new Meal in the database
     *
     * @param meal the Meal is the object with the information about it
     * @return a Meal
     */
    suspend fun insertMeal(meal: Meal): Boolean

    /**
     * This function update a Meal document in the collection
     *
     * @param meal the Meal is the object with the information about it
     * @return a boolean that is the result about the process in database
     */
    suspend fun updateMeal(meal: Meal): Boolean

    /**
     * This function delete a Meal in the database
     *
     * @param id the id is the identifier to find it
     * @return a boolean that is the result about the process in database
     */
    suspend fun deleteMeal(id: ObjectId): Boolean

    /**
     * This function gives all Meals
     *
     * @return a list of Meals
     */
    suspend fun getMeals(): List<Meal>
}