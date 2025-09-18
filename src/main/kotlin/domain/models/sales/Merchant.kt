package com.cessup.domain.models.sales

/**
 * Represents a Merchant.
 *
 * @property id unique identifier
 * @property name this is the name for the promotion
 */
data class Merchant(
    val id: String,
    val name:String,
)