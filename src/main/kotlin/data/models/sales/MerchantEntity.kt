package com.cessup.data.models.sales

import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id

/**
 * Represents a Merchant.
 *
 * @property id unique identifier
 * @property name this is the name for the promotion
 */
data class MerchantEntity(
    @BsonId val id: Id<MerchantEntity>,
    val name:String,
)