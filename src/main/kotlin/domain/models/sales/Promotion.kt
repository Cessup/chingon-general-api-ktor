package com.cessup.domain.models.sales

import org.bson.types.ObjectId

/**
 * Represents a Promotion.
 *
 * @property id unique identifier
 * @property name this is the name for the promotion
 * @property details that is more details for the promotion
 * @property discount the discount for the sale
 * @property expiration that is the date to expire that
 * @property merchant the merchant is the owner of the promotion
 */
data class Promotion (
    val id: ObjectId = ObjectId(),
    val name: String,
    val details : String,
    val discount: Int,
    val expiration: Long,
    val merchant: ObjectId = ObjectId()
)