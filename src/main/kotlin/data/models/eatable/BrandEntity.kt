package com.cessup.data.models.eatable

import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id

/**
 * Represents Brand abstraction to eat.
 *
 * @property id unique identifier
 * @property name unique identifier
 * @property description unique identifier
 * @property img unique identifier
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