package com.cessup.domain.models.products

import org.bson.types.ObjectId

/**
 * Represents Brand.
 *
 * @property id unique identifier
 * @property name unique identifier
 * @property description unique identifier
 * @property img unique identifier
 *
 * @author
 *     Cessup
 * @since 1.0
 */
data class Brand (
    val id: ObjectId = ObjectId(),
    val name: String,
    val description:String,
    val img:String,
)