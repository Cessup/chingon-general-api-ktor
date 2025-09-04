package com.cessup.data.models.eatable

import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id

/**
 * Represents Eatable abstraction of eat.
 *
 * @property id unique identifier
 * @property energyContent unique identifier
 * @property perServing unique identifier
 * @property protein unique identifier
 * @property totalFat unique identifier
 * @property saturatedFat unique identifier
 * @property transFat unique identifier
 * @property carbohydrates unique identifier
 * @property sugars unique identifier
 * @property addedSugars unique identifier
 * @property dietaryFiber unique identifier
 * @property sodium unique identifier
 * @property ingredients unique identifier
 *
 * @author
 *     Cessup
 * @since 1.0
 */
data class EnergyEntity(
    @BsonId val id: Id<EnergyEntity>,
     val energyContent: Double,
     val perServing: Double,
     val protein: Double,
     val totalFat: Double,
     val saturatedFat: Double,
     val transFat: Double,
     val carbohydrates: Double,
     val sugars: Double,
     val addedSugars: Double,
     val dietaryFiber: Double,
     var sodium: Double,
     val ingredients: String,
)