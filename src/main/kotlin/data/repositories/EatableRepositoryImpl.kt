package com.cessup.data.repositories

import com.cessup.data.entities.toDocument
import com.cessup.data.entities.toDrink
import com.cessup.data.entities.toMeal
import com.cessup.domain.models.eatable.Drink
import com.cessup.domain.models.eatable.Meal
import com.cessup.domain.repositories.EatableRepository
import com.mongodb.client.model.Filters.eq
import com.mongodb.client.model.Updates.set
import com.mongodb.reactivestreams.client.MongoDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.withContext
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
class EatableRepositoryImpl (val database: MongoDatabase) : EatableRepository {
    /*Functions about Drink Collection*/

    /**
     * This function insert a new user in the database
     */
    private val drinkCollection = database.getCollection("drink")

    /**
     * This function insert a new drink in the database
     *
     * @param drink the drink is the object with the information about it
     * @return a drink
     */
    override suspend fun insertDrink(drink: Drink): Boolean = withContext(Dispatchers.IO) {
        try {
            drinkCollection.insertOne(drink.toDocument()).awaitFirstOrNull()
            true
        } catch (_: Exception) {
            false
        }
    }

    /**
     * This function update a drink document in the collection
     *
     * @param drink the drink is the object with the information about it
     * @return a boolean that is the result about the process in database
     */
    override suspend fun updateDrink(drink: Drink): Boolean = withContext(Dispatchers.IO) {
        val updateResult = drinkCollection.updateOne(
            eq("_id", drink.id),
            set("details", drink.toDocument())
        ).awaitFirstOrNull()
        updateResult?.matchedCount == 1L
    }

    /**
     * This function delete a drink in the database
     *
     * @param id the id is the identifier to find it
     * @return a boolean that is the result about the process in database
     */
    override suspend fun deleteDrink(id: ObjectId): Boolean = withContext(Dispatchers.IO) {
        val deleteResult = drinkCollection.deleteOne(eq("_id", id)).awaitFirstOrNull()
        deleteResult?.deletedCount == 1L
    }

    /**
     * This function gives all drinks
     *
     * @return a list of drinks
     */
    override suspend fun getDrinks(): List<Drink> = drinkCollection.find().asFlow().map { it.toDrink() }.toList()

    /*Functions about Meal Collection*/

    /**
     * This function insert a new user in the database
     */
    private val mealCollection = database.getCollection("meal")

    /**
     * This function insert a new Meal in the database
     *
     * @param meal the Meal is the object with the information about it
     * @return a Meal
     */
    override suspend fun insertMeal(meal: Meal): Boolean = withContext(Dispatchers.IO) {
        try {
            mealCollection.insertOne(meal.toDocument()).awaitFirstOrNull()
            true
        } catch (_: Exception) {
            false
        }
    }

    /**
     * This function update a Meal document in the collection
     *
     * @param meal the Meal is the object with the information about it
     * @return a boolean that is the result about the process in database
     */
    override suspend fun updateMeal(meal: Meal): Boolean = withContext(Dispatchers.IO) {
        val updateResult = mealCollection.updateOne(
            eq("_id", meal.id),
            set("details", meal.toDocument())
        ).awaitFirstOrNull()
        updateResult?.matchedCount == 1L
    }

    /**
     * This function delete a Meal in the database
     *
     * @param id the id is the identifier to find it
     * @return a boolean that is the result about the process in database
     */
    override suspend fun deleteMeal(id: ObjectId): Boolean = withContext(Dispatchers.IO) {
        val deleteResult = mealCollection.deleteOne(eq("_id", id)).awaitFirstOrNull()
        deleteResult?.deletedCount == 1L
    }

    /**
     * This function gives all Meal
     *
     * @return a list of Meal
     */
    override suspend fun getMeals(): List<Meal> = mealCollection.find().asFlow().map { it.toMeal() }.toList()
}