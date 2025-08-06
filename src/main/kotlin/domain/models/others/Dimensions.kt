package com.cessup.domain.models.others

/**
 * Represents a Dimensions.
 *
 * @property id unique identifier
 * @property width the width is a parameter of dimension.
 * @property height the height is a parameter of dimension.
 * @property depth the depth is a parameter of dimension.
 */
data class Dimensions(
    val id: String,
    val width: Double,
    val height: Double,
    val depth: Double,
)