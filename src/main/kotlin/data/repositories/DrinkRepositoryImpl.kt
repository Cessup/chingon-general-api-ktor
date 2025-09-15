package com.cessup.data.repositories

import com.cessup.data.models.eatable.BrandEntity
import com.cessup.data.models.eatable.EnergyEntity
import com.cessup.data.models.eatable.drink.BeerEntity
import com.cessup.data.models.eatable.drink.ChocolateEntity
import com.cessup.data.models.eatable.drink.CocktailEntity
import com.cessup.data.models.eatable.drink.CoffeeEntity
import com.cessup.data.models.eatable.drink.DrinkEntity
import com.cessup.data.models.eatable.drink.JuiceEntity
import com.cessup.data.models.eatable.drink.LiqueurEntity
import com.cessup.data.models.eatable.drink.MocktailEntity
import com.cessup.data.models.eatable.drink.SmoothieEntity
import com.cessup.data.models.eatable.drink.SodaEntity
import com.cessup.data.models.eatable.drink.SpiritEntity
import com.cessup.data.models.eatable.drink.TeaEntity
import com.cessup.data.models.eatable.drink.TequilaEntity
import com.cessup.data.models.eatable.drink.WaterEntity
import com.cessup.data.models.eatable.drink.WineEntity
import com.cessup.domain.models.eatable.Brand
import com.cessup.domain.models.eatable.Energy
import com.cessup.domain.models.eatable.drink.Beer
import com.cessup.domain.models.eatable.drink.Chocolate
import com.cessup.domain.models.eatable.drink.Cocktail
import com.cessup.domain.models.eatable.drink.Coffee
import com.cessup.domain.models.eatable.drink.Drink
import com.cessup.domain.models.eatable.drink.Juice
import com.cessup.domain.models.eatable.drink.Liqueur
import com.cessup.domain.models.eatable.drink.Mocktail
import com.cessup.domain.models.eatable.drink.Smoothie
import com.cessup.domain.models.eatable.drink.Soda
import com.cessup.domain.models.eatable.drink.Spirit
import com.cessup.domain.models.eatable.drink.Tea
import com.cessup.domain.models.eatable.drink.Tequila
import com.cessup.domain.models.eatable.drink.Water
import com.cessup.domain.models.eatable.drink.Wine
import com.cessup.domain.repositories.eatable.DrinkRepository
import com.google.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.bson.types.ObjectId
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.id.toId
import org.litote.kmongo.newId

/**
 * Eatable Repository have every data about the drinks and meals.
 *
 * This class is an interface with all functions about food information
 * There are actions that user can perform
 *
 * @author
 *     Cessup
 * @since 1.0
 */
class DrinkRepositoryImpl@Inject constructor(database: CoroutineDatabase) : DrinkRepository {
    /*Functions about Beer Collection*/

    /**
     * This function insert a new user in the database
     */
    private val beerCollection = database.getCollection<BeerEntity>("beer")

    /**
     * This function insert a new beer in the database
     *
     * @param beer the beerEntity is the object with the information about it
     * @return a beer
     */
    override suspend fun insertBeer(beer: Beer): Boolean = withContext(Dispatchers.IO) {
        val beerEntity = BeerEntity(
            newId(),
            beer.description,
            beer.imgURL,
            BrandEntity(
                newId(),
                beer.brand.name,
                beer.brand.description,
                beer.brand.img
            ),
            DrinkEntity(
                newId(),
                beer.drink.name,
                beer.drink.temperature,
                beer.drink.isAlcoholic,
                beer.drink.millilitres,
                EnergyEntity(
                    newId(),
                    beer.drink.energy.energyContent,
                    beer.drink.energy.perServing,
                    beer.drink.energy.protein,
                    beer.drink.energy.totalFat,
                    beer.drink.energy.saturatedFat,
                    beer.drink.energy.transFat,
                    beer.drink.energy.carbohydrates,
                    beer.drink.energy.sugars,
                    beer.drink.energy.addedSugars,
                    beer.drink.energy.dietaryFiber,
                    beer.drink.energy.sodium,
                    beer.drink.energy.ingredients,
                )
            )
        )
        try {
            beerCollection.insertOne(beerEntity)
            true
        } catch (_: Exception) {
            false
        }
    }

    /**
     * This function update a beer document in the collection
     *
     * @param beer the beerEntity is the object with the information about it
     * @return a boolean that is the result about the process in database
     */
    override suspend fun updateBeer(beer: Beer): Boolean = withContext(Dispatchers.IO) {
        val beerEntity =
            BeerEntity(
                ObjectId(beer.id).toId(),
                beer.description,
                beer.imgURL,
                BrandEntity(
                    newId(),
                    beer.brand.name,
                    beer.brand.description,
                    beer.brand.img
                ),
                DrinkEntity(
                    ObjectId(beer.drink.id).toId(),
                    beer.drink.name,
                    beer.drink.temperature,
                    beer.drink.isAlcoholic,
                    beer.drink.millilitres,
                    EnergyEntity(
                        ObjectId(beer.drink.energy.id).toId(),
                        beer.drink.energy.energyContent,
                        beer.drink.energy.perServing,
                        beer.drink.energy.protein,
                        beer.drink.energy.totalFat,
                        beer.drink.energy.saturatedFat,
                        beer.drink.energy.transFat,
                        beer.drink.energy.carbohydrates,
                        beer.drink.energy.sugars,
                        beer.drink.energy.addedSugars,
                        beer.drink.energy.dietaryFiber,
                        beer.drink.energy.sodium,
                        beer.drink.energy.ingredients,
                    )
                    )
            )

        val updateResult = beerCollection.replaceOne(
            BeerEntity::id eq beerEntity.id,
            beerEntity
        )

        updateResult.matchedCount > 0 && updateResult.modifiedCount > 0
    }

    /**
     * This function delete a beer in the database
     *
     * @param id the id is the identifier to find it
     * @return a boolean that is the result about the process in database
     */
    override suspend fun deleteBeer(id: ObjectId): Boolean = withContext(Dispatchers.IO) {
        val deleteResult = beerCollection.deleteOneById(id)
        deleteResult.deletedCount == 1L
    }

    /**
     * This function gives all beers
     *
     * @return a list of beers
     */
    override suspend fun getBeers(): List<Beer> = beerCollection.find().toList().let {
        it.map { beerEntity ->
            Beer(
                beerEntity.id.toString(),
                beerEntity.description,
                beerEntity.imgURL,
                Brand(
                    beerEntity.brandEntity.id.toString(),
                    beerEntity.brandEntity.name,
                    beerEntity.brandEntity.description,
                    beerEntity.brandEntity.img
                ),
                Drink(
                    beerEntity.drinkEntity.id.toString(),
                    beerEntity.drinkEntity.name,
                    beerEntity.drinkEntity.temperature,
                    beerEntity.drinkEntity.isAlcoholic,
                    beerEntity.drinkEntity.millilitres,
                    Energy(
                        beerEntity.drinkEntity.energyEntity.id.toString(),
                        beerEntity.drinkEntity.energyEntity.energyContent,
                        beerEntity.drinkEntity.energyEntity.perServing,
                        beerEntity.drinkEntity.energyEntity.protein,
                        beerEntity.drinkEntity.energyEntity.totalFat,
                        beerEntity.drinkEntity.energyEntity.saturatedFat,
                        beerEntity.drinkEntity.energyEntity.transFat,
                        beerEntity.drinkEntity.energyEntity.carbohydrates,
                        beerEntity.drinkEntity.energyEntity.sugars,
                        beerEntity.drinkEntity.energyEntity.addedSugars,
                        beerEntity.drinkEntity.energyEntity.dietaryFiber,
                        beerEntity.drinkEntity.energyEntity.sodium,
                        beerEntity.drinkEntity.energyEntity.ingredients,
                    ),
                ),

            )
        }
    }


    /*Functions about Chocolate Collection*/

    /**
     * This function insert a new user in the database
     */
    private val chocolateCollection = database.getCollection<ChocolateEntity>("chocolate")

    /**
     * This function insert a new chocolate in the database
     *
     * @param chocolate the chocolateEntity is the object with the information about it
     * @return a chocolate
     */
    override suspend fun insertChocolate(chocolate: Chocolate): Boolean = withContext(Dispatchers.IO) {
        val chocolateEntity = ChocolateEntity(
            newId(),
            chocolate.description,
            chocolate.imgURL,
            BrandEntity(
                newId(),
                chocolate.brand.name,
                chocolate.brand.description,
                chocolate.brand.img
            ),
            DrinkEntity(
                newId(),
                chocolate.drink.name,
                chocolate.drink.temperature,
                chocolate.drink.isAlcoholic,
                chocolate.drink.millilitres,
                EnergyEntity(
                    newId(),
                    chocolate.drink.energy.energyContent,
                    chocolate.drink.energy.perServing,
                    chocolate.drink.energy.protein,
                    chocolate.drink.energy.totalFat,
                    chocolate.drink.energy.saturatedFat,
                    chocolate.drink.energy.transFat,
                    chocolate.drink.energy.carbohydrates,
                    chocolate.drink.energy.sugars,
                    chocolate.drink.energy.addedSugars,
                    chocolate.drink.energy.dietaryFiber,
                    chocolate.drink.energy.sodium,
                    chocolate.drink.energy.ingredients,
                )
            )
        )
        try {
            chocolateCollection.insertOne(chocolateEntity)
            true
        } catch (_: Exception) {
            false
        }
    }

