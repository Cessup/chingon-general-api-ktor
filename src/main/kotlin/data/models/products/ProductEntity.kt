package com.cessup.data.models.products

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import org.litote.kmongo.Id

/**
 * Represents a Product.
 *
 * @property id unique identifier
 * @property serialNumber the code for identify every product
 * @property category the category is the classification about it
 * @property subcategory the subcategory is the classification inside category about it so it is more specify
 * @property stock the stock is the count of product there are
 * @property name the name is the name commercial
 * @property img the img is the url of the image to the product
 * @property rating the rating is a classification about of product
 * @property idBrand the idBrand is the identify to brand
 * @property idDetails All information about this product
 */
data class ProductEntity(
    @BsonId val id: Id<ProductEntity>,
    val serialNumber: String,
    val category: String,
    val subcategory: String,
    val stock: Int,
    val name: String,
    val img: String,
    val rating: Double,
    val idBrand: ObjectId,
    val idDetails: ObjectId
)