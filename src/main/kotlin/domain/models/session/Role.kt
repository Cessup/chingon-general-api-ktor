package com.cessup.domain.models.session

import org.bson.types.ObjectId

/**
 * Represents a user.
 *
 * @property id unique identifier
 * @property name the name is about the rol like admin, guest or another one.
 */
data class Role(
    val id: ObjectId = ObjectId(),
    val code: Int,
    val name: String,
)