    /**
     * This function update a chocolate document in the collection
     *
     * @param chocolate the chocolateEntity is the object with the information about it
     * @return a boolean that is the result about the process in database
     */
    override suspend fun updateChocolate(chocolate: Chocolate): Boolean = withContext(Dispatchers.IO) {
        val chocolateEntity =
            ChocolateEntity(
                ObjectId(chocolate.id).toId(),
                chocolate.description,
                chocolate.imgURL,
                BrandEntity(
                    newId(),
                    chocolate.brand.name,
                    chocolate.brand.description,
                    chocolate.brand.img
                ),
                DrinkEntity(
                    ObjectId(chocolate.drink.id).toId(),
                    chocolate.drink.name,
                    chocolate.drink.temperature,
                    chocolate.drink.isAlcoholic,
                    chocolate.drink.millilitres,
                    EnergyEntity(
                        ObjectId(chocolate.drink.energy.id).toId(),
                        chocolate.drink.energy.energyContent,
                        chocolate.drink.energy.perServing,
                        chocolate.drink.energy.protein,
                        chocolate.drink.energy.totalFat,
                        chocolate.drink.energy.saturatedFat,
                        chocolate.drink.energy.transFat,
                        chocolate.drink.energy.carbohydrates,
                        chocolate.drink.energy.sugars,
                        chocolate.drink.energy.addedSugars,
                        chocolate.drink.energy.dietaryFiber,
                        chocolate.drink.energy.sodium,
                        chocolate.drink.energy.ingredients,
                    )
                )
            )

        val updateResult = chocolateCollection.replaceOne(
            ChocolateEntity::id eq chocolateEntity.id,
            chocolateEntity
        )

        updateResult.matchedCount > 0 && updateResult.modifiedCount > 0
    }

    /**
     * This function delete a chocolate in the database
     *
     * @param id the id is the identifier to find it
     * @return a boolean that is the result about the process in database
     */
    override suspend fun deleteChocolate(id: ObjectId): Boolean = withContext(Dispatchers.IO) {
        val deleteResult = chocolateCollection.deleteOneById(id)
        deleteResult.deletedCount == 1L
    }

    /**
     * This function gives all chocolates
     *
     * @return a list of chocolates
     */
    override suspend fun getChocolates(): List<Chocolate> = chocolateCollection.find().toList().let {
        it.map { chocolateEntity ->
            Chocolate(
                chocolateEntity.id.toString(),
                chocolateEntity.description,
                chocolateEntity.imgURL,
                Brand(
                    chocolateEntity.brandEntity.id.toString(),
                    chocolateEntity.brandEntity.name,
                    chocolateEntity.brandEntity.description,
                    chocolateEntity.brandEntity.img
                ),
                Drink(
                    chocolateEntity.drinkEntity.id.toString(),
                    chocolateEntity.drinkEntity.name,
                    chocolateEntity.drinkEntity.temperature,
                    chocolateEntity.drinkEntity.isAlcoholic,
                    chocolateEntity.drinkEntity.millilitres,
                    Energy(
                        chocolateEntity.drinkEntity.energyEntity.id.toString(),
                        chocolateEntity.drinkEntity.energyEntity.energyContent,
                        chocolateEntity.drinkEntity.energyEntity.perServing,
                        chocolateEntity.drinkEntity.energyEntity.protein,
                        chocolateEntity.drinkEntity.energyEntity.totalFat,
                        chocolateEntity.drinkEntity.energyEntity.saturatedFat,
                        chocolateEntity.drinkEntity.energyEntity.transFat,
                        chocolateEntity.drinkEntity.energyEntity.carbohydrates,
                        chocolateEntity.drinkEntity.energyEntity.sugars,
                        chocolateEntity.drinkEntity.energyEntity.addedSugars,
                        chocolateEntity.drinkEntity.energyEntity.dietaryFiber,
                        chocolateEntity.drinkEntity.energyEntity.sodium,
                        chocolateEntity.drinkEntity.energyEntity.ingredients,
                    ),
                ),

                )
        }
    }


    /*Functions about Cocktail Collection*/

    /**
     * This function insert a new user in the database
     */
    private val cocktailCollection = database.getCollection<CocktailEntity>("cocktail")

    /**
     * This function insert a new cocktail in the database
     *
     * @param cocktail the cocktailEntity is the object with the information about it
     * @return a cocktail
     */
    override suspend fun insertCocktail(cocktail: Cocktail): Boolean = withContext(Dispatchers.IO) {
        val cocktailEntity = CocktailEntity(
            newId(),
            cocktail.description,
            cocktail.imgURL,
            BrandEntity(
                newId(),
                cocktail.brand.name,
                cocktail.brand.description,
                cocktail.brand.img
            ),
            DrinkEntity(
                newId(),
                cocktail.drink.name,
                cocktail.drink.temperature,
                cocktail.drink.isAlcoholic,
                cocktail.drink.millilitres,
                EnergyEntity(
                    newId(),
                    cocktail.drink.energy.energyContent,
                    cocktail.drink.energy.perServing,
                    cocktail.drink.energy.protein,
                    cocktail.drink.energy.totalFat,
                    cocktail.drink.energy.saturatedFat,
                    cocktail.drink.energy.transFat,
                    cocktail.drink.energy.carbohydrates,
                    cocktail.drink.energy.sugars,
                    cocktail.drink.energy.addedSugars,
                    cocktail.drink.energy.dietaryFiber,
                    cocktail.drink.energy.sodium,
                    cocktail.drink.energy.ingredients,
                )
            )
        )
        try {
            cocktailCollection.insertOne(cocktailEntity)
            true
        } catch (_: Exception) {
            false
        }
    }

    /**
     * This function update a cocktail document in the collection
     *
     * @param cocktail the cocktailEntity is the object with the information about it
     * @return a boolean that is the result about the process in database
     */
    override suspend fun updateCocktail(cocktail: Cocktail): Boolean = withContext(Dispatchers.IO) {
        val cocktailEntity =
            CocktailEntity(
                ObjectId(cocktail.id).toId(),
                cocktail.description,
                cocktail.imgURL,
                BrandEntity(
                    newId(),
                    cocktail.brand.name,
                    cocktail.brand.description,
                    cocktail.brand.img
                ),
                DrinkEntity(
                    ObjectId(cocktail.drink.id).toId(),
                    cocktail.drink.name,
                    cocktail.drink.temperature,
                    cocktail.drink.isAlcoholic,
                    cocktail.drink.millilitres,
                    EnergyEntity(
                        ObjectId(cocktail.drink.energy.id).toId(),
                        cocktail.drink.energy.energyContent,
                        cocktail.drink.energy.perServing,
                        cocktail.drink.energy.protein,
                        cocktail.drink.energy.totalFat,
                        cocktail.drink.energy.saturatedFat,
                        cocktail.drink.energy.transFat,
                        cocktail.drink.energy.carbohydrates,
                        cocktail.drink.energy.sugars,
                        cocktail.drink.energy.addedSugars,
                        cocktail.drink.energy.dietaryFiber,
                        cocktail.drink.energy.sodium,
                        cocktail.drink.energy.ingredients,
                    )
                )
            )

        val updateResult = cocktailCollection.replaceOne(
            CocktailEntity::id eq cocktailEntity.id,
            cocktailEntity
        )

        updateResult.matchedCount > 0 && updateResult.modifiedCount > 0
    }

    /**
     * This function delete a cocktail in the database
     *
     * @param id the id is the identifier to find it
     * @return a boolean that is the result about the process in database
     */
    override suspend fun deleteCocktail(id: ObjectId): Boolean = withContext(Dispatchers.IO) {
        val deleteResult = cocktailCollection.deleteOneById(id)
        deleteResult.deletedCount == 1L
    }

    /**
     * This function gives all cocktails
     *
     * @return a list of cocktails
     */
    override suspend fun getCocktails(): List<Cocktail> = cocktailCollection.find().toList().let {
        it.map { cocktailEntity ->
            Cocktail(
                cocktailEntity.id.toString(),
                cocktailEntity.description,
                cocktailEntity.imgURL,
                Brand(
                    cocktailEntity.brandEntity.id.toString(),
                    cocktailEntity.brandEntity.name,
                    cocktailEntity.brandEntity.description,
                    cocktailEntity.brandEntity.img
                ),
                Drink(
                    cocktailEntity.drinkEntity.id.toString(),
                    cocktailEntity.drinkEntity.name,
                    cocktailEntity.drinkEntity.temperature,
                    cocktailEntity.drinkEntity.isAlcoholic,
                    cocktailEntity.drinkEntity.millilitres,
                    Energy(
                        cocktailEntity.drinkEntity.energyEntity.id.toString(),
                        cocktailEntity.drinkEntity.energyEntity.energyContent,
                        cocktailEntity.drinkEntity.energyEntity.perServing,
                        cocktailEntity.drinkEntity.energyEntity.protein,
                        cocktailEntity.drinkEntity.energyEntity.totalFat,
                        cocktailEntity.drinkEntity.energyEntity.saturatedFat,
                        cocktailEntity.drinkEntity.energyEntity.transFat,
                        cocktailEntity.drinkEntity.energyEntity.carbohydrates,
                        cocktailEntity.drinkEntity.energyEntity.sugars,
                        cocktailEntity.drinkEntity.energyEntity.addedSugars,
                        cocktailEntity.drinkEntity.energyEntity.dietaryFiber,
                        cocktailEntity.drinkEntity.energyEntity.sodium,
                        cocktailEntity.drinkEntity.energyEntity.ingredients,
                    ),
                ),

                )
        }
    }


    /*Functions about Coffee Collection*/

    /**
     * This function insert a new user in the database
     */
    private val coffeeCollection = database.getCollection<CoffeeEntity>("coffee")

