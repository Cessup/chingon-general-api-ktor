package com.cessup.data.repositories

import com.cessup.data.models.eatable.BrandEntity
import com.cessup.data.models.eatable.EnergyEntity
import com.cessup.data.models.eatable.meal.BakedEntity
import com.cessup.data.models.eatable.meal.BakeryEntity
import com.cessup.data.models.eatable.meal.BreadEntity
import com.cessup.data.models.eatable.meal.DessertEntity
import com.cessup.data.models.eatable.meal.MainEntity
import com.cessup.data.models.eatable.meal.MealEntity
import com.cessup.data.models.eatable.meal.PastaEntity
import com.cessup.data.models.eatable.meal.RiceEntity
import com.cessup.data.models.eatable.meal.SaladEntity
import com.cessup.data.models.eatable.meal.SandwichEntity
import com.cessup.data.models.eatable.meal.SideEntity
import com.cessup.data.models.eatable.meal.SoupEntity
import com.cessup.data.models.eatable.meal.StarterEntity
import com.cessup.data.models.eatable.meal.WrapEntity
import com.cessup.domain.models.eatable.Brand
import com.cessup.domain.models.eatable.Energy
import com.cessup.domain.models.eatable.meal.Baked
import com.cessup.domain.models.eatable.meal.Bakery
import com.cessup.domain.models.eatable.meal.Bread
import com.cessup.domain.models.eatable.meal.Dessert
import com.cessup.domain.models.eatable.meal.Main
import com.cessup.domain.models.eatable.meal.Meal
import com.cessup.domain.models.eatable.meal.Pasta
import com.cessup.domain.models.eatable.meal.Rice
import com.cessup.domain.models.eatable.meal.Salad
import com.cessup.domain.models.eatable.meal.Sandwich
import com.cessup.domain.models.eatable.meal.Side
import com.cessup.domain.models.eatable.meal.Soup
import com.cessup.domain.models.eatable.meal.Starter
import com.cessup.domain.models.eatable.meal.Wrap
import com.cessup.domain.repositories.eatable.MealRepository
import com.google.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.bson.types.ObjectId
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
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
class MealRepositoryImpl@Inject constructor(database: CoroutineDatabase): MealRepository {
    /*Functions about Baked Collection*/

    /**
     * This function insert a new user in the database
     */
    private val bakedCollection = database.getCollection<BakedEntity>("baked")

    /**
     * This function insert a new Baked in the database
     *
     * @param baked the Baked is the object with the information about it
     * @return a Baked
     */
    override suspend fun insertBaked(baked: Baked): Boolean = withContext(Dispatchers.IO) {
        val bakedEntity = BakedEntity(
            newId(),
            baked.description,
            baked.imgURL,
            BrandEntity(
                newId(),
                baked.brand.name,
                baked.brand.description,
                baked.brand.img
            ),
            MealEntity(
                newId(),
                baked.meal.name,
                baked.meal.temperature,
                EnergyEntity(
                    newId(),
                    baked.meal.energy.energyContent,
                    baked.meal.energy.perServing,
                    baked.meal.energy.protein,
                    baked.meal.energy.totalFat,
                    baked.meal.energy.saturatedFat,
                    baked.meal.energy.transFat,
                    baked.meal.energy.carbohydrates,
                    baked.meal.energy.sugars,
                    baked.meal.energy.addedSugars,
                    baked.meal.energy.dietaryFiber,
                    baked.meal.energy.sodium,
                    baked.meal.energy.ingredients,
                )
            )
        )
        try {
            bakedCollection.insertOne(bakedEntity)
            true
        } catch (_: Exception) {
            false
        }
    }

    /**
     * This function update a Baked document in the collection
     *
     * @param baked the Baked is the object with the information about it
     * @return a boolean that is the result about the process in database
     */
    override suspend fun updateBaked(baked: Baked): Boolean = withContext(Dispatchers.IO) {
        val bakedEntity = BakedEntity(
            newId(),
            baked.description,
            baked.imgURL,
            BrandEntity(
                newId(),
                baked.brand.name,
                baked.brand.description,
                baked.brand.img
            ),
            MealEntity(
                newId(),
                baked.meal.name,
                baked.meal.temperature,
                EnergyEntity(
                    newId(),
                    baked.meal.energy.energyContent,
                    baked.meal.energy.perServing,
                    baked.meal.energy.protein,
                    baked.meal.energy.totalFat,
                    baked.meal.energy.saturatedFat,
                    baked.meal.energy.transFat,
                    baked.meal.energy.carbohydrates,
                    baked.meal.energy.sugars,
                    baked.meal.energy.addedSugars,
                    baked.meal.energy.dietaryFiber,
                    baked.meal.energy.sodium,
                    baked.meal.energy.ingredients,
                )
            )
        )

        val updateResult = bakedCollection.replaceOne(
            BakedEntity::id eq bakedEntity.id,
            bakedEntity
        )

        updateResult.matchedCount > 0 && updateResult.modifiedCount > 0
    }

    /**
     * This function delete a Baked in the database
     *
     * @param id the id is the identifier to find it
     * @return a boolean that is the result about the process in database
     */
    override suspend fun deleteBaked(id: ObjectId): Boolean = withContext(Dispatchers.IO) {
        val deleteResult = bakedCollection.deleteOneById(id)
        deleteResult.deletedCount == 1L
    }

    /**
     * This function gives all Baked
     *
     * @return a list of Baked
     */
    override suspend fun getBakeds(): List<Baked> = bakedCollection.find().toList().let {
        it.map { bakedEntity ->
            Baked(bakedEntity.id.toString(),
                bakedEntity.description,
                bakedEntity.imgURL,
                Brand(
                    bakedEntity.brandEntity.id.toString(),
                    bakedEntity.brandEntity.name,
                    bakedEntity.brandEntity.description,
                    bakedEntity.brandEntity.img,
                ),
                Meal(
                    bakedEntity.mealEntity.id.toString(),
                    bakedEntity.mealEntity.name,
                    bakedEntity.mealEntity.temperature,
                    Energy(
                        bakedEntity.mealEntity.energyEntity.id.toString(),
                        bakedEntity.mealEntity.energyEntity.energyContent,
                        bakedEntity.mealEntity.energyEntity.perServing,
                        bakedEntity.mealEntity.energyEntity.protein,
                        bakedEntity.mealEntity.energyEntity.totalFat,
                        bakedEntity.mealEntity.energyEntity.saturatedFat,
                        bakedEntity.mealEntity.energyEntity.transFat,
                        bakedEntity.mealEntity.energyEntity.carbohydrates,
                        bakedEntity.mealEntity.energyEntity.sugars,
                        bakedEntity.mealEntity.energyEntity.addedSugars,
                        bakedEntity.mealEntity.energyEntity.dietaryFiber,
                        bakedEntity.mealEntity.energyEntity.sodium,
                        bakedEntity.mealEntity.energyEntity.ingredients,
                    )
                )
            )
        }
    }



    /*Functions about Bakery Collection*/

    /**
     * This function insert a new user in the database
     */
    private val bakeryCollection = database.getCollection<BakeryEntity>("bakery")

    /**
     * This function insert a new Bakery in the database
     *
     * @param bakery the Bakery is the object with the information about it
     * @return a Bakery
     */
    override suspend fun insertBakery(bakery: Bakery): Boolean = withContext(Dispatchers.IO) {
        val bakeryEntity = BakeryEntity(
            newId(),
            bakery.description,
            bakery.imgURL,
            BrandEntity(
                newId(),
                bakery.brand.name,
                bakery.brand.description,
                bakery.brand.img
            ),
            MealEntity(
                newId(),
                bakery.meal.name,
                bakery.meal.temperature,
                EnergyEntity(
                    newId(),
                    bakery.meal.energy.energyContent,
                    bakery.meal.energy.perServing,
                    bakery.meal.energy.protein,
                    bakery.meal.energy.totalFat,
                    bakery.meal.energy.saturatedFat,
                    bakery.meal.energy.transFat,
                    bakery.meal.energy.carbohydrates,
                    bakery.meal.energy.sugars,
                    bakery.meal.energy.addedSugars,
                    bakery.meal.energy.dietaryFiber,
                    bakery.meal.energy.sodium,
                    bakery.meal.energy.ingredients,
                )
            )
        )
        try {
            bakeryCollection.insertOne(bakeryEntity)
            true
        } catch (_: Exception) {
            false
        }
    }

