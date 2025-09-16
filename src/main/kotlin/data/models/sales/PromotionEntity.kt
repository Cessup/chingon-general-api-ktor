package com.cessup.data.models.sales

import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id

data class PromotionEntity (
    @BsonId val id: Id<PromotionEntity>,
    val name: String,
    val details : String,
    val discount: Int,
    val merchant: MerchantEntity
)