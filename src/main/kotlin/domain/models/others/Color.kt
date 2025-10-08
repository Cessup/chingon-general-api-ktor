package com.cessup.domain.models.others

import org.bson.types.ObjectId

/**
 * Represents a Color.
 *
 * @property id unique identifier
 * @property code the code for identify every color
 * @property name the name the color
 */
data class Color(
    val id: ObjectId,
    val code: String,
    val name: String
)