    /**
     * This function update a Bakery document in the collection
     *
     * @param bakery the Bakery is the object with the information about it
     * @return a boolean that is the result about the process in database
     */
    override suspend fun updateBakery(bakery: Bakery): Boolean = withContext(Dispatchers.IO) {
        val bakeryEntity = BakeryEntity(
            newId(),
            bakery.description,
            bakery.imgURL,
            BrandEntity(
                newId(),
                bakery.brand.name,
                bakery.brand.description,
                bakery.brand.img
            ),
            MealEntity(
                newId(),
                bakery.meal.name,
                bakery.meal.temperature,
                EnergyEntity(
                    newId(),
                    bakery.meal.energy.energyContent,
                    bakery.meal.energy.perServing,
                    bakery.meal.energy.protein,
                    bakery.meal.energy.totalFat,
                    bakery.meal.energy.saturatedFat,
                    bakery.meal.energy.transFat,
                    bakery.meal.energy.carbohydrates,
                    bakery.meal.energy.sugars,
                    bakery.meal.energy.addedSugars,
                    bakery.meal.energy.dietaryFiber,
                    bakery.meal.energy.sodium,
                    bakery.meal.energy.ingredients,
                )
            )
        )

        val updateResult = bakeryCollection.replaceOne(
            BakeryEntity::id eq bakeryEntity.id,
            bakeryEntity
        )

        updateResult.matchedCount > 0 && updateResult.modifiedCount > 0
    }

    /**
     * This function delete a Bakery in the database
     *
     * @param id the id is the identifier to find it
     * @return a boolean that is the result about the process in database
     */
    override suspend fun deleteBakery(id: ObjectId): Boolean = withContext(Dispatchers.IO) {
        val deleteResult = bakeryCollection.deleteOneById(id)
        deleteResult.deletedCount == 1L
    }

    /**
     * This function gives all Bakery
     *
     * @return a list of Bakery
     */
    override suspend fun getBakeries(): List<Bakery> = bakeryCollection.find().toList().let {
        it.map { bakeryEntity ->
            Bakery(bakeryEntity.id.toString(),
                bakeryEntity.description,
                bakeryEntity.imgURL,
                Brand(
                    bakeryEntity.brandEntity.id.toString(),
                    bakeryEntity.brandEntity.name,
                    bakeryEntity.brandEntity.description,
                    bakeryEntity.brandEntity.img,
                ),
                Meal(
                    bakeryEntity.mealEntity.id.toString(),
                    bakeryEntity.mealEntity.name,
                    bakeryEntity.mealEntity.temperature,
                    Energy(
                        bakeryEntity.mealEntity.energyEntity.id.toString(),
                        bakeryEntity.mealEntity.energyEntity.energyContent,
                        bakeryEntity.mealEntity.energyEntity.perServing,
                        bakeryEntity.mealEntity.energyEntity.protein,
                        bakeryEntity.mealEntity.energyEntity.totalFat,
                        bakeryEntity.mealEntity.energyEntity.saturatedFat,
                        bakeryEntity.mealEntity.energyEntity.transFat,
                        bakeryEntity.mealEntity.energyEntity.carbohydrates,
                        bakeryEntity.mealEntity.energyEntity.sugars,
                        bakeryEntity.mealEntity.energyEntity.addedSugars,
                        bakeryEntity.mealEntity.energyEntity.dietaryFiber,
                        bakeryEntity.mealEntity.energyEntity.sodium,
                        bakeryEntity.mealEntity.energyEntity.ingredients,
                    )
                )
            )
        }
    }



    /*Functions about Bread Collection*/

    /**
     * This function insert a new user in the database
     */
    private val breadCollection = database.getCollection<BreadEntity>("bread")

    /**
     * This function insert a new Bread object in the database
     *
     * @param bread the Bread is the object with the information about it
     * @return a Bread
     */
    override suspend fun insertBread(bread: Bread): Boolean = withContext(Dispatchers.IO) {
        val breadEntity = BreadEntity(
            newId(),
            bread.description,
            bread.imgURL,
            BrandEntity(
                newId(),
                bread.brand.name,
                bread.brand.description,
                bread.brand.img
            ),
            MealEntity(
                newId(),
                bread.meal.name,
                bread.meal.temperature,
                EnergyEntity(
                    newId(),
                    bread.meal.energy.energyContent,
                    bread.meal.energy.perServing,
                    bread.meal.energy.protein,
                    bread.meal.energy.totalFat,
                    bread.meal.energy.saturatedFat,
                    bread.meal.energy.transFat,
                    bread.meal.energy.carbohydrates,
                    bread.meal.energy.sugars,
                    bread.meal.energy.addedSugars,
                    bread.meal.energy.dietaryFiber,
                    bread.meal.energy.sodium,
                    bread.meal.energy.ingredients,
                )
            )
        )
        try {
            breadCollection.insertOne(breadEntity)
            true
        } catch (_: Exception) {
            false
        }
    }

    /**
     * This function update a Bread document in the collection
     *
     * @param bread the Bread is the object with the information about it
     * @return a boolean that is the result about the process in database
     */
    override suspend fun updateBread(bread: Bread): Boolean = withContext(Dispatchers.IO) {
        val breadEntity = BreadEntity(
            newId(),
            bread.description,
            bread.imgURL,
            BrandEntity(
                newId(),
                bread.brand.name,
                bread.brand.description,
                bread.brand.img
            ),
            MealEntity(
                newId(),
                bread.meal.name,
                bread.meal.temperature,
                EnergyEntity(
                    newId(),
                    bread.meal.energy.energyContent,
                    bread.meal.energy.perServing,
                    bread.meal.energy.protein,
                    bread.meal.energy.totalFat,
                    bread.meal.energy.saturatedFat,
                    bread.meal.energy.transFat,
                    bread.meal.energy.carbohydrates,
                    bread.meal.energy.sugars,
                    bread.meal.energy.addedSugars,
                    bread.meal.energy.dietaryFiber,
                    bread.meal.energy.sodium,
                    bread.meal.energy.ingredients,
                )
            )
        )

        val updateResult = breadCollection.replaceOne(
            BreadEntity::id eq breadEntity.id,
            breadEntity
        )

        updateResult.matchedCount > 0 && updateResult.modifiedCount > 0
    }

    /**
     * This function delete a Bread object in the database
     *
     * @param id the id is the identifier to find it
     * @return a boolean that is the result about the process in database
     */
    override suspend fun deleteBread(id: ObjectId): Boolean = withContext(Dispatchers.IO) {
        val deleteResult = breadCollection.deleteOneById(id)
        deleteResult.deletedCount == 1L
    }

    /**
     * This function gives all Bread
     *
     * @return a list of Bread
     */
    override suspend fun getBreads(): List<Bread> = breadCollection.find().toList().let {
        it.map { breadEntity ->
            Bread(breadEntity.id.toString(),
                breadEntity.description,
                breadEntity.imgURL,
                Brand(
                    breadEntity.brandEntity.id.toString(),
                    breadEntity.brandEntity.name,
                    breadEntity.brandEntity.description,
                    breadEntity.brandEntity.img,
                ),
                Meal(
                    breadEntity.mealEntity.id.toString(),
                    breadEntity.mealEntity.name,
                    breadEntity.mealEntity.temperature,
                    Energy(
                        breadEntity.mealEntity.energyEntity.id.toString(),
                        breadEntity.mealEntity.energyEntity.energyContent,
                        breadEntity.mealEntity.energyEntity.perServing,
                        breadEntity.mealEntity.energyEntity.protein,
                        breadEntity.mealEntity.energyEntity.totalFat,
                        breadEntity.mealEntity.energyEntity.saturatedFat,
                        breadEntity.mealEntity.energyEntity.transFat,
                        breadEntity.mealEntity.energyEntity.carbohydrates,
                        breadEntity.mealEntity.energyEntity.sugars,
                        breadEntity.mealEntity.energyEntity.addedSugars,
                        breadEntity.mealEntity.energyEntity.dietaryFiber,
                        breadEntity.mealEntity.energyEntity.sodium,
                        breadEntity.mealEntity.energyEntity.ingredients,
                    )
                )
            )
        }
    }

