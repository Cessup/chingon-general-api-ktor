package com.cessup.data.models.eatable.drink

import com.cessup.data.models.eatable.BrandEntity
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id

/**
 * Represents a Cocktail drink.
 *
 * @property id unique identifier
 * @property description the description is a text about the drink
 * @property brandEntity the brand of drink
 * @property drinkEntity the drink is all information about this
 *
 * @author
 *     Cessup
 * @since 1.0
 */
data class CocktailEntity(
    @BsonId val id: Id<CocktailEntity>,
    val description:String,
    val imgURL: String,
    val brandEntity: BrandEntity,
    val drinkEntity: DrinkEntity,
)