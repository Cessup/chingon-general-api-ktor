package com.cessup.domain.models.eatable

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
data class Meal(
     val id: String,
     val temperature: Double,
     val weight: Double,
     val category: String,
     val subcategory: String,
     val idEnergy: String,
     val idProduct: String
)