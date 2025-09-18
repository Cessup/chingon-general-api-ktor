package com.cessup.data.repositories

import com.cessup.data.models.eatable.DrinkEntity
import com.cessup.data.models.eatable.MealEntity
import com.cessup.domain.models.eatable.Drink
import com.cessup.domain.models.eatable.Meal
import com.cessup.domain.repositories.EatableRepository
import com.google.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.bson.types.ObjectId
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.newId

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
class EatableRepositoryImpl @Inject constructor(database: CoroutineDatabase) : EatableRepository {
    /*Functions about Drink Collection*/

    /**
     * This function insert a new user in the database
     */
    private val drinkCollection = database.getCollection<DrinkEntity>("drink")

    /**
     * This function insert a new drink in the database
     *
     * @param drink the drinkEntity is the object with the information about it
     * @return a drink
     */
    override suspend fun insertDrink(drink: Drink): Boolean = withContext(Dispatchers.IO) {
        val drinkEntity = DrinkEntity(
            newId(),
            drink.temperature,
            drink.isAlcoholic,
            drink.millilitres,
            drink.category,
            drink.subcategory,
            ObjectId(drink.idEnergy),
            ObjectId(drink.idProduct),
        )
        try {
            drinkCollection.insertOne(drinkEntity)
            true
        } catch (_: Exception) {
            false
        }
    }

    /**
     * This function update a drink document in the collection
     *
     * @param drink the drinkEntity is the object with the information about it
     * @return a boolean that is the result about the process in database
     */
    override suspend fun updateDrink(drink: Drink): Boolean = withContext(Dispatchers.IO) {
        val drinkEntity = DrinkEntity(
            newId(),
            drink.temperature,
            drink.isAlcoholic,
            drink.millilitres,
            drink.category,
            drink.subcategory,
            ObjectId(drink.idEnergy),
            ObjectId(drink.idProduct),
        )

        val updateResult = drinkCollection.replaceOne(
            DrinkEntity::id eq drinkEntity.id,
            drinkEntity
        )

        updateResult.matchedCount > 0 && updateResult.modifiedCount > 0
    }

    /**
     * This function delete a drink in the database
     *
     * @param id the id is the identifier to find it
     * @return a boolean that is the result about the process in database
     */
    override suspend fun deleteDrink(id: ObjectId): Boolean = withContext(Dispatchers.IO) {
        val deleteResult = drinkCollection.deleteOneById(id)
        deleteResult.deletedCount == 1L
    }

    /**
     * This function gives all drinks
     *
     * @return a list of drinks
     */
    override suspend fun getDrinks(): List<Drink> = drinkCollection.find().toList().let {
        it.map { drinkEntity ->
            Drink(
                drinkEntity.id.toString(),
                drinkEntity.temperature,
                drinkEntity.isAlcoholic,
                drinkEntity.millilitres,
                drinkEntity.category,
                drinkEntity.subcategory,
                drinkEntity.idEnergy.toString(),
                drinkEntity.idProduct.toString(),
            )
        }
    }

    /*Functions about Meal Collection*/

    /**
     * This function insert a new user in the database
     */
    private val mealCollection = database.getCollection<MealEntity>("meal")

    /**
     * This function insert a new Meal in the database
     *
     * @param meal the Meal is the object with the information about it
     * @return a Meal
     */
    override suspend fun insertMeal(meal: Meal): Boolean = withContext(Dispatchers.IO) {
        val mealEntity = MealEntity(
            newId(),
            meal.temperature,
            meal.weight,
            meal.category,
            meal.subcategory,
            ObjectId(meal.idEnergy),
            ObjectId(meal.idProduct),
        )
        try {
            mealCollection.insertOne(mealEntity)
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
        val mealEntity = MealEntity(
            newId(),
            meal.temperature,
            meal.weight,
            meal.category,
            meal.subcategory,
            ObjectId(meal.idEnergy),
            ObjectId(meal.idProduct),
        )

        val updateResult = mealCollection.replaceOne(
            MealEntity::id eq mealEntity.id,
            mealEntity
        )

        updateResult.matchedCount > 0 && updateResult.modifiedCount > 0
    }

    /**
     * This function delete a Meal in the database
     *
     * @param id the id is the identifier to find it
     * @return a boolean that is the result about the process in database
     */
    override suspend fun deleteMeal(id: ObjectId): Boolean = withContext(Dispatchers.IO) {
        val deleteResult = mealCollection.deleteOneById(id)
        deleteResult.deletedCount == 1L
    }

    /**
     * This function gives all Meal
     *
     * @return a list of Meal
     */
    override suspend fun getMeals(): List<Meal> = mealCollection.find().toList().let {
        it.map { mealEntity ->
            Meal(
                mealEntity.id.toString(),
                mealEntity.temperature,
                mealEntity.weight,
                mealEntity.category,
                mealEntity.subcategory,
                mealEntity.idEnergy.toString(),
                mealEntity.idProduct.toString(),
            )
        }
    }
}