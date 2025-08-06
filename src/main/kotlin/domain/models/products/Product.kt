package com.cessup.domain.models.products

import com.cessup.domain.models.others.Review

/**
 * Represents a Product.
 *
 * @property id unique identifier
 * @property serialNumber the code for identify every product
 * @property price the price is about value of product
 * @property currency the currency is a references of prices
 * @property category the category is the classification about it
 * @property subcategory the subcategory is the classification inside category about it so it is more specify
 * @property stock the stock is the count of product there are
 * @property rating the rating is a classification about of product
 * @property details All information about this product
 * @property reviews That is a list with all reviews from users
 */
data class Product(
    val id: String,
    val serialNumber: String,
    val price: Double,
    val currency: Double,
    val category: String,
    val subcategory: String,
    val stock: Int,
    val rating: Double,
    val details: ProductDetails,
    val reviews: List<Review>
)