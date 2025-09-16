package com.cessup.domain.models.sales

data class Promotion (
    val id: String,
    val name: String,
    val details : String,
    val discount: Int,
    val merchant: Merchant
)