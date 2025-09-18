package com.cessup.data.models.products

import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id

/**
 * Represents Brand.
 *
 * @property id unique identifier
 * @property name unique identifier
 * @property description unique identifier
 * @property img the img is the url of the image to the brand
 *
 * @author
 *     Cessup
 * @since 1.0
 */
data class BrandEntity (
    @BsonId val id: Id<BrandEntity>,
    val name: String,
    val description:String,
    val img:String,
)