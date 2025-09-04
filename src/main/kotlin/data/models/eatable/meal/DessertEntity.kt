package com.cessup.data.models.eatable.meal

import com.cessup.data.models.eatable.BrandEntity
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id

/**
 * Represents a Beer drink.
 *
 * @property id unique identifier
 * @property mealEntity the meal contain information about this food
 * @property description the description is a text about the food
 *
 * @author
 *     Cessup
 * @since 1.0
 */
data class DessertEntity (
    @BsonId val id: Id<DessertEntity>,
    val description:String,
    val imgURL: String,
    val brandEntity: BrandEntity,
    val mealEntity: MealEntity,
)


