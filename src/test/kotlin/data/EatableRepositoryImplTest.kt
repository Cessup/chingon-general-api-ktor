package com.cessup.data

import com.cessup.data.repositories.EatableRepositoryImpl
import com.cessup.domain.models.eatable.Drink
import com.cessup.domain.models.eatable.Meal
import com.mongodb.reactivestreams.client.MongoClients
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.test.runTest
import org.bson.types.ObjectId
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EatableRepositoryImplTest {

    private lateinit var repository: EatableRepositoryImpl

    @BeforeAll
    fun setup() {
        val client = MongoClients.create("mongodb://localhost:27017")
        val database = client.getDatabase("eatable_db")
        runTest {
            database.getCollection("drink").drop().awaitFirstOrNull()
            database.getCollection("meal").drop().awaitFirstOrNull()
        }
        repository = EatableRepositoryImpl(database)
    }

    @AfterEach
    fun cleanCollections() = runTest {
        repository.getDrinks().forEach { repository.deleteDrink(it.id) }
        repository.getMeals().forEach { repository.deleteMeal(it.id) }
    }

    @Test
    fun `insertDrink should store a drink and getDrinks should return it`() = runTest {
        val drink = createTestDrink()
        val result = repository.insertDrink(drink)

        assertTrue(result)

        val drinks = repository.getDrinks()
        assertEquals(1, drinks.size)
        assertEquals("Wine", drinks.first().category)
    }

    @Test
    fun `updateDrink should update drink details`() = runTest {
        val drink = createTestDrink()
        repository.insertDrink(drink)

        val updated = drink.copy(millilitres = 500)
        val updateResult = repository.updateDrink(updated)

        assertTrue(updateResult)

        val fetched = repository.getDrinks().first()
        assertEquals(500, fetched.millilitres)
    }

    @Test
    fun `deleteDrink should remove the drink`() = runTest {
        val drink = createTestDrink()
        repository.insertDrink(drink)

        val deleteResult = repository.deleteDrink(drink.id)

        assertTrue(deleteResult)
        val drinks = repository.getDrinks()
        assertTrue(drinks.isEmpty())
    }

    @Test
    fun `insertMeal should store a meal and getMeals should return it`() = runTest {
        val meal = createTestMeal()
        val result = repository.insertMeal(meal)

        assertTrue(result)

        val meals = repository.getMeals()
        assertEquals(1, meals.size)
        assertEquals("Meat", meals.first().category)
    }

    @Test
    fun `updateMeal should update meal details`() = runTest {
        val meal = createTestMeal()
        repository.insertMeal(meal)

        val updated = meal.copy(weight = 750.50)
        val updateResult = repository.updateMeal(updated)

        assertTrue(updateResult)

        val fetched = repository.getMeals().first()
        assertEquals(750.50, fetched.weight)
    }

    @Test
    fun `deleteMeal should remove the meal`() = runTest {
        val meal = createTestMeal()
        repository.insertMeal(meal)

        val deleteResult = repository.deleteMeal(meal.id)

        assertTrue(deleteResult)
        val meals = repository.getMeals()
        assertTrue(meals.isEmpty())
    }
}

private fun createTestDrink(): Drink {
    return Drink(
        id = ObjectId(),
        temperature = 0.0,
        isAlcoholic = true,
        millilitres = 500,
        category = "Wine",
        subcategory = "White",
        idEnergy = ObjectId(),
        idProduct = ObjectId()
    )
}

private fun createTestMeal(): Meal {
    return Meal(
        id = ObjectId(),
        temperature = 0.0,
        weight = 750.50,
        category = "Meat",
        subcategory = "Beef",
        idEnergy = ObjectId(),
        idProduct = ObjectId()
    )
}