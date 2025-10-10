package com.cessup.data.entities

import com.cessup.domain.models.products.Brand
import com.cessup.domain.models.products.Product
import com.cessup.domain.models.products.ProductDetails
import org.bson.Document

fun Product.toDocument(): Document = Document()
    .append("_id", id)
    .append("serialNumber", serialNumber)
    .append("category", category)
    .append("subcategory", subcategory)
    .append("stock", stock)
    .append("name", name)
    .append("img", img)
    .append("rating", rating)
    .append("idBrand", idBrand)
    .append("idDetails", idDetails)

fun Document.toProduct(): Product =
    Product(
        id = getObjectId("_id"),
        serialNumber = getString("serialNumber"),
        category = getString("category"),
        subcategory = getString("subcategory"),
        stock = getInteger("stock"),
        name = getString("name"),
        img = getString("img"),
        rating = getDouble("rating"),
        idBrand = getObjectId("idBrand"),
        idDetails = getObjectId("idDetails"),
    )

fun ProductDetails.toDocument(): Document = Document()
    .append("_id", id)
    .append("description", description)
    .append("version", version)
    .append("dimensions", dimensions)
    .append("color", color)
    .append("tags", tags)

fun Document.toProductDetails(): ProductDetails =
    ProductDetails(
        id = getObjectId("_id"),
        description = getString("description"),
        version = getString("version"),
        dimensions = (get("dimensions") as Document).toDimensions(),
        color = (get("color") as Document).toColor(),
        tags = getList("tags",String::class.java)
    )

fun Brand.toDocument(): Document = Document()
    .append("_id", id)
    .append("name", name)
    .append("description", description)
    .append("img", img)

fun Document.toBrandEntity(): Brand =
    Brand(
        id = getObjectId("_id"),
        name = getString("name"),
        description = getString("description"),
        img = getString("img")
    )