    /**
     * This function insert a new coffee in the database
     *
     * @param coffee the coffeeEntity is the object with the information about it
     * @return a coffee
     */
    override suspend fun insertCoffee(coffee: Coffee): Boolean = withContext(Dispatchers.IO) {
        val coffeeEntity = CoffeeEntity(
            newId(),
            coffee.description,
            coffee.imgURL,
            BrandEntity(
                newId(),
                coffee.brand.name,
                coffee.brand.description,
                coffee.brand.img
            ),
            DrinkEntity(
                newId(),
                coffee.drink.name,
                coffee.drink.temperature,
                coffee.drink.isAlcoholic,
                coffee.drink.millilitres,
                EnergyEntity(
                    newId(),
                    coffee.drink.energy.energyContent,
                    coffee.drink.energy.perServing,
                    coffee.drink.energy.protein,
                    coffee.drink.energy.totalFat,
                    coffee.drink.energy.saturatedFat,
                    coffee.drink.energy.transFat,
                    coffee.drink.energy.carbohydrates,
                    coffee.drink.energy.sugars,
                    coffee.drink.energy.addedSugars,
                    coffee.drink.energy.dietaryFiber,
                    coffee.drink.energy.sodium,
                    coffee.drink.energy.ingredients,
                )
            )
        )
        try {
            coffeeCollection.insertOne(coffeeEntity)
            true
        } catch (_: Exception) {
            false
        }
    }

    /**
     * This function update a coffee document in the collection
     *
     * @param coffee the coffeeEntity is the object with the information about it
     * @return a boolean that is the result about the process in database
     */
    override suspend fun updateCoffee(coffee: Coffee): Boolean = withContext(Dispatchers.IO) {
        val coffeeEntity =
            CoffeeEntity(
                ObjectId(coffee.id).toId(),
                coffee.description,
                coffee.imgURL,
                BrandEntity(
                    newId(),
                    coffee.brand.name,
                    coffee.brand.description,
                    coffee.brand.img
                ),
                DrinkEntity(
                    ObjectId(coffee.drink.id).toId(),
                    coffee.drink.name,
                    coffee.drink.temperature,
                    coffee.drink.isAlcoholic,
                    coffee.drink.millilitres,
                    EnergyEntity(
                        ObjectId(coffee.drink.energy.id).toId(),
                        coffee.drink.energy.energyContent,
                        coffee.drink.energy.perServing,
                        coffee.drink.energy.protein,
                        coffee.drink.energy.totalFat,
                        coffee.drink.energy.saturatedFat,
                        coffee.drink.energy.transFat,
                        coffee.drink.energy.carbohydrates,
                        coffee.drink.energy.sugars,
                        coffee.drink.energy.addedSugars,
                        coffee.drink.energy.dietaryFiber,
                        coffee.drink.energy.sodium,
                        coffee.drink.energy.ingredients,
                    )
                )
            )

        val updateResult = coffeeCollection.replaceOne(
            CoffeeEntity::id eq coffeeEntity.id,
            coffeeEntity
        )

        updateResult.matchedCount > 0 && updateResult.modifiedCount > 0
    }

    /**
     * This function delete a coffee in the database
     *
     * @param id the id is the identifier to find it
     * @return a boolean that is the result about the process in database
     */
    override suspend fun deleteCoffee(id: ObjectId): Boolean = withContext(Dispatchers.IO) {
        val deleteResult = coffeeCollection.deleteOneById(id)
        deleteResult.deletedCount == 1L
    }

    /**
     * This function gives all coffees
     *
     * @return a list of coffees
     */
    override suspend fun getCoffees(): List<Coffee> = coffeeCollection.find().toList().let {
        it.map { coffeeEntity ->
            Coffee(
                coffeeEntity.id.toString(),
                coffeeEntity.description,
                coffeeEntity.imgURL,
                Brand(
                    coffeeEntity.brandEntity.id.toString(),
                    coffeeEntity.brandEntity.name,
                    coffeeEntity.brandEntity.description,
                    coffeeEntity.brandEntity.img
                ),
                Drink(
                    coffeeEntity.drinkEntity.id.toString(),
                    coffeeEntity.drinkEntity.name,
                    coffeeEntity.drinkEntity.temperature,
                    coffeeEntity.drinkEntity.isAlcoholic,
                    coffeeEntity.drinkEntity.millilitres,
                    Energy(
                        coffeeEntity.drinkEntity.energyEntity.id.toString(),
                        coffeeEntity.drinkEntity.energyEntity.energyContent,
                        coffeeEntity.drinkEntity.energyEntity.perServing,
                        coffeeEntity.drinkEntity.energyEntity.protein,
                        coffeeEntity.drinkEntity.energyEntity.totalFat,
                        coffeeEntity.drinkEntity.energyEntity.saturatedFat,
                        coffeeEntity.drinkEntity.energyEntity.transFat,
                        coffeeEntity.drinkEntity.energyEntity.carbohydrates,
                        coffeeEntity.drinkEntity.energyEntity.sugars,
                        coffeeEntity.drinkEntity.energyEntity.addedSugars,
                        coffeeEntity.drinkEntity.energyEntity.dietaryFiber,
                        coffeeEntity.drinkEntity.energyEntity.sodium,
                        coffeeEntity.drinkEntity.energyEntity.ingredients,
                    ),
                ),

                )
        }
    }


    /*Functions about Juice Collection*/

    /**
     * This function insert a new user in the database
     */
    private val juiceCollection = database.getCollection<JuiceEntity>("juice")

    /**
     * This function insert a new juice in the database
     *
     * @param juice the juiceEntity is the object with the information about it
     * @return a juice
     */
    override suspend fun insertJuice(juice: Juice): Boolean = withContext(Dispatchers.IO) {
        val juiceEntity = JuiceEntity(
            newId(),
            juice.description,
            juice.imgURL,
            BrandEntity(
                newId(),
                juice.brand.name,
                juice.brand.description,
                juice.brand.img
            ),
            DrinkEntity(
                newId(),
                juice.drink.name,
                juice.drink.temperature,
                juice.drink.isAlcoholic,
                juice.drink.millilitres,
                EnergyEntity(
                    newId(),
                    juice.drink.energy.energyContent,
                    juice.drink.energy.perServing,
                    juice.drink.energy.protein,
                    juice.drink.energy.totalFat,
                    juice.drink.energy.saturatedFat,
                    juice.drink.energy.transFat,
                    juice.drink.energy.carbohydrates,
                    juice.drink.energy.sugars,
                    juice.drink.energy.addedSugars,
                    juice.drink.energy.dietaryFiber,
                    juice.drink.energy.sodium,
                    juice.drink.energy.ingredients,
                )
            )
        )
        try {
            juiceCollection.insertOne(juiceEntity)
            true
        } catch (_: Exception) {
            false
        }
    }

    /**
     * This function update a juice document in the collection
     *
     * @param juice the juiceEntity is the object with the information about it
     * @return a boolean that is the result about the process in database
     */
    override suspend fun updateJuice(juice: Juice): Boolean = withContext(Dispatchers.IO) {
        val juiceEntity =
            JuiceEntity(
                ObjectId(juice.id).toId(),
                juice.description,
                juice.imgURL,
                BrandEntity(
                    newId(),
                    juice.brand.name,
                    juice.brand.description,
                    juice.brand.img
                ),
                DrinkEntity(
                    ObjectId(juice.drink.id).toId(),
                    juice.drink.name,
                    juice.drink.temperature,
                    juice.drink.isAlcoholic,
                    juice.drink.millilitres,
                    EnergyEntity(
                        ObjectId(juice.drink.energy.id).toId(),
                        juice.drink.energy.energyContent,
                        juice.drink.energy.perServing,
                        juice.drink.energy.protein,
                        juice.drink.energy.totalFat,
                        juice.drink.energy.saturatedFat,
                        juice.drink.energy.transFat,
                        juice.drink.energy.carbohydrates,
                        juice.drink.energy.sugars,
                        juice.drink.energy.addedSugars,
                        juice.drink.energy.dietaryFiber,
                        juice.drink.energy.sodium,
                        juice.drink.energy.ingredients,
                    )
                )
            )

        val updateResult = juiceCollection.replaceOne(
            JuiceEntity::id eq juiceEntity.id,
            juiceEntity
        )

        updateResult.matchedCount > 0 && updateResult.modifiedCount > 0
    }

    /**
     * This function delete a juice in the database
     *
     * @param id the id is the identifier to find it
     * @return a boolean that is the result about the process in database
     */
    override suspend fun deleteJuice(id: ObjectId): Boolean = withContext(Dispatchers.IO) {
        val deleteResult = juiceCollection.deleteOneById(id)
        deleteResult.deletedCount == 1L
    }

    /**
     * This function gives all juices
     *
     * @return a list of juices
     */
    override suspend fun getJuices(): List<Juice> = juiceCollection.find().toList().let {
        it.map { juiceEntity ->
            Juice(
                juiceEntity.id.toString(),
                juiceEntity.description,
                juiceEntity.imgURL,
                Brand(
                    juiceEntity.brandEntity.id.toString(),
                    juiceEntity.brandEntity.name,
                    juiceEntity.brandEntity.description,
                    juiceEntity.brandEntity.img
                ),
                Drink(
                    juiceEntity.drinkEntity.id.toString(),
                    juiceEntity.drinkEntity.name,
                    juiceEntity.drinkEntity.temperature,
                    juiceEntity.drinkEntity.isAlcoholic,
                    juiceEntity.drinkEntity.millilitres,
                    Energy(
                        juiceEntity.drinkEntity.energyEntity.id.toString(),
                        juiceEntity.drinkEntity.energyEntity.energyContent,
                        juiceEntity.drinkEntity.energyEntity.perServing,
                        juiceEntity.drinkEntity.energyEntity.protein,
                        juiceEntity.drinkEntity.energyEntity.totalFat,
                        juiceEntity.drinkEntity.energyEntity.saturatedFat,
                        juiceEntity.drinkEntity.energyEntity.transFat,
                        juiceEntity.drinkEntity.energyEntity.carbohydrates,
                        juiceEntity.drinkEntity.energyEntity.sugars,
                        juiceEntity.drinkEntity.energyEntity.addedSugars,
                        juiceEntity.drinkEntity.energyEntity.dietaryFiber,
                        juiceEntity.drinkEntity.energyEntity.sodium,
                        juiceEntity.drinkEntity.energyEntity.ingredients,
                    ),
                ),

                )
        }
    }


