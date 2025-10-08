package com.cessup.domain.models.session

import org.bson.types.ObjectId

/**
 * Represents a user.
 *
 * @property id unique identifier
 * @property email the email the user belongs to
 * @property phone the phone of the user for account
 * @property nickname the nickname is another identifier to user
 * @property password the password of the user for account
 * @property details All information about this user
 */
data class User(
    val id: ObjectId,
    val email: String,
    val phone: String,
    val nickname: String,
    val password: String,
    val details: UserDetails
)