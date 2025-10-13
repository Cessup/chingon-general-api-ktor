package com.cessup.domain.models.sales

import org.bson.types.ObjectId

/**
 * Represents a Merchant.
 *
 * @property id unique identifier
 * @property name this is the name for the promotion
 */
data class Merchant(
    val id: ObjectId = ObjectId(),
    val name:String
)