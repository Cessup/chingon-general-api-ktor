package com.cessup.data.models.sales

import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id

data class PriceEntity (
    @BsonId val id: Id<PriceEntity>,
    val mount: Int,
    val currency: String,
    val merchant: MerchantEntity,
    val item: Object
)