    /*Functions about Liqueur Collection*/

    /**
     * This function insert a new user in the database
     */
    private val liqueurCollection = database.getCollection<LiqueurEntity>("liqueur")

    /**
     * This function insert a new liqueur in the database
     *
     * @param liqueur the liqueurEntity is the object with the information about it
     * @return a liqueur
     */
    override suspend fun insertLiqueur(liqueur: Liqueur): Boolean = withContext(Dispatchers.IO) {
        val liqueurEntity = LiqueurEntity(
            newId(),
            liqueur.description,
            liqueur.imgURL,
            BrandEntity(
                newId(),
                liqueur.brand.name,
                liqueur.brand.description,
                liqueur.brand.img
            ),
            DrinkEntity(
                newId(),
                liqueur.drink.name,
                liqueur.drink.temperature,
                liqueur.drink.isAlcoholic,
                liqueur.drink.millilitres,
                EnergyEntity(
                    newId(),
                    liqueur.drink.energy.energyContent,
                    liqueur.drink.energy.perServing,
                    liqueur.drink.energy.protein,
                    liqueur.drink.energy.totalFat,
                    liqueur.drink.energy.saturatedFat,
                    liqueur.drink.energy.transFat,
                    liqueur.drink.energy.carbohydrates,
                    liqueur.drink.energy.sugars,
                    liqueur.drink.energy.addedSugars,
                    liqueur.drink.energy.dietaryFiber,
                    liqueur.drink.energy.sodium,
                    liqueur.drink.energy.ingredients,
                )
            )
        )
        try {
            liqueurCollection.insertOne(liqueurEntity)
            true
        } catch (_: Exception) {
            false
        }
    }

    /**
     * This function update a liqueur document in the collection
     *
     * @param liqueur the liqueurEntity is the object with the information about it
     * @return a boolean that is the result about the process in database
     */
    override suspend fun updateLiqueur(liqueur: Liqueur): Boolean = withContext(Dispatchers.IO) {
        val liqueurEntity =
            LiqueurEntity(
                ObjectId(liqueur.id).toId(),
                liqueur.description,
                liqueur.imgURL,
                BrandEntity(
                    newId(),
                    liqueur.brand.name,
                    liqueur.brand.description,
                    liqueur.brand.img
                ),
                DrinkEntity(
                    ObjectId(liqueur.drink.id).toId(),
                    liqueur.drink.name,
                    liqueur.drink.temperature,
                    liqueur.drink.isAlcoholic,
                    liqueur.drink.millilitres,
                    EnergyEntity(
                        ObjectId(liqueur.drink.energy.id).toId(),
                        liqueur.drink.energy.energyContent,
                        liqueur.drink.energy.perServing,
                        liqueur.drink.energy.protein,
                        liqueur.drink.energy.totalFat,
                        liqueur.drink.energy.saturatedFat,
                        liqueur.drink.energy.transFat,
                        liqueur.drink.energy.carbohydrates,
                        liqueur.drink.energy.sugars,
                        liqueur.drink.energy.addedSugars,
                        liqueur.drink.energy.dietaryFiber,
                        liqueur.drink.energy.sodium,
                        liqueur.drink.energy.ingredients,
                    )
                )
            )

        val updateResult = liqueurCollection.replaceOne(
            LiqueurEntity::id eq liqueurEntity.id,
            liqueurEntity
        )

        updateResult.matchedCount > 0 && updateResult.modifiedCount > 0
    }

    /**
     * This function delete a liqueur in the database
     *
     * @param id the id is the identifier to find it
     * @return a boolean that is the result about the process in database
     */
    override suspend fun deleteLiqueur(id: ObjectId): Boolean = withContext(Dispatchers.IO) {
        val deleteResult = liqueurCollection.deleteOneById(id)
        deleteResult.deletedCount == 1L
    }

    /**
     * This function gives all liqueurs
     *
     * @return a list of liqueurs
     */
    override suspend fun getLiqueurs(): List<Liqueur> = liqueurCollection.find().toList().let {
        it.map { liqueurEntity ->
            Liqueur(
                liqueurEntity.id.toString(),
                liqueurEntity.description,
                liqueurEntity.imgURL,
                Brand(
                    liqueurEntity.brandEntity.id.toString(),
                    liqueurEntity.brandEntity.name,
                    liqueurEntity.brandEntity.description,
                    liqueurEntity.brandEntity.img
                ),
                Drink(
                    liqueurEntity.drinkEntity.id.toString(),
                    liqueurEntity.drinkEntity.name,
                    liqueurEntity.drinkEntity.temperature,
                    liqueurEntity.drinkEntity.isAlcoholic,
                    liqueurEntity.drinkEntity.millilitres,
                    Energy(
                        liqueurEntity.drinkEntity.energyEntity.id.toString(),
                        liqueurEntity.drinkEntity.energyEntity.energyContent,
                        liqueurEntity.drinkEntity.energyEntity.perServing,
                        liqueurEntity.drinkEntity.energyEntity.protein,
                        liqueurEntity.drinkEntity.energyEntity.totalFat,
                        liqueurEntity.drinkEntity.energyEntity.saturatedFat,
                        liqueurEntity.drinkEntity.energyEntity.transFat,
                        liqueurEntity.drinkEntity.energyEntity.carbohydrates,
                        liqueurEntity.drinkEntity.energyEntity.sugars,
                        liqueurEntity.drinkEntity.energyEntity.addedSugars,
                        liqueurEntity.drinkEntity.energyEntity.dietaryFiber,
                        liqueurEntity.drinkEntity.energyEntity.sodium,
                        liqueurEntity.drinkEntity.energyEntity.ingredients,
                    ),
                ),

                )
        }
    }


    /*Functions about Mocktail Collection*/

    /**
     * This function insert a new user in the database
     */
    private val mocktailCollection = database.getCollection<MocktailEntity>("mocktail")

    /**
     * This function insert a new mocktail in the database
     *
     * @param mocktail the mocktailEntity is the object with the information about it
     * @return a mocktail
     */
    override suspend fun insertMocktail(mocktail: Mocktail): Boolean = withContext(Dispatchers.IO) {
        val mocktailEntity = MocktailEntity(
            newId(),
            mocktail.description,
            mocktail.imgURL,
            BrandEntity(
                newId(),
                mocktail.brand.name,
                mocktail.brand.description,
                mocktail.brand.img
            ),
            DrinkEntity(
                newId(),
                mocktail.drink.name,
                mocktail.drink.temperature,
                mocktail.drink.isAlcoholic,
                mocktail.drink.millilitres,
                EnergyEntity(
                    newId(),
                    mocktail.drink.energy.energyContent,
                    mocktail.drink.energy.perServing,
                    mocktail.drink.energy.protein,
                    mocktail.drink.energy.totalFat,
                    mocktail.drink.energy.saturatedFat,
                    mocktail.drink.energy.transFat,
                    mocktail.drink.energy.carbohydrates,
                    mocktail.drink.energy.sugars,
                    mocktail.drink.energy.addedSugars,
                    mocktail.drink.energy.dietaryFiber,
                    mocktail.drink.energy.sodium,
                    mocktail.drink.energy.ingredients,
                )
            )
        )
        try {
            mocktailCollection.insertOne(mocktailEntity)
            true
        } catch (_: Exception) {
            false
        }
    }

    /**
     * This function update a mocktail document in the collection
     *
     * @param mocktail the mocktailEntity is the object with the information about it
     * @return a boolean that is the result about the process in database
     */
    override suspend fun updateMocktail(mocktail: Mocktail): Boolean = withContext(Dispatchers.IO) {
        val mocktailEntity =
            MocktailEntity(
                ObjectId(mocktail.id).toId(),
                mocktail.description,
                mocktail.imgURL,
                BrandEntity(
                    newId(),
                    mocktail.brand.name,
                    mocktail.brand.description,
                    mocktail.brand.img
                ),
                DrinkEntity(
                    ObjectId(mocktail.drink.id).toId(),
                    mocktail.drink.name,
                    mocktail.drink.temperature,
                    mocktail.drink.isAlcoholic,
                    mocktail.drink.millilitres,
                    EnergyEntity(
                        ObjectId(mocktail.drink.energy.id).toId(),
                        mocktail.drink.energy.energyContent,
                        mocktail.drink.energy.perServing,
                        mocktail.drink.energy.protein,
                        mocktail.drink.energy.totalFat,
                        mocktail.drink.energy.saturatedFat,
                        mocktail.drink.energy.transFat,
                        mocktail.drink.energy.carbohydrates,
                        mocktail.drink.energy.sugars,
                        mocktail.drink.energy.addedSugars,
                        mocktail.drink.energy.dietaryFiber,
                        mocktail.drink.energy.sodium,
                        mocktail.drink.energy.ingredients,
                    )
                )
            )

        val updateResult = mocktailCollection.replaceOne(
            MocktailEntity::id eq mocktailEntity.id,
            mocktailEntity
        )

        updateResult.matchedCount > 0 && updateResult.modifiedCount > 0
    }

