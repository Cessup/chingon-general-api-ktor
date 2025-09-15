package com.cessup.data.models.eatable.drink

import com.cessup.data.models.eatable.BrandEntity
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id

/**
 * Represents a Wine drink.
 *
 * @property id unique identifier
 * @property description the description is a text about the drink
 * @property imgURL the imgURL is a text about the drink
 * @property brandEntity the brand is the information about brand of the drink
 * @property drinkEntity the drink is all information about this
 *
 * @author
 *     Cessup
 * @since 1.0
 */
data class WineEntity(
    @BsonId val id: Id<WineEntity>,
    val description:String,
    val imgURL: String,
    val brandEntity: BrandEntity,
    val drinkEntity: DrinkEntity,
)