    /*Functions about Dessert Collection*/

    /**
     * This function insert a new user in the database
     */
    private val dessertCollection = database.getCollection<DessertEntity>("dessert")

    /**
     * This function insert a new Dessert in the database
     *
     * @param dessert the Dessert is the object with the information about it
     * @return a Dessert
     */
    override suspend fun insertDessert(dessert: Dessert): Boolean = withContext(Dispatchers.IO) {
        val dessertEntity = DessertEntity(
            newId(),
            dessert.description,
            dessert.imgURL,
            BrandEntity(
                newId(),
                dessert.brand.name,
                dessert.brand.description,
                dessert.brand.img
            ),
            MealEntity(
                newId(),
                dessert.meal.name,
                dessert.meal.temperature,
                EnergyEntity(
                    newId(),
                    dessert.meal.energy.energyContent,
                    dessert.meal.energy.perServing,
                    dessert.meal.energy.protein,
                    dessert.meal.energy.totalFat,
                    dessert.meal.energy.saturatedFat,
                    dessert.meal.energy.transFat,
                    dessert.meal.energy.carbohydrates,
                    dessert.meal.energy.sugars,
                    dessert.meal.energy.addedSugars,
                    dessert.meal.energy.dietaryFiber,
                    dessert.meal.energy.sodium,
                    dessert.meal.energy.ingredients,
                )
            )
        )
        try {
            dessertCollection.insertOne(dessertEntity)
            true
        } catch (_: Exception) {
            false
        }
    }

    /**
     * This function update a Dessert document in the collection
     *
     * @param dessert the Dessert is the object with the information about it
     * @return a boolean that is the result about the process in database
     */
    override suspend fun updateDessert(dessert: Dessert): Boolean = withContext(Dispatchers.IO) {
        val dessertEntity = DessertEntity(
            newId(),
            dessert.description,
            dessert.imgURL,
            BrandEntity(
                newId(),
                dessert.brand.name,
                dessert.brand.description,
                dessert.brand.img
            ),
            MealEntity(
                newId(),
                dessert.meal.name,
                dessert.meal.temperature,
                EnergyEntity(
                    newId(),
                    dessert.meal.energy.energyContent,
                    dessert.meal.energy.perServing,
                    dessert.meal.energy.protein,
                    dessert.meal.energy.totalFat,
                    dessert.meal.energy.saturatedFat,
                    dessert.meal.energy.transFat,
                    dessert.meal.energy.carbohydrates,
                    dessert.meal.energy.sugars,
                    dessert.meal.energy.addedSugars,
                    dessert.meal.energy.dietaryFiber,
                    dessert.meal.energy.sodium,
                    dessert.meal.energy.ingredients,
                )
            )
        )

        val updateResult = dessertCollection.replaceOne(
            DessertEntity::id eq dessertEntity.id,
            dessertEntity
        )

        updateResult.matchedCount > 0 && updateResult.modifiedCount > 0
    }

    /**
     * This function delete a Dessert in the database
     *
     * @param id the id is the identifier to find it
     * @return a boolean that is the result about the process in database
     */
    override suspend fun deleteDessert(id: ObjectId): Boolean = withContext(Dispatchers.IO) {
        val deleteResult = dessertCollection.deleteOneById(id)
        deleteResult.deletedCount == 1L
    }

    /**
     * This function gives all Dessert
     *
     * @return a list of Dessert
     */
    override suspend fun getDesserts(): List<Dessert> = dessertCollection.find().toList().let {
        it.map { dessertEntity ->
            Dessert(dessertEntity.id.toString(),
                dessertEntity.description,
                dessertEntity.imgURL,
                Brand(
                    dessertEntity.brandEntity.id.toString(),
                    dessertEntity.brandEntity.name,
                    dessertEntity.brandEntity.description,
                    dessertEntity.brandEntity.img,
                ),
                Meal(
                    dessertEntity.mealEntity.id.toString(),
                    dessertEntity.mealEntity.name,
                    dessertEntity.mealEntity.temperature,
                    Energy(
                        dessertEntity.mealEntity.energyEntity.id.toString(),
                        dessertEntity.mealEntity.energyEntity.energyContent,
                        dessertEntity.mealEntity.energyEntity.perServing,
                        dessertEntity.mealEntity.energyEntity.protein,
                        dessertEntity.mealEntity.energyEntity.totalFat,
                        dessertEntity.mealEntity.energyEntity.saturatedFat,
                        dessertEntity.mealEntity.energyEntity.transFat,
                        dessertEntity.mealEntity.energyEntity.carbohydrates,
                        dessertEntity.mealEntity.energyEntity.sugars,
                        dessertEntity.mealEntity.energyEntity.addedSugars,
                        dessertEntity.mealEntity.energyEntity.dietaryFiber,
                        dessertEntity.mealEntity.energyEntity.sodium,
                        dessertEntity.mealEntity.energyEntity.ingredients,
                    )
                )
            )
        }
    }


    /*Functions about Main Collection*/

    /**
     * This function insert a new user in the database
     */
    private val mainCollection = database.getCollection<MainEntity>("main")

    /**
     * This function insert a new Main in the database
     *
     * @param main the Main is the object with the information about it
     * @return a Main
     */
    override suspend fun insertMain(main: Main): Boolean = withContext(Dispatchers.IO) {
        val mainEntity = MainEntity(
            newId(),
            main.description,
            main.imgURL,
            BrandEntity(
                newId(),
                main.brand.name,
                main.brand.description,
                main.brand.img
            ),
            MealEntity(
                newId(),
                main.meal.name,
                main.meal.temperature,
                EnergyEntity(
                    newId(),
                    main.meal.energy.energyContent,
                    main.meal.energy.perServing,
                    main.meal.energy.protein,
                    main.meal.energy.totalFat,
                    main.meal.energy.saturatedFat,
                    main.meal.energy.transFat,
                    main.meal.energy.carbohydrates,
                    main.meal.energy.sugars,
                    main.meal.energy.addedSugars,
                    main.meal.energy.dietaryFiber,
                    main.meal.energy.sodium,
                    main.meal.energy.ingredients,
                )
            )
        )
        try {
            mainCollection.insertOne(mainEntity)
            true
        } catch (_: Exception) {
            false
        }
    }

    /**
     * This function update a Main document in the collection
     *
     * @param main the Main is the object with the information about it
     * @return a boolean that is the result about the process in database
     */
    override suspend fun updateMain(main: Main): Boolean = withContext(Dispatchers.IO) {
        val mainEntity = MainEntity(
            newId(),
            main.description,
            main.imgURL,
            BrandEntity(
                newId(),
                main.brand.name,
                main.brand.description,
                main.brand.img
            ),
            MealEntity(
                newId(),
                main.meal.name,
                main.meal.temperature,
                EnergyEntity(
                    newId(),
                    main.meal.energy.energyContent,
                    main.meal.energy.perServing,
                    main.meal.energy.protein,
                    main.meal.energy.totalFat,
                    main.meal.energy.saturatedFat,
                    main.meal.energy.transFat,
                    main.meal.energy.carbohydrates,
                    main.meal.energy.sugars,
                    main.meal.energy.addedSugars,
                    main.meal.energy.dietaryFiber,
                    main.meal.energy.sodium,
                    main.meal.energy.ingredients,
                )
            )
        )

        val updateResult = mainCollection.replaceOne(
            MainEntity::id eq mainEntity.id,
            mainEntity
        )

        updateResult.matchedCount > 0 && updateResult.modifiedCount > 0
    }