    /**
     * This function delete a mocktail in the database
     *
     * @param id the id is the identifier to find it
     * @return a boolean that is the result about the process in database
     */
    override suspend fun deleteMocktail(id: ObjectId): Boolean = withContext(Dispatchers.IO) {
        val deleteResult = mocktailCollection.deleteOneById(id)
        deleteResult.deletedCount == 1L
    }

    /**
     * This function gives all mocktails
     *
     * @return a list of mocktails
     */
    override suspend fun getMocktails(): List<Mocktail> = mocktailCollection.find().toList().let {
        it.map { mocktailEntity ->
            Mocktail(
                mocktailEntity.id.toString(),
                mocktailEntity.description,
                mocktailEntity.imgURL,
                Brand(
                    mocktailEntity.brandEntity.id.toString(),
                    mocktailEntity.brandEntity.name,
                    mocktailEntity.brandEntity.description,
                    mocktailEntity.brandEntity.img
                ),
                Drink(
                    mocktailEntity.drinkEntity.id.toString(),
                    mocktailEntity.drinkEntity.name,
                    mocktailEntity.drinkEntity.temperature,
                    mocktailEntity.drinkEntity.isAlcoholic,
                    mocktailEntity.drinkEntity.millilitres,
                    Energy(
                        mocktailEntity.drinkEntity.energyEntity.id.toString(),
                        mocktailEntity.drinkEntity.energyEntity.energyContent,
                        mocktailEntity.drinkEntity.energyEntity.perServing,
                        mocktailEntity.drinkEntity.energyEntity.protein,
                        mocktailEntity.drinkEntity.energyEntity.totalFat,
                        mocktailEntity.drinkEntity.energyEntity.saturatedFat,
                        mocktailEntity.drinkEntity.energyEntity.transFat,
                        mocktailEntity.drinkEntity.energyEntity.carbohydrates,
                        mocktailEntity.drinkEntity.energyEntity.sugars,
                        mocktailEntity.drinkEntity.energyEntity.addedSugars,
                        mocktailEntity.drinkEntity.energyEntity.dietaryFiber,
                        mocktailEntity.drinkEntity.energyEntity.sodium,
                        mocktailEntity.drinkEntity.energyEntity.ingredients,
                    ),
                ),

                )
        }
    }


    /*Functions about Smoothie Collection*/

    /**
     * This function insert a new user in the database
     */
    private val smoothieCollection = database.getCollection<SmoothieEntity>("smoothie")

    /**
     * This function insert a new smoothie in the database
     *
     * @param smoothie the smoothieEntity is the object with the information about it
     * @return a smoothie
     */
    override suspend fun insertSmoothie(smoothie: Smoothie): Boolean = withContext(Dispatchers.IO) {
        val smoothieEntity = SmoothieEntity(
            newId(),
            smoothie.description,
            smoothie.imgURL,
            BrandEntity(
                newId(),
                smoothie.brand.name,
                smoothie.brand.description,
                smoothie.brand.img
            ),
            DrinkEntity(
                newId(),
                smoothie.drink.name,
                smoothie.drink.temperature,
                smoothie.drink.isAlcoholic,
                smoothie.drink.millilitres,
                EnergyEntity(
                    newId(),
                    smoothie.drink.energy.energyContent,
                    smoothie.drink.energy.perServing,
                    smoothie.drink.energy.protein,
                    smoothie.drink.energy.totalFat,
                    smoothie.drink.energy.saturatedFat,
                    smoothie.drink.energy.transFat,
                    smoothie.drink.energy.carbohydrates,
                    smoothie.drink.energy.sugars,
                    smoothie.drink.energy.addedSugars,
                    smoothie.drink.energy.dietaryFiber,
                    smoothie.drink.energy.sodium,
                    smoothie.drink.energy.ingredients,
                )
            )
        )
        try {
            smoothieCollection.insertOne(smoothieEntity)
            true
        } catch (_: Exception) {
            false
        }
    }

    /**
     * This function update a smoothie document in the collection
     *
     * @param smoothie the smoothieEntity is the object with the information about it
     * @return a boolean that is the result about the process in database
     */
    override suspend fun updateSmoothie(smoothie: Smoothie): Boolean = withContext(Dispatchers.IO) {
        val smoothieEntity =
            SmoothieEntity(
                ObjectId(smoothie.id).toId(),
                smoothie.description,
                smoothie.imgURL,
                BrandEntity(
                    newId(),
                    smoothie.brand.name,
                    smoothie.brand.description,
                    smoothie.brand.img
                ),
                DrinkEntity(
                    ObjectId(smoothie.drink.id).toId(),
                    smoothie.drink.name,
                    smoothie.drink.temperature,
                    smoothie.drink.isAlcoholic,
                    smoothie.drink.millilitres,
                    EnergyEntity(
                        ObjectId(smoothie.drink.energy.id).toId(),
                        smoothie.drink.energy.energyContent,
                        smoothie.drink.energy.perServing,
                        smoothie.drink.energy.protein,
                        smoothie.drink.energy.totalFat,
                        smoothie.drink.energy.saturatedFat,
                        smoothie.drink.energy.transFat,
                        smoothie.drink.energy.carbohydrates,
                        smoothie.drink.energy.sugars,
                        smoothie.drink.energy.addedSugars,
                        smoothie.drink.energy.dietaryFiber,
                        smoothie.drink.energy.sodium,
                        smoothie.drink.energy.ingredients,
                    )
                )
            )

        val updateResult = smoothieCollection.replaceOne(
            SmoothieEntity::id eq smoothieEntity.id,
            smoothieEntity
        )

        updateResult.matchedCount > 0 && updateResult.modifiedCount > 0
    }

    /**
     * This function delete a smoothie in the database
     *
     * @param id the id is the identifier to find it
     * @return a boolean that is the result about the process in database
     */
    override suspend fun deleteSmoothie(id: ObjectId): Boolean = withContext(Dispatchers.IO) {
        val deleteResult = smoothieCollection.deleteOneById(id)
        deleteResult.deletedCount == 1L
    }

    /**
     * This function gives all smoothies
     *
     * @return a list of smoothies
     */
    override suspend fun getSmoothies(): List<Smoothie> = smoothieCollection.find().toList().let {
        it.map { smoothieEntity ->
            Smoothie(
                smoothieEntity.id.toString(),
                smoothieEntity.description,
                smoothieEntity.imgURL,
                Brand(
                    smoothieEntity.brandEntity.id.toString(),
                    smoothieEntity.brandEntity.name,
                    smoothieEntity.brandEntity.description,
                    smoothieEntity.brandEntity.img
                ),
                Drink(
                    smoothieEntity.drinkEntity.id.toString(),
                    smoothieEntity.drinkEntity.name,
                    smoothieEntity.drinkEntity.temperature,
                    smoothieEntity.drinkEntity.isAlcoholic,
                    smoothieEntity.drinkEntity.millilitres,
                    Energy(
                        smoothieEntity.drinkEntity.energyEntity.id.toString(),
                        smoothieEntity.drinkEntity.energyEntity.energyContent,
                        smoothieEntity.drinkEntity.energyEntity.perServing,
                        smoothieEntity.drinkEntity.energyEntity.protein,
                        smoothieEntity.drinkEntity.energyEntity.totalFat,
                        smoothieEntity.drinkEntity.energyEntity.saturatedFat,
                        smoothieEntity.drinkEntity.energyEntity.transFat,
                        smoothieEntity.drinkEntity.energyEntity.carbohydrates,
                        smoothieEntity.drinkEntity.energyEntity.sugars,
                        smoothieEntity.drinkEntity.energyEntity.addedSugars,
                        smoothieEntity.drinkEntity.energyEntity.dietaryFiber,
                        smoothieEntity.drinkEntity.energyEntity.sodium,
                        smoothieEntity.drinkEntity.energyEntity.ingredients,
                    ),
                ),

                )
        }
    }


    /*Functions about Soda Collection*/

    /**
     * This function insert a new user in the database
     */
    private val sodaCollection = database.getCollection<SodaEntity>("soda")

    /**
     * This function insert a new soda in the database
     *
     * @param soda the sodaEntity is the object with the information about it
     * @return a soda
     */
    override suspend fun insertSoda(soda: Soda): Boolean = withContext(Dispatchers.IO) {
        val sodaEntity = SodaEntity(
            newId(),
            soda.description,
            soda.imgURL,
            BrandEntity(
                newId(),
                soda.brand.name,
                soda.brand.description,
                soda.brand.img
            ),
            DrinkEntity(
                newId(),
                soda.drink.name,
                soda.drink.temperature,
                soda.drink.isAlcoholic,
                soda.drink.millilitres,
                EnergyEntity(
                    newId(),
                    soda.drink.energy.energyContent,
                    soda.drink.energy.perServing,
                    soda.drink.energy.protein,
                    soda.drink.energy.totalFat,
                    soda.drink.energy.saturatedFat,
                    soda.drink.energy.transFat,
                    soda.drink.energy.carbohydrates,
                    soda.drink.energy.sugars,
                    soda.drink.energy.addedSugars,
                    soda.drink.energy.dietaryFiber,
                    soda.drink.energy.sodium,
                    soda.drink.energy.ingredients,
                )
            )
        )
        try {
            sodaCollection.insertOne(sodaEntity)
            true
        } catch (_: Exception) {
            false
        }
    }

