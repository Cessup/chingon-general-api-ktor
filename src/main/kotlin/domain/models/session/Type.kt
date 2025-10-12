package com.cessup.domain.models.session

import org.bson.types.ObjectId

/**
 * Represents a user.
 *
 * @property id unique identifier
 * @property idUser unique identifier of user
 * @property idRole the phone of the user for account
 */
data class Type(
    val id: ObjectId = ObjectId(),
    val idUser: ObjectId = ObjectId(),
    val idRole: ObjectId = ObjectId()
)