    /**
     * This function delete a Main in the database
     *
     * @param id the id is the identifier to find it
     * @return a boolean that is the result about the process in database
     */
    override suspend fun deleteMain(id: ObjectId): Boolean = withContext(Dispatchers.IO) {
        val deleteResult = mainCollection.deleteOneById(id)
        deleteResult.deletedCount == 1L
    }

    /**
     * This function gives all Main
     *
     * @return a list of Main
     */
    override suspend fun getMains(): List<Main> = mainCollection.find().toList().let {
        it.map { mainEntity ->
            Main(mainEntity.id.toString(),
                mainEntity.description,
                mainEntity.imgURL,
                Brand(
                    mainEntity.brandEntity.id.toString(),
                    mainEntity.brandEntity.name,
                    mainEntity.brandEntity.description,
                    mainEntity.brandEntity.img,
                ),
                Meal(
                    mainEntity.mealEntity.id.toString(),
                    mainEntity.mealEntity.name,
                    mainEntity.mealEntity.temperature,
                    Energy(
                        mainEntity.mealEntity.energyEntity.id.toString(),
                        mainEntity.mealEntity.energyEntity.energyContent,
                        mainEntity.mealEntity.energyEntity.perServing,
                        mainEntity.mealEntity.energyEntity.protein,
                        mainEntity.mealEntity.energyEntity.totalFat,
                        mainEntity.mealEntity.energyEntity.saturatedFat,
                        mainEntity.mealEntity.energyEntity.transFat,
                        mainEntity.mealEntity.energyEntity.carbohydrates,
                        mainEntity.mealEntity.energyEntity.sugars,
                        mainEntity.mealEntity.energyEntity.addedSugars,
                        mainEntity.mealEntity.energyEntity.dietaryFiber,
                        mainEntity.mealEntity.energyEntity.sodium,
                        mainEntity.mealEntity.energyEntity.ingredients,
                    )
                )
            )
        }
    }


    /*Functions about Pasta Collection*/

    /**
     * This function insert a new user in the database
     */
    private val pastaCollection = database.getCollection<PastaEntity>("pasta")

    /**
     * This function insert a new Pasta object in the database
     *
     * @param pasta the Pasta is the object with the information about it
     * @return a Pasta
     */
    override suspend fun insertPasta(pasta: Pasta): Boolean = withContext(Dispatchers.IO) {
        val pastaEntity = PastaEntity(
            newId(),
            pasta.description,
            pasta.imgURL,
            BrandEntity(
                newId(),
                pasta.brand.name,
                pasta.brand.description,
                pasta.brand.img
            ),
            MealEntity(
                newId(),
                pasta.meal.name,
                pasta.meal.temperature,
                EnergyEntity(
                    newId(),
                    pasta.meal.energy.energyContent,
                    pasta.meal.energy.perServing,
                    pasta.meal.energy.protein,
                    pasta.meal.energy.totalFat,
                    pasta.meal.energy.saturatedFat,
                    pasta.meal.energy.transFat,
                    pasta.meal.energy.carbohydrates,
                    pasta.meal.energy.sugars,
                    pasta.meal.energy.addedSugars,
                    pasta.meal.energy.dietaryFiber,
                    pasta.meal.energy.sodium,
                    pasta.meal.energy.ingredients,
                )
            )
        )
        try {
            pastaCollection.insertOne(pastaEntity)
            true
        } catch (_: Exception) {
            false
        }
    }

    /**
     * This function update a Pasta document in the collection
     *
     * @param pasta the Pasta is the object with the information about it
     * @return a boolean that is the result about the process in database
     */
    override suspend fun updatePasta(pasta: Pasta): Boolean = withContext(Dispatchers.IO) {
        val pastaEntity = PastaEntity(
            newId(),
            pasta.description,
            pasta.imgURL,
            BrandEntity(
                newId(),
                pasta.brand.name,
                pasta.brand.description,
                pasta.brand.img
            ),
            MealEntity(
                newId(),
                pasta.meal.name,
                pasta.meal.temperature,
                EnergyEntity(
                    newId(),
                    pasta.meal.energy.energyContent,
                    pasta.meal.energy.perServing,
                    pasta.meal.energy.protein,
                    pasta.meal.energy.totalFat,
                    pasta.meal.energy.saturatedFat,
                    pasta.meal.energy.transFat,
                    pasta.meal.energy.carbohydrates,
                    pasta.meal.energy.sugars,
                    pasta.meal.energy.addedSugars,
                    pasta.meal.energy.dietaryFiber,
                    pasta.meal.energy.sodium,
                    pasta.meal.energy.ingredients,
                )
            )
        )

        val updateResult = pastaCollection.replaceOne(
            PastaEntity::id eq pastaEntity.id,
            pastaEntity
        )

        updateResult.matchedCount > 0 && updateResult.modifiedCount > 0
    }

    /**
     * This function delete a Pasta object in the database
     *
     * @param id the id is the identifier to find it
     * @return a boolean that is the result about the process in database
     */
    override suspend fun deletePasta(id: ObjectId): Boolean = withContext(Dispatchers.IO) {
        val deleteResult = pastaCollection.deleteOneById(id)
        deleteResult.deletedCount == 1L
    }

    /**
     * This function gives all Pasta
     *
     * @return a list of Pasta
     */
    override suspend fun getPastas(): List<Pasta> = pastaCollection.find().toList().let {
        it.map { pastaEntity ->
            Pasta(pastaEntity.id.toString(),
                pastaEntity.description,
                pastaEntity.imgURL,
                Brand(
                    pastaEntity.brandEntity.id.toString(),
                    pastaEntity.brandEntity.name,
                    pastaEntity.brandEntity.description,
                    pastaEntity.brandEntity.img,
                ),
                Meal(
                    pastaEntity.mealEntity.id.toString(),
                    pastaEntity.mealEntity.name,
                    pastaEntity.mealEntity.temperature,
                    Energy(
                        pastaEntity.mealEntity.energyEntity.id.toString(),
                        pastaEntity.mealEntity.energyEntity.energyContent,
                        pastaEntity.mealEntity.energyEntity.perServing,
                        pastaEntity.mealEntity.energyEntity.protein,
                        pastaEntity.mealEntity.energyEntity.totalFat,
                        pastaEntity.mealEntity.energyEntity.saturatedFat,
                        pastaEntity.mealEntity.energyEntity.transFat,
                        pastaEntity.mealEntity.energyEntity.carbohydrates,
                        pastaEntity.mealEntity.energyEntity.sugars,
                        pastaEntity.mealEntity.energyEntity.addedSugars,
                        pastaEntity.mealEntity.energyEntity.dietaryFiber,
                        pastaEntity.mealEntity.energyEntity.sodium,
                        pastaEntity.mealEntity.energyEntity.ingredients,
                    )
                )
            )
        }
    }


    /*Functions about Rice Collection*/

    /**
     * This function insert a new user in the database
     */
    private val riceCollection = database.getCollection<RiceEntity>("rice")

    /**
     * This function insert a new Rice in the database
     *
     * @param rice the Rice is the object with the information about it
     * @return a Rice
     */
    override suspend fun insertRice(rice: Rice): Boolean = withContext(Dispatchers.IO) {
        val riceEntity = RiceEntity(
            newId(),
            rice.description,
            rice.imgURL,
            BrandEntity(
                newId(),
                rice.brand.name,
                rice.brand.description,
                rice.brand.img
            ),
            MealEntity(
                newId(),
                rice.meal.name,
                rice.meal.temperature,
                EnergyEntity(
                    newId(),
                    rice.meal.energy.energyContent,
                    rice.meal.energy.perServing,
                    rice.meal.energy.protein,
                    rice.meal.energy.totalFat,
                    rice.meal.energy.saturatedFat,
                    rice.meal.energy.transFat,
                    rice.meal.energy.carbohydrates,
                    rice.meal.energy.sugars,
                    rice.meal.energy.addedSugars,
                    rice.meal.energy.dietaryFiber,
                    rice.meal.energy.sodium,
                    rice.meal.energy.ingredients,
                )
            )
        )
        try {
            riceCollection.insertOne(riceEntity)
            true
        } catch (_: Exception) {
            false
        }
    }

