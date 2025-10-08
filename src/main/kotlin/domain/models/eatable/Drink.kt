package com.cessup.domain.models.eatable

import org.bson.types.ObjectId

/**
 * Represents a Drink.
 *
 * @property id unique identifier
 * @property temperature the price is about value of product
 * @property isAlcoholic the currency is a references of prices
 * @property millilitres the millilitres is the count per piece
 * @property category the category is the classification about it
 * @property subcategory the subcategory is the classification inside category about it so it is more specify
 * @property idEnergy unique identifier of energy
 * @property idProduct unique identifier of Product
 *
 * @author
 *     Cessup
 * @since 1.0
 */
data class Drink(
    val id: ObjectId,
    val temperature: Double,
    val isAlcoholic: Boolean,
    val millilitres: Int,
    val category: String,
    val subcategory: String,
    val idEnergy: ObjectId,
    val idProduct: ObjectId
)