package com.cessup.data.entities

import com.cessup.domain.models.eatable.Drink
import com.cessup.domain.models.eatable.Energy
import com.cessup.domain.models.eatable.Meal
import org.bson.Document

fun Drink.toDocument(): Document = Document()
    .append("_id", id)
    .append("temperature", temperature)
    .append("isAlcoholic", isAlcoholic)
    .append("millilitres", millilitres)
    .append("category", category)
    .append("subcategory", subcategory)
    .append("idEnergy", idEnergy)
    .append("idProduct", idProduct)

fun Document.toDrink(): Drink =
    Drink(
        id = getObjectId("_id"),
        temperature = getDouble("temperature"),
        isAlcoholic = getBoolean("isAlcoholic"),
        millilitres = getInteger("millilitres"),
        category = getString("category"),
        subcategory = getString("subcategory"),
        idEnergy = getObjectId("idEnergy"),
        idProduct = getObjectId("idProduct")
    )
fun Meal.toDocument(): Document = Document()
    .append("_id", id)
    .append("temperature", temperature)
    .append("weight", weight)
    .append("category", category)
    .append("subcategory", subcategory)
    .append("idEnergy", idEnergy)
    .append("idProduct", idProduct)

fun Document.toMeal(): Meal =
    Meal(
        id = getObjectId("_id"),
        temperature = getDouble("temperature"),
        weight = getDouble("weight"),
        category = getString("category"),
        subcategory = getString("subcategory"),
        idEnergy = getObjectId("idEnergy"),
        idProduct = getObjectId("idProduct")
    )

fun Energy.toDocument(): Document = Document()
    .append("_id", id)
    .append("energyContent", energyContent)
    .append("perServing", perServing)
    .append("protein", protein)
    .append("totalFat", totalFat)
    .append("saturatedFat", saturatedFat)
    .append("transFat", transFat)
    .append("carbohydrates", carbohydrates)
    .append("sugars", sugars)
    .append("addedSugars", addedSugars)
    .append("dietaryFiber", dietaryFiber)
    .append("sodium", sodium)
    .append("ingredients", ingredients)

fun Document.toEnergy(): Energy =
    Energy(
        id = getObjectId("_id"),
        energyContent = getDouble("energyContent"),
        perServing = getDouble("perServing"),
        protein = getDouble("protein"),
        totalFat = getDouble("totalFat"),
        saturatedFat = getDouble("saturatedFat"),
        transFat = getDouble("transFat"),
        carbohydrates = getDouble("carbohydrates"),
        sugars = getDouble("sugars"),
        addedSugars = getDouble("addedSugars"),
        dietaryFiber = getDouble("dietaryFiber"),
        sodium = getDouble("sodium"),
        ingredients = getString("ingredients")
    )