    /**
     * This function update a Rice document in the collection
     *
     * @param rice the Rice is the object with the information about it
     * @return a boolean that is the result about the process in database
     */
    override suspend fun updateRice(rice: Rice): Boolean = withContext(Dispatchers.IO) {
        val riceEntity = RiceEntity(
            newId(),
            rice.description,
            rice.imgURL,
            BrandEntity(
                newId(),
                rice.brand.name,
                rice.brand.description,
                rice.brand.img
            ),
            MealEntity(
                newId(),
                rice.meal.name,
                rice.meal.temperature,
                EnergyEntity(
                    newId(),
                    rice.meal.energy.energyContent,
                    rice.meal.energy.perServing,
                    rice.meal.energy.protein,
                    rice.meal.energy.totalFat,
                    rice.meal.energy.saturatedFat,
                    rice.meal.energy.transFat,
                    rice.meal.energy.carbohydrates,
                    rice.meal.energy.sugars,
                    rice.meal.energy.addedSugars,
                    rice.meal.energy.dietaryFiber,
                    rice.meal.energy.sodium,
                    rice.meal.energy.ingredients,
                )
            )
        )

        val updateResult = riceCollection.replaceOne(
            RiceEntity::id eq riceEntity.id,
            riceEntity
        )

        updateResult.matchedCount > 0 && updateResult.modifiedCount > 0
    }

    /**
     * This function delete a Rice in the database
     *
     * @param id the id is the identifier to find it
     * @return a boolean that is the result about the process in database
     */
    override suspend fun deleteRice(id: ObjectId): Boolean = withContext(Dispatchers.IO) {
        val deleteResult = riceCollection.deleteOneById(id)
        deleteResult.deletedCount == 1L
    }

    /**
     * This function gives all Rice
     *
     * @return a list of Rice
     */
    override suspend fun getRices(): List<Rice> = riceCollection.find().toList().let {
        it.map { riceEntity ->
            Rice(riceEntity.id.toString(),
                riceEntity.description,
                riceEntity.imgURL,
                Brand(
                    riceEntity.brandEntity.id.toString(),
                    riceEntity.brandEntity.name,
                    riceEntity.brandEntity.description,
                    riceEntity.brandEntity.img,
                ),
                Meal(
                    riceEntity.mealEntity.id.toString(),
                    riceEntity.mealEntity.name,
                    riceEntity.mealEntity.temperature,
                    Energy(
                        riceEntity.mealEntity.energyEntity.id.toString(),
                        riceEntity.mealEntity.energyEntity.energyContent,
                        riceEntity.mealEntity.energyEntity.perServing,
                        riceEntity.mealEntity.energyEntity.protein,
                        riceEntity.mealEntity.energyEntity.totalFat,
                        riceEntity.mealEntity.energyEntity.saturatedFat,
                        riceEntity.mealEntity.energyEntity.transFat,
                        riceEntity.mealEntity.energyEntity.carbohydrates,
                        riceEntity.mealEntity.energyEntity.sugars,
                        riceEntity.mealEntity.energyEntity.addedSugars,
                        riceEntity.mealEntity.energyEntity.dietaryFiber,
                        riceEntity.mealEntity.energyEntity.sodium,
                        riceEntity.mealEntity.energyEntity.ingredients,
                    )
                )
            )
        }
    }

    /*Functions about Salad Collection*/

    /**
     * This function insert a new user in the database
     */
    private val saladCollection = database.getCollection<SaladEntity>("salad")

    /**
     * This function insert a new Salad in the database
     *
     * @param salad the Salad is the object with the information about it
     * @return a Salad
     */
    override suspend fun insertSalad(salad: Salad): Boolean = withContext(Dispatchers.IO) {
        val saladEntity = SaladEntity(
            newId(),
            salad.description,
            salad.imgURL,
            BrandEntity(
                newId(),
                salad.brand.name,
                salad.brand.description,
                salad.brand.img
            ),
            MealEntity(
                newId(),
                salad.meal.name,
                salad.meal.temperature,
                EnergyEntity(
                    newId(),
                    salad.meal.energy.energyContent,
                    salad.meal.energy.perServing,
                    salad.meal.energy.protein,
                    salad.meal.energy.totalFat,
                    salad.meal.energy.saturatedFat,
                    salad.meal.energy.transFat,
                    salad.meal.energy.carbohydrates,
                    salad.meal.energy.sugars,
                    salad.meal.energy.addedSugars,
                    salad.meal.energy.dietaryFiber,
                    salad.meal.energy.sodium,
                    salad.meal.energy.ingredients,
                )
            )
        )
        try {
            saladCollection.insertOne(saladEntity)
            true
        } catch (_: Exception) {
            false
        }
    }

    /**
     * This function update a Salad document in the collection
     *
     * @param salad the Salad is the object with the information about it
     * @return a boolean that is the result about the process in database
     */
    override suspend fun updateSalad(salad: Salad): Boolean = withContext(Dispatchers.IO) {
        val saladEntity = SaladEntity(
            newId(),
            salad.description,
            salad.imgURL,
            BrandEntity(
                newId(),
                salad.brand.name,
                salad.brand.description,
                salad.brand.img
            ),
            MealEntity(
                newId(),
                salad.meal.name,
                salad.meal.temperature,
                EnergyEntity(
                    newId(),
                    salad.meal.energy.energyContent,
                    salad.meal.energy.perServing,
                    salad.meal.energy.protein,
                    salad.meal.energy.totalFat,
                    salad.meal.energy.saturatedFat,
                    salad.meal.energy.transFat,
                    salad.meal.energy.carbohydrates,
                    salad.meal.energy.sugars,
                    salad.meal.energy.addedSugars,
                    salad.meal.energy.dietaryFiber,
                    salad.meal.energy.sodium,
                    salad.meal.energy.ingredients,
                )
            )
        )

        val updateResult = saladCollection.replaceOne(
            SaladEntity::id eq saladEntity.id,
            saladEntity
        )

        updateResult.matchedCount > 0 && updateResult.modifiedCount > 0
    }

    /**
     * This function delete a Salad in the database
     *
     * @param id the id is the identifier to find it
     * @return a boolean that is the result about the process in database
     */
    override suspend fun deleteSalad(id: ObjectId): Boolean = withContext(Dispatchers.IO) {
        val deleteResult = saladCollection.deleteOneById(id)
        deleteResult.deletedCount == 1L
    }

    /**
     * This function gives all Salad
     *
     * @return a list of Salad
     */
    override suspend fun getSalads(): List<Salad> = saladCollection.find().toList().let {
        it.map { saladEntity ->
            Salad(saladEntity.id.toString(),
                saladEntity.description,
                saladEntity.imgURL,
                Brand(
                    saladEntity.brandEntity.id.toString(),
                    saladEntity.brandEntity.name,
                    saladEntity.brandEntity.description,
                    saladEntity.brandEntity.img,
                ),
                Meal(
                    saladEntity.mealEntity.id.toString(),
                    saladEntity.mealEntity.name,
                    saladEntity.mealEntity.temperature,
                    Energy(
                        saladEntity.mealEntity.energyEntity.id.toString(),
                        saladEntity.mealEntity.energyEntity.energyContent,
                        saladEntity.mealEntity.energyEntity.perServing,
                        saladEntity.mealEntity.energyEntity.protein,
                        saladEntity.mealEntity.energyEntity.totalFat,
                        saladEntity.mealEntity.energyEntity.saturatedFat,
                        saladEntity.mealEntity.energyEntity.transFat,
                        saladEntity.mealEntity.energyEntity.carbohydrates,
                        saladEntity.mealEntity.energyEntity.sugars,
                        saladEntity.mealEntity.energyEntity.addedSugars,
                        saladEntity.mealEntity.energyEntity.dietaryFiber,
                        saladEntity.mealEntity.energyEntity.sodium,
                        saladEntity.mealEntity.energyEntity.ingredients,
                    )
                )
            )
        }
    }

