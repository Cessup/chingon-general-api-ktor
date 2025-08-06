package com.cessup.data.models.others

import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id

/**
 * Represents a Dimensions.
 *
 * @property id unique identifier
 * @property width the width is a parameter of dimension.
 * @property height the height is a parameter of dimension.
 * @property depth the depth is a parameter of dimension.
 */
data class DimensionsEntity(
    @BsonId val id: Id<DimensionsEntity>,
    val width: Double,
    val height: Double,
    val depth: Double,
)