    /**
     * This function update a soda document in the collection
     *
     * @param soda the sodaEntity is the object with the information about it
     * @return a boolean that is the result about the process in database
     */
    override suspend fun updateSoda(soda: Soda): Boolean = withContext(Dispatchers.IO) {
        val sodaEntity =
            SodaEntity(
                ObjectId(soda.id).toId(),
                soda.description,
                soda.imgURL,
                BrandEntity(
                    newId(),
                    soda.brand.name,
                    soda.brand.description,
                    soda.brand.img
                ),
                DrinkEntity(
                    ObjectId(soda.drink.id).toId(),
                    soda.drink.name,
                    soda.drink.temperature,
                    soda.drink.isAlcoholic,
                    soda.drink.millilitres,
                    EnergyEntity(
                        ObjectId(soda.drink.energy.id).toId(),
                        soda.drink.energy.energyContent,
                        soda.drink.energy.perServing,
                        soda.drink.energy.protein,
                        soda.drink.energy.totalFat,
                        soda.drink.energy.saturatedFat,
                        soda.drink.energy.transFat,
                        soda.drink.energy.carbohydrates,
                        soda.drink.energy.sugars,
                        soda.drink.energy.addedSugars,
                        soda.drink.energy.dietaryFiber,
                        soda.drink.energy.sodium,
                        soda.drink.energy.ingredients,
                    )
                )
            )

        val updateResult = sodaCollection.replaceOne(
            SodaEntity::id eq sodaEntity.id,
            sodaEntity
        )

        updateResult.matchedCount > 0 && updateResult.modifiedCount > 0
    }

    /**
     * This function delete a soda in the database
     *
     * @param id the id is the identifier to find it
     * @return a boolean that is the result about the process in database
     */
    override suspend fun deleteSoda(id: ObjectId): Boolean = withContext(Dispatchers.IO) {
        val deleteResult = sodaCollection.deleteOneById(id)
        deleteResult.deletedCount == 1L
    }

    /**
     * This function gives all sodas
     *
     * @return a list of sodas
     */
    override suspend fun getSodas(): List<Soda> = sodaCollection.find().toList().let {
        it.map { sodaEntity ->
            Soda(
                sodaEntity.id.toString(),
                sodaEntity.description,
                sodaEntity.imgURL,
                Brand(
                    sodaEntity.brandEntity.id.toString(),
                    sodaEntity.brandEntity.name,
                    sodaEntity.brandEntity.description,
                    sodaEntity.brandEntity.img
                ),
                Drink(
                    sodaEntity.drinkEntity.id.toString(),
                    sodaEntity.drinkEntity.name,
                    sodaEntity.drinkEntity.temperature,
                    sodaEntity.drinkEntity.isAlcoholic,
                    sodaEntity.drinkEntity.millilitres,
                    Energy(
                        sodaEntity.drinkEntity.energyEntity.id.toString(),
                        sodaEntity.drinkEntity.energyEntity.energyContent,
                        sodaEntity.drinkEntity.energyEntity.perServing,
                        sodaEntity.drinkEntity.energyEntity.protein,
                        sodaEntity.drinkEntity.energyEntity.totalFat,
                        sodaEntity.drinkEntity.energyEntity.saturatedFat,
                        sodaEntity.drinkEntity.energyEntity.transFat,
                        sodaEntity.drinkEntity.energyEntity.carbohydrates,
                        sodaEntity.drinkEntity.energyEntity.sugars,
                        sodaEntity.drinkEntity.energyEntity.addedSugars,
                        sodaEntity.drinkEntity.energyEntity.dietaryFiber,
                        sodaEntity.drinkEntity.energyEntity.sodium,
                        sodaEntity.drinkEntity.energyEntity.ingredients,
                    ),
                ),

                )
        }
    }


    /*Functions about Spirit Collection*/

    /**
     * This function insert a new user in the database
     */
    private val spiritCollection = database.getCollection<SpiritEntity>("spirit")

    /**
     * This function insert a new spirit in the database
     *
     * @param spirit the spiritEntity is the object with the information about it
     * @return a spirit
     */
    override suspend fun insertSpirit(spirit: Spirit): Boolean = withContext(Dispatchers.IO) {
        val spiritEntity = SpiritEntity(
            newId(),
            spirit.description,
            spirit.imgURL,
            BrandEntity(
                newId(),
                spirit.brand.name,
                spirit.brand.description,
                spirit.brand.img
            ),
            DrinkEntity(
                newId(),
                spirit.drink.name,
                spirit.drink.temperature,
                spirit.drink.isAlcoholic,
                spirit.drink.millilitres,
                EnergyEntity(
                    newId(),
                    spirit.drink.energy.energyContent,
                    spirit.drink.energy.perServing,
                    spirit.drink.energy.protein,
                    spirit.drink.energy.totalFat,
                    spirit.drink.energy.saturatedFat,
                    spirit.drink.energy.transFat,
                    spirit.drink.energy.carbohydrates,
                    spirit.drink.energy.sugars,
                    spirit.drink.energy.addedSugars,
                    spirit.drink.energy.dietaryFiber,
                    spirit.drink.energy.sodium,
                    spirit.drink.energy.ingredients,
                )
            )
        )
        try {
            spiritCollection.insertOne(spiritEntity)
            true
        } catch (_: Exception) {
            false
        }
    }

    /**
     * This function update a spirit document in the collection
     *
     * @param spirit the spiritEntity is the object with the information about it
     * @return a boolean that is the result about the process in database
     */
    override suspend fun updateSpirit(spirit: Spirit): Boolean = withContext(Dispatchers.IO) {
        val spiritEntity =
            SpiritEntity(
                ObjectId(spirit.id).toId(),
                spirit.description,
                spirit.imgURL,
                BrandEntity(
                    newId(),
                    spirit.brand.name,
                    spirit.brand.description,
                    spirit.brand.img
                ),
                DrinkEntity(
                    ObjectId(spirit.drink.id).toId(),
                    spirit.drink.name,
                    spirit.drink.temperature,
                    spirit.drink.isAlcoholic,
                    spirit.drink.millilitres,
                    EnergyEntity(
                        ObjectId(spirit.drink.energy.id).toId(),
                        spirit.drink.energy.energyContent,
                        spirit.drink.energy.perServing,
                        spirit.drink.energy.protein,
                        spirit.drink.energy.totalFat,
                        spirit.drink.energy.saturatedFat,
                        spirit.drink.energy.transFat,
                        spirit.drink.energy.carbohydrates,
                        spirit.drink.energy.sugars,
                        spirit.drink.energy.addedSugars,
                        spirit.drink.energy.dietaryFiber,
                        spirit.drink.energy.sodium,
                        spirit.drink.energy.ingredients,
                    )
                )
            )

        val updateResult = spiritCollection.replaceOne(
            SpiritEntity::id eq spiritEntity.id,
            spiritEntity
        )

        updateResult.matchedCount > 0 && updateResult.modifiedCount > 0
    }

    /**
     * This function delete a spirit in the database
     *
     * @param id the id is the identifier to find it
     * @return a boolean that is the result about the process in database
     */
    override suspend fun deleteSpirit(id: ObjectId): Boolean = withContext(Dispatchers.IO) {
        val deleteResult = spiritCollection.deleteOneById(id)
        deleteResult.deletedCount == 1L
    }

    /**
     * This function gives all spirits
     *
     * @return a list of spirits
     */
    override suspend fun getSpirits(): List<Spirit> = spiritCollection.find().toList().let {
        it.map { spiritEntity ->
            Spirit(
                spiritEntity.id.toString(),
                spiritEntity.description,
                spiritEntity.imgURL,
                Brand(
                    spiritEntity.brandEntity.id.toString(),
                    spiritEntity.brandEntity.name,
                    spiritEntity.brandEntity.description,
                    spiritEntity.brandEntity.img
                ),
                Drink(
                    spiritEntity.drinkEntity.id.toString(),
                    spiritEntity.drinkEntity.name,
                    spiritEntity.drinkEntity.temperature,
                    spiritEntity.drinkEntity.isAlcoholic,
                    spiritEntity.drinkEntity.millilitres,
                    Energy(
                        spiritEntity.drinkEntity.energyEntity.id.toString(),
                        spiritEntity.drinkEntity.energyEntity.energyContent,
                        spiritEntity.drinkEntity.energyEntity.perServing,
                        spiritEntity.drinkEntity.energyEntity.protein,
                        spiritEntity.drinkEntity.energyEntity.totalFat,
                        spiritEntity.drinkEntity.energyEntity.saturatedFat,
                        spiritEntity.drinkEntity.energyEntity.transFat,
                        spiritEntity.drinkEntity.energyEntity.carbohydrates,
                        spiritEntity.drinkEntity.energyEntity.sugars,
                        spiritEntity.drinkEntity.energyEntity.addedSugars,
                        spiritEntity.drinkEntity.energyEntity.dietaryFiber,
                        spiritEntity.drinkEntity.energyEntity.sodium,
                        spiritEntity.drinkEntity.energyEntity.ingredients,
                    ),
                ),

                )
        }
    }


    /*Functions about Tea Collection*/

    /**
     * This function insert a new user in the database
     */
    private val teaCollection = database.getCollection<TeaEntity>("tea")

    /**
     * This function insert a new tea in the database
     *
     * @param tea the teaEntity is the object with the information about it
     * @return a tea
     */
    override suspend fun insertTea(tea: Tea): Boolean = withContext(Dispatchers.IO) {
        val teaEntity = TeaEntity(
            newId(),
            tea.description,
            tea.imgURL,
            BrandEntity(
                newId(),
                tea.brand.name,
                tea.brand.description,
                tea.brand.img
            ),
            DrinkEntity(
                newId(),
                tea.drink.name,
                tea.drink.temperature,
                tea.drink.isAlcoholic,
                tea.drink.millilitres,
                EnergyEntity(
                    newId(),
                    tea.drink.energy.energyContent,
                    tea.drink.energy.perServing,
                    tea.drink.energy.protein,
                    tea.drink.energy.totalFat,
                    tea.drink.energy.saturatedFat,
                    tea.drink.energy.transFat,
                    tea.drink.energy.carbohydrates,
                    tea.drink.energy.sugars,
                    tea.drink.energy.addedSugars,
                    tea.drink.energy.dietaryFiber,
                    tea.drink.energy.sodium,
                    tea.drink.energy.ingredients,
                )
            )
        )
        try {
            teaCollection.insertOne(teaEntity)
            true
        } catch (_: Exception) {
            false
        }
    }

