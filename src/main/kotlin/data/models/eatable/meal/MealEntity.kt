package com.cessup.data.models.eatable.meal

import com.cessup.data.models.eatable.EnergyEntity
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id

/**
 * Represents a Beer drink.
 *
 * @property id unique identifier
 * @property name the name of the meal
 * @property temperature the temperature is a ideal to this food
 * @property energyEntity the energy is the information about the food
 *
 * @author
 *     Cessup
 * @since 1.0
 */
data class MealEntity(
    @BsonId val id: Id<MealEntity>,
     val name: String,
     val temperature: Double,
     val energyEntity: EnergyEntity
)