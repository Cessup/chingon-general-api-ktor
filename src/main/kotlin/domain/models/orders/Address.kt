package com.cessup.domain.models.orders

import org.bson.types.ObjectId

data class Address(
    val id: ObjectId = ObjectId.get(),
    val street: String,
    val numberInt: String,
    val numberExt: String,
    val city: String,
    val state: String,
    val country: String,
    val postalCode: String,
    val zipCode: String,
)