package com.cessup.domain.models.sales

data class Price (
    val id : String,
    val mount: Int,
    val currency: String,
    val merchant: Merchant,
    val item: Object
)