package com.cessup.data.models.session

import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id

/**
 * Represents a user details with the structure of the database.
 *
 * @property id unique identifier
 * @property name the name of the user
 * @property lastName the lastname of the user
 * @property gender the gender of the user
 * @property birthdate the birthday of the user
 */
data class UserDetailsEntity(
    @BsonId val id: Id<UserDetailsEntity>,
    val name: String,
    val lastName: String,
    val address: String,
    val gender: String,
    val birthdate : Long
)