    /*Functions about Sandwich Collection*/

    /**
     * This function insert a new user in the database
     */
    private val sandwichCollection = database.getCollection<SandwichEntity>("sandwich")

    /**
     * This function insert a new Sandwich in the database
     *
     * @param sandwich the Sandwich is the object with the information about it
     * @return a Sandwich
     */
    override suspend fun insertSandwich(sandwich: Sandwich): Boolean = withContext(Dispatchers.IO) {
        val sandwichEntity = SandwichEntity(
            newId(),
            sandwich.description,
            sandwich.imgURL,
            BrandEntity(
                newId(),
                sandwich.brand.name,
                sandwich.brand.description,
                sandwich.brand.img
            ),
            MealEntity(
                newId(),
                sandwich.meal.name,
                sandwich.meal.temperature,
                EnergyEntity(
                    newId(),
                    sandwich.meal.energy.energyContent,
                    sandwich.meal.energy.perServing,
                    sandwich.meal.energy.protein,
                    sandwich.meal.energy.totalFat,
                    sandwich.meal.energy.saturatedFat,
                    sandwich.meal.energy.transFat,
                    sandwich.meal.energy.carbohydrates,
                    sandwich.meal.energy.sugars,
                    sandwich.meal.energy.addedSugars,
                    sandwich.meal.energy.dietaryFiber,
                    sandwich.meal.energy.sodium,
                    sandwich.meal.energy.ingredients,
                )
            )
        )
        try {
            sandwichCollection.insertOne(sandwichEntity)
            true
        } catch (_: Exception) {
            false
        }
    }

    /**
     * This function update a Sandwich document in the collection
     *
     * @param sandwich the Sandwich is the object with the information about it
     * @return a boolean that is the result about the process in database
     */
    override suspend fun updateSandwich(sandwich: Sandwich): Boolean = withContext(Dispatchers.IO) {
        val sandwichEntity = SandwichEntity(
            newId(),
            sandwich.description,
            sandwich.imgURL,
            BrandEntity(
                newId(),
                sandwich.brand.name,
                sandwich.brand.description,
                sandwich.brand.img
            ),
            MealEntity(
                newId(),
                sandwich.meal.name,
                sandwich.meal.temperature,
                EnergyEntity(
                    newId(),
                    sandwich.meal.energy.energyContent,
                    sandwich.meal.energy.perServing,
                    sandwich.meal.energy.protein,
                    sandwich.meal.energy.totalFat,
                    sandwich.meal.energy.saturatedFat,
                    sandwich.meal.energy.transFat,
                    sandwich.meal.energy.carbohydrates,
                    sandwich.meal.energy.sugars,
                    sandwich.meal.energy.addedSugars,
                    sandwich.meal.energy.dietaryFiber,
                    sandwich.meal.energy.sodium,
                    sandwich.meal.energy.ingredients,
                )
            )
        )

        val updateResult = sandwichCollection.replaceOne(
            SandwichEntity::id eq sandwichEntity.id,
            sandwichEntity
        )

        updateResult.matchedCount > 0 && updateResult.modifiedCount > 0
    }

    /**
     * This function delete a Sandwich in the database
     *
     * @param id the id is the identifier to find it
     * @return a boolean that is the result about the process in database
     */
    override suspend fun deleteSandwich(id: ObjectId): Boolean = withContext(Dispatchers.IO) {
        val deleteResult = sandwichCollection.deleteOneById(id)
        deleteResult.deletedCount == 1L
    }

    /**
     * This function gives all Sandwich
     *
     * @return a list of Sandwich
     */
    override suspend fun getSandwiches(): List<Sandwich> = sandwichCollection.find().toList().let {
        it.map { sandwichEntity ->
            Sandwich(sandwichEntity.id.toString(),
                sandwichEntity.description,
                sandwichEntity.imgURL,
                Brand(
                    sandwichEntity.brandEntity.id.toString(),
                    sandwichEntity.brandEntity.name,
                    sandwichEntity.brandEntity.description,
                    sandwichEntity.brandEntity.img,
                ),
                Meal(
                    sandwichEntity.mealEntity.id.toString(),
                    sandwichEntity.mealEntity.name,
                    sandwichEntity.mealEntity.temperature,
                    Energy(
                        sandwichEntity.mealEntity.energyEntity.id.toString(),
                        sandwichEntity.mealEntity.energyEntity.energyContent,
                        sandwichEntity.mealEntity.energyEntity.perServing,
                        sandwichEntity.mealEntity.energyEntity.protein,
                        sandwichEntity.mealEntity.energyEntity.totalFat,
                        sandwichEntity.mealEntity.energyEntity.saturatedFat,
                        sandwichEntity.mealEntity.energyEntity.transFat,
                        sandwichEntity.mealEntity.energyEntity.carbohydrates,
                        sandwichEntity.mealEntity.energyEntity.sugars,
                        sandwichEntity.mealEntity.energyEntity.addedSugars,
                        sandwichEntity.mealEntity.energyEntity.dietaryFiber,
                        sandwichEntity.mealEntity.energyEntity.sodium,
                        sandwichEntity.mealEntity.energyEntity.ingredients,
                    )
                )
            )
        }
    }


    /*Functions about Side Collection*/

    /**
     * This function insert a new user in the database
     */
    private val sideCollection = database.getCollection<SideEntity>("side")

    /**
     * This function insert a new Side in the database
     *
     * @param side the Side is the object with the information about it
     * @return a Side
     */
    override suspend fun insertSide(side: Side): Boolean = withContext(Dispatchers.IO) {
        val sideEntity = SideEntity(
            newId(),
            side.description,
            side.imgURL,
            BrandEntity(
                newId(),
                side.brand.name,
                side.brand.description,
                side.brand.img
            ),
            MealEntity(
                newId(),
                side.meal.name,
                side.meal.temperature,
                EnergyEntity(
                    newId(),
                    side.meal.energy.energyContent,
                    side.meal.energy.perServing,
                    side.meal.energy.protein,
                    side.meal.energy.totalFat,
                    side.meal.energy.saturatedFat,
                    side.meal.energy.transFat,
                    side.meal.energy.carbohydrates,
                    side.meal.energy.sugars,
                    side.meal.energy.addedSugars,
                    side.meal.energy.dietaryFiber,
                    side.meal.energy.sodium,
                    side.meal.energy.ingredients,
                )
            )
        )
        try {
            sideCollection.insertOne(sideEntity)
            true
        } catch (_: Exception) {
            false
        }
    }

    /**
     * This function update a Side document in the collection
     *
     * @param side the Side is the object with the information about it
     * @return a boolean that is the result about the process in database
     */
    override suspend fun updateSide(side: Side): Boolean = withContext(Dispatchers.IO) {
        val sideEntity = SideEntity(
            newId(),
            side.description,
            side.imgURL,
            BrandEntity(
                newId(),
                side.brand.name,
                side.brand.description,
                side.brand.img
            ),
            MealEntity(
                newId(),
                side.meal.name,
                side.meal.temperature,
                EnergyEntity(
                    newId(),
                    side.meal.energy.energyContent,
                    side.meal.energy.perServing,
                    side.meal.energy.protein,
                    side.meal.energy.totalFat,
                    side.meal.energy.saturatedFat,
                    side.meal.energy.transFat,
                    side.meal.energy.carbohydrates,
                    side.meal.energy.sugars,
                    side.meal.energy.addedSugars,
                    side.meal.energy.dietaryFiber,
                    side.meal.energy.sodium,
                    side.meal.energy.ingredients,
                )
            )
        )

        val updateResult = sideCollection.replaceOne(
            SideEntity::id eq sideEntity.id,
            sideEntity
        )

        updateResult.matchedCount > 0 && updateResult.modifiedCount > 0
    }

