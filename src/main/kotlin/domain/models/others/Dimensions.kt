package com.cessup.domain.models.others

import org.bson.types.ObjectId

/**
 * Represents a Dimensions.
 *
 * @property id unique identifier
 * @property width the width is a parameter of dimension.
 * @property height the height is a parameter of dimension.
 * @property depth the depth is a parameter of dimension.
 */
data class Dimensions(
    val id: ObjectId,
    val width: Double,
    val height: Double,
    val depth: Double,
)