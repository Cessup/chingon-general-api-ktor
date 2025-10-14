package com.cessup.data.models.sales

import org.bson.types.ObjectId

/**
 * Represents a Merchant.
 *
 * @property id unique identifier
 * @property name this is the name for the promotion
 */
data class MerchantEntity(
    val id: ObjectId = ObjectId(),
    val name:String
)