    /**
     * This function delete a Side in the database
     *
     * @param id the id is the identifier to find it
     * @return a boolean that is the result about the process in database
     */
    override suspend fun deleteSide(id: ObjectId): Boolean = withContext(Dispatchers.IO) {
        val deleteResult = sideCollection.deleteOneById(id)
        deleteResult.deletedCount == 1L
    }

    /**
     * This function gives all Side
     *
     * @return a list of Side
     */
    override suspend fun getSides(): List<Side> = sideCollection.find().toList().let {
        it.map { sideEntity ->
            Side(sideEntity.id.toString(),
                sideEntity.description,
                sideEntity.imgURL,
                Brand(
                    sideEntity.brandEntity.id.toString(),
                    sideEntity.brandEntity.name,
                    sideEntity.brandEntity.description,
                    sideEntity.brandEntity.img,
                ),
                Meal(
                    sideEntity.mealEntity.id.toString(),
                    sideEntity.mealEntity.name,
                    sideEntity.mealEntity.temperature,
                    Energy(
                        sideEntity.mealEntity.energyEntity.id.toString(),
                        sideEntity.mealEntity.energyEntity.energyContent,
                        sideEntity.mealEntity.energyEntity.perServing,
                        sideEntity.mealEntity.energyEntity.protein,
                        sideEntity.mealEntity.energyEntity.totalFat,
                        sideEntity.mealEntity.energyEntity.saturatedFat,
                        sideEntity.mealEntity.energyEntity.transFat,
                        sideEntity.mealEntity.energyEntity.carbohydrates,
                        sideEntity.mealEntity.energyEntity.sugars,
                        sideEntity.mealEntity.energyEntity.addedSugars,
                        sideEntity.mealEntity.energyEntity.dietaryFiber,
                        sideEntity.mealEntity.energyEntity.sodium,
                        sideEntity.mealEntity.energyEntity.ingredients,
                    )
                )
            )
        }
    }


    /*Functions about Soup Collection*/

    /**
     * This function insert a new user in the database
     */
    private val soupCollection = database.getCollection<SoupEntity>("soup")

    /**
     * This function insert a new Soup in the database
     *
     * @param soup the Soup is the object with the information about it
     * @return a Soup
     */
    override suspend fun insertSoup(soup: Soup): Boolean = withContext(Dispatchers.IO) {
        val soupEntity = SoupEntity(
            newId(),
            soup.description,
            soup.imgURL,
            BrandEntity(
                newId(),
                soup.brand.name,
                soup.brand.description,
                soup.brand.img
            ),
            MealEntity(
                newId(),
                soup.meal.name,
                soup.meal.temperature,
                EnergyEntity(
                    newId(),
                    soup.meal.energy.energyContent,
                    soup.meal.energy.perServing,
                    soup.meal.energy.protein,
                    soup.meal.energy.totalFat,
                    soup.meal.energy.saturatedFat,
                    soup.meal.energy.transFat,
                    soup.meal.energy.carbohydrates,
                    soup.meal.energy.sugars,
                    soup.meal.energy.addedSugars,
                    soup.meal.energy.dietaryFiber,
                    soup.meal.energy.sodium,
                    soup.meal.energy.ingredients,
                )
            )
        )
        try {
            soupCollection.insertOne(soupEntity)
            true
        } catch (_: Exception) {
            false
        }
    }

    /**
     * This function update a Soup document in the collection
     *
     * @param soup the Soup is the object with the information about it
     * @return a boolean that is the result about the process in database
     */
    override suspend fun updateSoup(soup: Soup): Boolean = withContext(Dispatchers.IO) {
        val soupEntity = SoupEntity(
            newId(),
            soup.description,
            soup.imgURL,
            BrandEntity(
                newId(),
                soup.brand.name,
                soup.brand.description,
                soup.brand.img
            ),
            MealEntity(
                newId(),
                soup.meal.name,
                soup.meal.temperature,
                EnergyEntity(
                    newId(),
                    soup.meal.energy.energyContent,
                    soup.meal.energy.perServing,
                    soup.meal.energy.protein,
                    soup.meal.energy.totalFat,
                    soup.meal.energy.saturatedFat,
                    soup.meal.energy.transFat,
                    soup.meal.energy.carbohydrates,
                    soup.meal.energy.sugars,
                    soup.meal.energy.addedSugars,
                    soup.meal.energy.dietaryFiber,
                    soup.meal.energy.sodium,
                    soup.meal.energy.ingredients,
                )
            )
        )

        val updateResult = soupCollection.replaceOne(
            SoupEntity::id eq soupEntity.id,
            soupEntity
        )

        updateResult.matchedCount > 0 && updateResult.modifiedCount > 0
    }

    /**
     * This function delete a Soup in the database
     *
     * @param id the id is the identifier to find it
     * @return a boolean that is the result about the process in database
     */
    override suspend fun deleteSoup(id: ObjectId): Boolean = withContext(Dispatchers.IO) {
        val deleteResult = soupCollection.deleteOneById(id)
        deleteResult.deletedCount == 1L
    }

    /**
     * This function gives all Soup
     *
     * @return a list of Soup
     */
    override suspend fun getSoups(): List<Soup> = soupCollection.find().toList().let {
        it.map { soupEntity ->
            Soup(soupEntity.id.toString(),
                soupEntity.description,
                soupEntity.imgURL,
                Brand(
                    soupEntity.brandEntity.id.toString(),
                    soupEntity.brandEntity.name,
                    soupEntity.brandEntity.description,
                    soupEntity.brandEntity.img,
                ),
                Meal(
                    soupEntity.mealEntity.id.toString(),
                    soupEntity.mealEntity.name,
                    soupEntity.mealEntity.temperature,
                    Energy(
                        soupEntity.mealEntity.energyEntity.id.toString(),
                        soupEntity.mealEntity.energyEntity.energyContent,
                        soupEntity.mealEntity.energyEntity.perServing,
                        soupEntity.mealEntity.energyEntity.protein,
                        soupEntity.mealEntity.energyEntity.totalFat,
                        soupEntity.mealEntity.energyEntity.saturatedFat,
                        soupEntity.mealEntity.energyEntity.transFat,
                        soupEntity.mealEntity.energyEntity.carbohydrates,
                        soupEntity.mealEntity.energyEntity.sugars,
                        soupEntity.mealEntity.energyEntity.addedSugars,
                        soupEntity.mealEntity.energyEntity.dietaryFiber,
                        soupEntity.mealEntity.energyEntity.sodium,
                        soupEntity.mealEntity.energyEntity.ingredients,
                    )
                )
            )
        }
    }

    /*Functions about Starter Collection*/

    /**
     * This function insert a new user in the database
     */
    private val starterCollection = database.getCollection<StarterEntity>("starter")

    /**
     * This function insert a new Starter in the database
     *
     * @param starter the Starter is the object with the information about it
     * @return a Starter
     */
    override suspend fun insertStarter(starter: Starter): Boolean = withContext(Dispatchers.IO) {
        val starterEntity = StarterEntity(
            newId(),
            starter.description,
            starter.imgURL,
            BrandEntity(
                newId(),
                starter.brand.name,
                starter.brand.description,
                starter.brand.img
            ),
            MealEntity(
                newId(),
                starter.meal.name,
                starter.meal.temperature,
                EnergyEntity(
                    newId(),
                    starter.meal.energy.energyContent,
                    starter.meal.energy.perServing,
                    starter.meal.energy.protein,
                    starter.meal.energy.totalFat,
                    starter.meal.energy.saturatedFat,
                    starter.meal.energy.transFat,
                    starter.meal.energy.carbohydrates,
                    starter.meal.energy.sugars,
                    starter.meal.energy.addedSugars,
                    starter.meal.energy.dietaryFiber,
                    starter.meal.energy.sodium,
                    starter.meal.energy.ingredients,
                )
            )
        )
        try {
            starterCollection.insertOne(starterEntity)
            true
        } catch (_: Exception) {
            false
        }
    }