    /**
     * This function update a tea document in the collection
     *
     * @param tea the teaEntity is the object with the information about it
     * @return a boolean that is the result about the process in database
     */
    override suspend fun updateTea(tea: Tea): Boolean = withContext(Dispatchers.IO) {
        val teaEntity =
            TeaEntity(
                ObjectId(tea.id).toId(),
                tea.description,
                tea.imgURL,
                BrandEntity(
                    newId(),
                    tea.brand.name,
                    tea.brand.description,
                    tea.brand.img
                ),
                DrinkEntity(
                    ObjectId(tea.drink.id).toId(),
                    tea.drink.name,
                    tea.drink.temperature,
                    tea.drink.isAlcoholic,
                    tea.drink.millilitres,
                    EnergyEntity(
                        ObjectId(tea.drink.energy.id).toId(),
                        tea.drink.energy.energyContent,
                        tea.drink.energy.perServing,
                        tea.drink.energy.protein,
                        tea.drink.energy.totalFat,
                        tea.drink.energy.saturatedFat,
                        tea.drink.energy.transFat,
                        tea.drink.energy.carbohydrates,
                        tea.drink.energy.sugars,
                        tea.drink.energy.addedSugars,
                        tea.drink.energy.dietaryFiber,
                        tea.drink.energy.sodium,
                        tea.drink.energy.ingredients,
                    )
                )
            )

        val updateResult = teaCollection.replaceOne(
            TeaEntity::id eq teaEntity.id,
            teaEntity
        )

        updateResult.matchedCount > 0 && updateResult.modifiedCount > 0
    }

    /**
     * This function delete a tea in the database
     *
     * @param id the id is the identifier to find it
     * @return a boolean that is the result about the process in database
     */
    override suspend fun deleteTea(id: ObjectId): Boolean = withContext(Dispatchers.IO) {
        val deleteResult = teaCollection.deleteOneById(id)
        deleteResult.deletedCount == 1L
    }

    /**
     * This function gives all teas
     *
     * @return a list of teas
     */
    override suspend fun getTeas(): List<Tea> = teaCollection.find().toList().let {
        it.map { teaEntity ->
            Tea(
                teaEntity.id.toString(),
                teaEntity.description,
                teaEntity.imgURL,
                Brand(
                    teaEntity.brandEntity.id.toString(),
                    teaEntity.brandEntity.name,
                    teaEntity.brandEntity.description,
                    teaEntity.brandEntity.img
                ),
                Drink(
                    teaEntity.drinkEntity.id.toString(),
                    teaEntity.drinkEntity.name,
                    teaEntity.drinkEntity.temperature,
                    teaEntity.drinkEntity.isAlcoholic,
                    teaEntity.drinkEntity.millilitres,
                    Energy(
                        teaEntity.drinkEntity.energyEntity.id.toString(),
                        teaEntity.drinkEntity.energyEntity.energyContent,
                        teaEntity.drinkEntity.energyEntity.perServing,
                        teaEntity.drinkEntity.energyEntity.protein,
                        teaEntity.drinkEntity.energyEntity.totalFat,
                        teaEntity.drinkEntity.energyEntity.saturatedFat,
                        teaEntity.drinkEntity.energyEntity.transFat,
                        teaEntity.drinkEntity.energyEntity.carbohydrates,
                        teaEntity.drinkEntity.energyEntity.sugars,
                        teaEntity.drinkEntity.energyEntity.addedSugars,
                        teaEntity.drinkEntity.energyEntity.dietaryFiber,
                        teaEntity.drinkEntity.energyEntity.sodium,
                        teaEntity.drinkEntity.energyEntity.ingredients,
                    ),
                ),

                )
        }
    }


    /*Functions about Tequila Collection*/

    /**
     * This function insert a new user in the database
     */
    private val tequilaCollection = database.getCollection<TequilaEntity>("tequila")

    /**
     * This function insert a new tequila in the database
     *
     * @param tequila the tequilaEntity is the object with the information about it
     * @return a tequila
     */
    override suspend fun insertTequila(tequila: Tequila): Boolean = withContext(Dispatchers.IO) {
        val tequilaEntity = TequilaEntity(
            newId(),
            tequila.description,
            tequila.imgURL,
            BrandEntity(
                newId(),
                tequila.brand.name,
                tequila.brand.description,
                tequila.brand.img
            ),
            DrinkEntity(
                newId(),
                tequila.drink.name,
                tequila.drink.temperature,
                tequila.drink.isAlcoholic,
                tequila.drink.millilitres,
                EnergyEntity(
                    newId(),
                    tequila.drink.energy.energyContent,
                    tequila.drink.energy.perServing,
                    tequila.drink.energy.protein,
                    tequila.drink.energy.totalFat,
                    tequila.drink.energy.saturatedFat,
                    tequila.drink.energy.transFat,
                    tequila.drink.energy.carbohydrates,
                    tequila.drink.energy.sugars,
                    tequila.drink.energy.addedSugars,
                    tequila.drink.energy.dietaryFiber,
                    tequila.drink.energy.sodium,
                    tequila.drink.energy.ingredients,
                )
            )
        )
        try {
            tequilaCollection.insertOne(tequilaEntity)
            true
        } catch (_: Exception) {
            false
        }
    }

    /**
     * This function update a tequila document in the collection
     *
     * @param tequila the tequilaEntity is the object with the information about it
     * @return a boolean that is the result about the process in database
     */
    override suspend fun updateTequila(tequila: Tequila): Boolean = withContext(Dispatchers.IO) {
        val tequilaEntity =
            TequilaEntity(
                ObjectId(tequila.id).toId(),
                tequila.description,
                tequila.imgURL,
                BrandEntity(
                    newId(),
                    tequila.brand.name,
                    tequila.brand.description,
                    tequila.brand.img
                ),
                DrinkEntity(
                    ObjectId(tequila.drink.id).toId(),
                    tequila.drink.name,
                    tequila.drink.temperature,
                    tequila.drink.isAlcoholic,
                    tequila.drink.millilitres,
                    EnergyEntity(
                        ObjectId(tequila.drink.energy.id).toId(),
                        tequila.drink.energy.energyContent,
                        tequila.drink.energy.perServing,
                        tequila.drink.energy.protein,
                        tequila.drink.energy.totalFat,
                        tequila.drink.energy.saturatedFat,
                        tequila.drink.energy.transFat,
                        tequila.drink.energy.carbohydrates,
                        tequila.drink.energy.sugars,
                        tequila.drink.energy.addedSugars,
                        tequila.drink.energy.dietaryFiber,
                        tequila.drink.energy.sodium,
                        tequila.drink.energy.ingredients,
                    )
                )
            )

        val updateResult = tequilaCollection.replaceOne(
            TequilaEntity::id eq tequilaEntity.id,
            tequilaEntity
        )

        updateResult.matchedCount > 0 && updateResult.modifiedCount > 0
    }

    /**
     * This function delete a tequila in the database
     *
     * @param id the id is the identifier to find it
     * @return a boolean that is the result about the process in database
     */
    override suspend fun deleteTequila(id: ObjectId): Boolean = withContext(Dispatchers.IO) {
        val deleteResult = tequilaCollection.deleteOneById(id)
        deleteResult.deletedCount == 1L
    }

    /**
     * This function gives all tequilas
     *
     * @return a list of tequilas
     */
    override suspend fun getTequilas(): List<Tequila> = tequilaCollection.find().toList().let {
        it.map { tequilaEntity ->
            Tequila(
                tequilaEntity.id.toString(),
                tequilaEntity.description,
                tequilaEntity.imgURL,
                Brand(
                    tequilaEntity.brandEntity.id.toString(),
                    tequilaEntity.brandEntity.name,
                    tequilaEntity.brandEntity.description,
                    tequilaEntity.brandEntity.img
                ),
                Drink(
                    tequilaEntity.drinkEntity.id.toString(),
                    tequilaEntity.drinkEntity.name,
                    tequilaEntity.drinkEntity.temperature,
                    tequilaEntity.drinkEntity.isAlcoholic,
                    tequilaEntity.drinkEntity.millilitres,
                    Energy(
                        tequilaEntity.drinkEntity.energyEntity.id.toString(),
                        tequilaEntity.drinkEntity.energyEntity.energyContent,
                        tequilaEntity.drinkEntity.energyEntity.perServing,
                        tequilaEntity.drinkEntity.energyEntity.protein,
                        tequilaEntity.drinkEntity.energyEntity.totalFat,
                        tequilaEntity.drinkEntity.energyEntity.saturatedFat,
                        tequilaEntity.drinkEntity.energyEntity.transFat,
                        tequilaEntity.drinkEntity.energyEntity.carbohydrates,
                        tequilaEntity.drinkEntity.energyEntity.sugars,
                        tequilaEntity.drinkEntity.energyEntity.addedSugars,
                        tequilaEntity.drinkEntity.energyEntity.dietaryFiber,
                        tequilaEntity.drinkEntity.energyEntity.sodium,
                        tequilaEntity.drinkEntity.energyEntity.ingredients,
                    ),
                ),

                )
        }
    }


    /*Functions about Water Collection*/

    /**
     * This function insert a new user in the database
     */
    private val waterCollection = database.getCollection<WaterEntity>("water")

