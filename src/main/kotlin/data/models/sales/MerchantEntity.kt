package com.cessup.data.models.sales

import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id

data class MerchantEntity(
    @BsonId val id: Id<MerchantEntity>,
    val name:String,
)