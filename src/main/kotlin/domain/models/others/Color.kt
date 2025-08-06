package com.cessup.domain.models.others

/**
 * Represents a Color.
 *
 * @property id unique identifier
 * @property code the code for identify every color
 * @property name the name the color
 */
data class Color(
    val id: String,
    val code: String,
    val name: String
)