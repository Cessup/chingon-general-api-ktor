package com.cessup.data.models.sales

import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id

/**
 * Represents a Sale.
 *
 * @property id unique identifier
 * @property mount this is a number to the price
 * @property currency it is the currency from meke it
 * @property merchant Create new scratch file from selection
 */
data class SaleEntity (
    @BsonId val id: Id<SaleEntity>,
    val mount: Int,
    val currency: String,
    val merchant: MerchantEntity
)