    /**
     * This function update a Starter document in the collection
     *
     * @param starter the Starter is the object with the information about it
     * @return a boolean that is the result about the process in database
     */
    override suspend fun updateStarter(starter: Starter): Boolean = withContext(Dispatchers.IO) {
        val starterEntity = StarterEntity(
            newId(),
            starter.description,
            starter.imgURL,
            BrandEntity(
                newId(),
                starter.brand.name,
                starter.brand.description,
                starter.brand.img
            ),
            MealEntity(
                newId(),
                starter.meal.name,
                starter.meal.temperature,
                EnergyEntity(
                    newId(),
                    starter.meal.energy.energyContent,
                    starter.meal.energy.perServing,
                    starter.meal.energy.protein,
                    starter.meal.energy.totalFat,
                    starter.meal.energy.saturatedFat,
                    starter.meal.energy.transFat,
                    starter.meal.energy.carbohydrates,
                    starter.meal.energy.sugars,
                    starter.meal.energy.addedSugars,
                    starter.meal.energy.dietaryFiber,
                    starter.meal.energy.sodium,
                    starter.meal.energy.ingredients,
                )
            )
        )

        val updateResult = starterCollection.replaceOne(
            StarterEntity::id eq starterEntity.id,
            starterEntity
        )

        updateResult.matchedCount > 0 && updateResult.modifiedCount > 0
    }

    /**
     * This function delete a Starter in the database
     *
     * @param id the id is the identifier to find it
     * @return a boolean that is the result about the process in database
     */
    override suspend fun deleteStarter(id: ObjectId): Boolean = withContext(Dispatchers.IO) {
        val deleteResult = starterCollection.deleteOneById(id)
        deleteResult.deletedCount == 1L
    }

    /**
     * This function gives all Starter
     *
     * @return a list of Starter
     */
    override suspend fun getStarters(): List<Starter> = starterCollection.find().toList().let {
        it.map { starterEntity ->
            Starter(starterEntity.id.toString(),
                starterEntity.description,
                starterEntity.imgURL,
                Brand(
                    starterEntity.brandEntity.id.toString(),
                    starterEntity.brandEntity.name,
                    starterEntity.brandEntity.description,
                    starterEntity.brandEntity.img,
                ),
                Meal(
                    starterEntity.mealEntity.id.toString(),
                    starterEntity.mealEntity.name,
                    starterEntity.mealEntity.temperature,
                    Energy(
                        starterEntity.mealEntity.energyEntity.id.toString(),
                        starterEntity.mealEntity.energyEntity.energyContent,
                        starterEntity.mealEntity.energyEntity.perServing,
                        starterEntity.mealEntity.energyEntity.protein,
                        starterEntity.mealEntity.energyEntity.totalFat,
                        starterEntity.mealEntity.energyEntity.saturatedFat,
                        starterEntity.mealEntity.energyEntity.transFat,
                        starterEntity.mealEntity.energyEntity.carbohydrates,
                        starterEntity.mealEntity.energyEntity.sugars,
                        starterEntity.mealEntity.energyEntity.addedSugars,
                        starterEntity.mealEntity.energyEntity.dietaryFiber,
                        starterEntity.mealEntity.energyEntity.sodium,
                        starterEntity.mealEntity.energyEntity.ingredients,
                    )
                )
            )
        }
    }


    /*Functions about Wrap Collection*/

    /**
     * This function insert a new user in the database
     */
    private val wrapCollection = database.getCollection<WrapEntity>("wrap")

    /**
     * This function insert a new Wrap in the database
     *
     * @param wrap the Wrap is the object with the information about it
     * @return a Wrap
     */
    override suspend fun insertWrap(wrap: Wrap): Boolean = withContext(Dispatchers.IO) {
        val wrapEntity = WrapEntity(
            newId(),
            wrap.description,
            wrap.imgURL,
            BrandEntity(
                newId(),
                wrap.brand.name,
                wrap.brand.description,
                wrap.brand.img
            ),
            MealEntity(
                newId(),
                wrap.meal.name,
                wrap.meal.temperature,
                EnergyEntity(
                    newId(),
                    wrap.meal.energy.energyContent,
                    wrap.meal.energy.perServing,
                    wrap.meal.energy.protein,
                    wrap.meal.energy.totalFat,
                    wrap.meal.energy.saturatedFat,
                    wrap.meal.energy.transFat,
                    wrap.meal.energy.carbohydrates,
                    wrap.meal.energy.sugars,
                    wrap.meal.energy.addedSugars,
                    wrap.meal.energy.dietaryFiber,
                    wrap.meal.energy.sodium,
                    wrap.meal.energy.ingredients,
                )
            )
        )
        try {
            wrapCollection.insertOne(wrapEntity)
            true
        } catch (_: Exception) {
            false
        }
    }

    /**
     * This function update a Wrap document in the collection
     *
     * @param wrap the Wrap is the object with the information about it
     * @return a boolean that is the result about the process in database
     */
    override suspend fun updateWrap(wrap: Wrap): Boolean = withContext(Dispatchers.IO) {
        val wrapEntity = WrapEntity(
            newId(),
            wrap.description,
            wrap.imgURL,
            BrandEntity(
                newId(),
                wrap.brand.name,
                wrap.brand.description,
                wrap.brand.img
            ),
            MealEntity(
                newId(),
                wrap.meal.name,
                wrap.meal.temperature,
                EnergyEntity(
                    newId(),
                    wrap.meal.energy.energyContent,
                    wrap.meal.energy.perServing,
                    wrap.meal.energy.protein,
                    wrap.meal.energy.totalFat,
                    wrap.meal.energy.saturatedFat,
                    wrap.meal.energy.transFat,
                    wrap.meal.energy.carbohydrates,
                    wrap.meal.energy.sugars,
                    wrap.meal.energy.addedSugars,
                    wrap.meal.energy.dietaryFiber,
                    wrap.meal.energy.sodium,
                    wrap.meal.energy.ingredients,
                )
            )
        )

        val updateResult = wrapCollection.replaceOne(
            WrapEntity::id eq wrapEntity.id,
            wrapEntity
        )

        updateResult.matchedCount > 0 && updateResult.modifiedCount > 0
    }

    /**
     * This function delete a Wrap in the database
     *
     * @param id the id is the identifier to find it
     * @return a boolean that is the result about the process in database
     */
    override suspend fun deleteWrap(id: ObjectId): Boolean = withContext(Dispatchers.IO) {
        val deleteResult = wrapCollection.deleteOneById(id)
        deleteResult.deletedCount == 1L
    }

    /**
     * This function gives all Wrap
     *
     * @return a list of Wrap
     */
    override suspend fun getWraps(): List<Wrap> = wrapCollection.find().toList().let {
        it.map { wrapEntity ->
            Wrap(wrapEntity.id.toString(),
                wrapEntity.description,
                wrapEntity.imgURL,
                Brand(
                    wrapEntity.brandEntity.id.toString(),
                    wrapEntity.brandEntity.name,
                    wrapEntity.brandEntity.description,
                    wrapEntity.brandEntity.img,
                ),
                Meal(
                    wrapEntity.mealEntity.id.toString(),
                    wrapEntity.mealEntity.name,
                    wrapEntity.mealEntity.temperature,
                    Energy(
                        wrapEntity.mealEntity.energyEntity.id.toString(),
                        wrapEntity.mealEntity.energyEntity.energyContent,
                        wrapEntity.mealEntity.energyEntity.perServing,
                        wrapEntity.mealEntity.energyEntity.protein,
                        wrapEntity.mealEntity.energyEntity.totalFat,
                        wrapEntity.mealEntity.energyEntity.saturatedFat,
                        wrapEntity.mealEntity.energyEntity.transFat,
                        wrapEntity.mealEntity.energyEntity.carbohydrates,
                        wrapEntity.mealEntity.energyEntity.sugars,
                        wrapEntity.mealEntity.energyEntity.addedSugars,
                        wrapEntity.mealEntity.energyEntity.dietaryFiber,
                        wrapEntity.mealEntity.energyEntity.sodium,
                        wrapEntity.mealEntity.energyEntity.ingredients,
                    )
                )
            )
        }
    }

}