    /**
     * This function insert a new water object in the database
     *
     * @param water the waterEntity is the object with the information about it
     * @return a water
     */
    override suspend fun insertWater(water: Water): Boolean = withContext(Dispatchers.IO) {
        val waterEntity = WaterEntity(
            newId(),
            water.description,
            water.imgURL,
            BrandEntity(
                newId(),
                water.brand.name,
                water.brand.description,
                water.brand.img
            ),
            DrinkEntity(
                newId(),
                water.drink.name,
                water.drink.temperature,
                water.drink.isAlcoholic,
                water.drink.millilitres,
                EnergyEntity(
                    newId(),
                    water.drink.energy.energyContent,
                    water.drink.energy.perServing,
                    water.drink.energy.protein,
                    water.drink.energy.totalFat,
                    water.drink.energy.saturatedFat,
                    water.drink.energy.transFat,
                    water.drink.energy.carbohydrates,
                    water.drink.energy.sugars,
                    water.drink.energy.addedSugars,
                    water.drink.energy.dietaryFiber,
                    water.drink.energy.sodium,
                    water.drink.energy.ingredients,
                )
            )
        )
        try {
            waterCollection.insertOne(waterEntity)
            true
        } catch (_: Exception) {
            false
        }
    }

    /**
     * This function update a water document in the collection
     *
     * @param water the waterEntity is the object with the information about it
     * @return a boolean that is the result about the process in database
     */
    override suspend fun updateWater(water: Water): Boolean = withContext(Dispatchers.IO) {
        val waterEntity =
            WaterEntity(
                ObjectId(water.id).toId(),
                water.description,
                water.imgURL,
                BrandEntity(
                    newId(),
                    water.brand.name,
                    water.brand.description,
                    water.brand.img
                ),
                DrinkEntity(
                    ObjectId(water.drink.id).toId(),
                    water.drink.name,
                    water.drink.temperature,
                    water.drink.isAlcoholic,
                    water.drink.millilitres,
                    EnergyEntity(
                        ObjectId(water.drink.energy.id).toId(),
                        water.drink.energy.energyContent,
                        water.drink.energy.perServing,
                        water.drink.energy.protein,
                        water.drink.energy.totalFat,
                        water.drink.energy.saturatedFat,
                        water.drink.energy.transFat,
                        water.drink.energy.carbohydrates,
                        water.drink.energy.sugars,
                        water.drink.energy.addedSugars,
                        water.drink.energy.dietaryFiber,
                        water.drink.energy.sodium,
                        water.drink.energy.ingredients,
                    )
                )
            )

        val updateResult = waterCollection.replaceOne(
            WaterEntity::id eq waterEntity.id,
            waterEntity
        )

        updateResult.matchedCount > 0 && updateResult.modifiedCount > 0
    }

    /**
     * This function delete a water object in the database
     *
     * @param id the id is the identifier to find it
     * @return a boolean that is the result about the process in database
     */
    override suspend fun deleteWater(id: ObjectId): Boolean = withContext(Dispatchers.IO) {
        val deleteResult = waterCollection.deleteOneById(id)
        deleteResult.deletedCount == 1L
    }

    /**
     * This function gives all waters
     *
     * @return a list of waters
     */
    override suspend fun getWaters(): List<Water> = waterCollection.find().toList().let {
        it.map { waterEntity ->
            Water(
                waterEntity.id.toString(),
                waterEntity.description,
                waterEntity.imgURL,
                Brand(
                    waterEntity.brandEntity.id.toString(),
                    waterEntity.brandEntity.name,
                    waterEntity.brandEntity.description,
                    waterEntity.brandEntity.img
                ),
                Drink(
                    waterEntity.drinkEntity.id.toString(),
                    waterEntity.drinkEntity.name,
                    waterEntity.drinkEntity.temperature,
                    waterEntity.drinkEntity.isAlcoholic,
                    waterEntity.drinkEntity.millilitres,
                    Energy(
                        waterEntity.drinkEntity.energyEntity.id.toString(),
                        waterEntity.drinkEntity.energyEntity.energyContent,
                        waterEntity.drinkEntity.energyEntity.perServing,
                        waterEntity.drinkEntity.energyEntity.protein,
                        waterEntity.drinkEntity.energyEntity.totalFat,
                        waterEntity.drinkEntity.energyEntity.saturatedFat,
                        waterEntity.drinkEntity.energyEntity.transFat,
                        waterEntity.drinkEntity.energyEntity.carbohydrates,
                        waterEntity.drinkEntity.energyEntity.sugars,
                        waterEntity.drinkEntity.energyEntity.addedSugars,
                        waterEntity.drinkEntity.energyEntity.dietaryFiber,
                        waterEntity.drinkEntity.energyEntity.sodium,
                        waterEntity.drinkEntity.energyEntity.ingredients,
                    ),
                ),

                )
        }
    }


    /*Functions about Wine Collection*/

    /**
     * This function insert a new user in the database
     */
    private val wineCollection = database.getCollection<WineEntity>("wine")

    /**
     * This function insert a new wine in the database
     *
     * @param wine the wineEntity is the object with the information about it
     * @return a wine
     */
    override suspend fun insertWine(wine: Wine): Boolean = withContext(Dispatchers.IO) {
        val wineEntity = WineEntity(
            newId(),
            wine.description,
            wine.imgURL,
            BrandEntity(
                newId(),
                wine.brand.name,
                wine.brand.description,
                wine.brand.img
            ),
            DrinkEntity(
                newId(),
                wine.drink.name,
                wine.drink.temperature,
                wine.drink.isAlcoholic,
                wine.drink.millilitres,
                EnergyEntity(
                    newId(),
                    wine.drink.energy.energyContent,
                    wine.drink.energy.perServing,
                    wine.drink.energy.protein,
                    wine.drink.energy.totalFat,
                    wine.drink.energy.saturatedFat,
                    wine.drink.energy.transFat,
                    wine.drink.energy.carbohydrates,
                    wine.drink.energy.sugars,
                    wine.drink.energy.addedSugars,
                    wine.drink.energy.dietaryFiber,
                    wine.drink.energy.sodium,
                    wine.drink.energy.ingredients,
                )
            )
        )
        try {
            wineCollection.insertOne(wineEntity)
            true
        } catch (_: Exception) {
            false
        }
    }

    /**
     * This function update a wine document in the collection
     *
     * @param wine the wineEntity is the object with the information about it
     * @return a boolean that is the result about the process in database
     */
    override suspend fun updateWine(wine: Wine): Boolean = withContext(Dispatchers.IO) {
        val wineEntity =
            WineEntity(
                ObjectId(wine.id).toId(),
                wine.description,
                wine.imgURL,
                BrandEntity(
                    newId(),
                    wine.brand.name,
                    wine.brand.description,
                    wine.brand.img
                ),
                DrinkEntity(
                    ObjectId(wine.drink.id).toId(),
                    wine.drink.name,
                    wine.drink.temperature,
                    wine.drink.isAlcoholic,
                    wine.drink.millilitres,
                    EnergyEntity(
                        ObjectId(wine.drink.energy.id).toId(),
                        wine.drink.energy.energyContent,
                        wine.drink.energy.perServing,
                        wine.drink.energy.protein,
                        wine.drink.energy.totalFat,
                        wine.drink.energy.saturatedFat,
                        wine.drink.energy.transFat,
                        wine.drink.energy.carbohydrates,
                        wine.drink.energy.sugars,
                        wine.drink.energy.addedSugars,
                        wine.drink.energy.dietaryFiber,
                        wine.drink.energy.sodium,
                        wine.drink.energy.ingredients,
                    )
                )
            )

        val updateResult = wineCollection.replaceOne(
            WineEntity::id eq wineEntity.id,
            wineEntity
        )

        updateResult.matchedCount > 0 && updateResult.modifiedCount > 0
    }

    /**
     * This function delete a wine in the database
     *
     * @param id the id is the identifier to find it
     * @return a boolean that is the result about the process in database
     */
    override suspend fun deleteWine(id: ObjectId): Boolean = withContext(Dispatchers.IO) {
        val deleteResult = wineCollection.deleteOneById(id)
        deleteResult.deletedCount == 1L
    }

    /**
     * This function gives all wines
     *
     * @return a list of wines
     */
    override suspend fun getWines(): List<Wine> = wineCollection.find().toList().let {
        it.map { wineEntity ->
            Wine(
                wineEntity.id.toString(),
                wineEntity.description,
                wineEntity.imgURL,
                Brand(
                    wineEntity.brandEntity.id.toString(),
                    wineEntity.brandEntity.name,
                    wineEntity.brandEntity.description,
                    wineEntity.brandEntity.img
                ),
                Drink(
                    wineEntity.drinkEntity.id.toString(),
                    wineEntity.drinkEntity.name,
                    wineEntity.drinkEntity.temperature,
                    wineEntity.drinkEntity.isAlcoholic,
                    wineEntity.drinkEntity.millilitres,
                    Energy(
                        wineEntity.drinkEntity.energyEntity.id.toString(),
                        wineEntity.drinkEntity.energyEntity.energyContent,
                        wineEntity.drinkEntity.energyEntity.perServing,
                        wineEntity.drinkEntity.energyEntity.protein,
                        wineEntity.drinkEntity.energyEntity.totalFat,
                        wineEntity.drinkEntity.energyEntity.saturatedFat,
                        wineEntity.drinkEntity.energyEntity.transFat,
                        wineEntity.drinkEntity.energyEntity.carbohydrates,
                        wineEntity.drinkEntity.energyEntity.sugars,
                        wineEntity.drinkEntity.energyEntity.addedSugars,
                        wineEntity.drinkEntity.energyEntity.dietaryFiber,
                        wineEntity.drinkEntity.energyEntity.sodium,
                        wineEntity.drinkEntity.energyEntity.ingredients,
                    ),
                ),

                )
        }
    }


}