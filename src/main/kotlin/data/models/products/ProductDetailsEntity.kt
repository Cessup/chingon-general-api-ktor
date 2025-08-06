package com.cessup.data.models.products

import com.cessup.data.models.others.ColorEntity
import com.cessup.data.models.others.DimensionsEntity
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id

/**
 * Represents a user.
 *
 * @property id unique identifier
 * @property name the name of the user
 * @property description the lastname of the user
 * @property model the birthday of the user
 * @property version the birthday of the user
 * @property brand the birthday of the user
 * @property dimensions the gender of the user
 * @property color the birthday of the user
 * @property tags the birthday of the user
 */
data class ProductDetailsEntity(
    @BsonId val id: Id<ProductDetailsEntity>,
    val name: String,
    val description: String,
    val model: String,
    val version: String,
    val brand: String,
    val dimensions: DimensionsEntity,
    val color: ColorEntity,
    val tags: List<String>
)