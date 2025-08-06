package com.cessup.data.models.products

import com.cessup.data.models.others.ReviewEntity
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id

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
data class ProductEntity(
    @BsonId val id: Id<ProductEntity>,
    val serialNumber: String,
    val price: Double,
    val currency: Double,
    val category: String,
    val subcategory: String,
    val stock: Int,
    val rating: Double,
    val details: ProductDetailsEntity,
    val reviews: List<ReviewEntity>
)