package com.cessup.domain.models.session

import org.bson.types.ObjectId

/**
 * Represents a user.
 *
 * @property id unique identifier
 * @property name the name of the user
 * @property lastName the lastname of the user
 * @property gender the gender of the user
 * @property birthdate the birthday of the user
 */
data class UserDetails(
    val id: ObjectId,
    val name: String,
    val lastName: String,
    val address: String,
    val gender: String,
    val birthdate : Long
)