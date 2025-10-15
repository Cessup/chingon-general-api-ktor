package com.cessup.domain.models.orders

import com.cessup.domain.models.sales.Price
import org.bson.types.ObjectId

data class Order(
    val id: ObjectId = ObjectId.get(),
    val idUser: ObjectId = ObjectId.get(),
    val createdAt : Long,
    val address: Address,
    val items: List<Price>? = listOf(),
    val total: Double,
    val status: Status
)