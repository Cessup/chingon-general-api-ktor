package com.cessup.domain.models.products

import com.cessup.domain.models.sales.Sale

/**
 * Represents a Product.
 *
 * @property id unique identifier
 * @property serialNumber the code for identify every product
 * @property category the category is the classification about it
 * @property subcategory the subcategory is the classification inside category about it so it is more specify
 * @property name the name is the name commercial
 * @property img the img is the url of the image to the product
 * @property rating the rating is a classification about of product
 * @property details All information about this product
 */
data class Product(
    val id: String,
    val serialNumber: String,
    val category: String,
    val subcategory: String,
    val name: String,
    val img: String,
    val rating: Double,
    val sale: Sale?,
    val details: ProductDetails
)