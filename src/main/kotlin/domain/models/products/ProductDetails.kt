package com.cessup.domain.models.products

import com.cessup.domain.models.others.Color
import com.cessup.domain.models.others.Dimensions

/**
 * Represents a product details.
 *
 * @property id unique identifier
 * @property description the lastname of the user
 * @property version the birthday of the user
 * @property dimensions the gender of the user
 * @property color the birthday of the user
 * @property tags the birthday of the user
 */
data class ProductDetails(
    val id: String,
    val description: String,
    val version: String,
    val dimensions: Dimensions,
    val color: Color,
    val tags: List<String>
)