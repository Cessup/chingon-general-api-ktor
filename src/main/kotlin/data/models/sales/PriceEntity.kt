package com.cessup.data.models.sales

import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id

/**
 * Represents a Price.
 *
 * @property id unique identifier
 * @property mount this is a number to the price
 * @property currency it is the currency from meke it
 * @property merchant Create new scratch file from selection
 * @property item that is the object for the price
 */
data class PriceEntity (
    @BsonId val id: Id<PriceEntity>,
    val mount: Int,
    val currency: String,
    val merchant: MerchantEntity,
    val item: Object
)