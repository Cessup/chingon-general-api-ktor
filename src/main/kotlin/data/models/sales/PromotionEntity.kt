package com.cessup.data.models.sales

import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id
import java.sql.Date

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
data class PromotionEntity (
    @BsonId val id: Id<PromotionEntity>,
    val name: String,
    val details : String,
    val discount: Int,
    val expiration: Date,
    val merchant: MerchantEntity
)