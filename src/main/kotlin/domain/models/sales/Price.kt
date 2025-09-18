package com.cessup.domain.models.sales

/**
 * Represents a Price.
 *
 * @property id unique identifier
 * @property mount this is a number to the price
 * @property currency it is the currency from meke it
 * @property merchant Create new scratch file from selection
 * @property item that is the object for the price
 */
data class Price (
    val id : String,
    val mount: Int,
    val currency: String,
    val merchant: Merchant,
    val item: Object
)