package com.cessup.data.models.eatable

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import org.litote.kmongo.Id

/**
 * Represents a Meal.
 *
 * @property id unique identifier
 * @property temperature the temperature is a ideal to this food
 * @property weight the temperature is a ideal to this food
 * @property category the category is the classification about it
 * @property subcategory the subcategory is the classification inside category about it so it is more specify
 * @property idEnergy unique identifier of energy
 * @property idProduct unique identifier of Product
 *
 * @author
 *     Cessup
 * @since 1.0
 */
data class MealEntity(
    @BsonId val id: Id<MealEntity>,
    val temperature: Double,
    val weight: Double,
    val category: String,
    val subcategory: String,
    val idEnergy: ObjectId,
    val idProduct: ObjectId
)