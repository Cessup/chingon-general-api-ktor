package com.cessup.data.models.others

import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id

/**
 * Represents a Color.
 *
 * @property id unique identifier
 * @property code the code for identify every color
 * @property name the name the color
 */
data class ColorEntity (
    @BsonId val id: Id<ColorEntity>,
    val code: String,
    val name: String,
)