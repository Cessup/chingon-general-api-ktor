package com.cessup.domain.models.orders

import org.bson.types.ObjectId

data class Status(
    val id: ObjectId = ObjectId.get(),
    val code: Int,
    val name: String
)