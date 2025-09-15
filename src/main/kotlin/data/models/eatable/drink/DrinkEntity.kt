package com.cessup.data.models.eatable.drink

import com.cessup.data.models.eatable.EnergyEntity
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id

/**
 * Represents a Product.
 *
 * @property id unique identifier
 * @property name the code for identify every product
 * @property temperature the price is about value of product
 * @property isAlcoholic the currency is a references of prices
 * @property millilitres the millilitres is the count per piece
 * @property energyEntity the currency is a references of prices
 *
 * @author
 *     Cessup
 * @since 1.0
 */
data class DrinkEntity(
    @BsonId val id: Id<DrinkEntity>,
     val name: String,
     val temperature: Double,
     val isAlcoholic: Boolean,
     val millilitres: Int,
     val energyEntity: EnergyEntity,
)