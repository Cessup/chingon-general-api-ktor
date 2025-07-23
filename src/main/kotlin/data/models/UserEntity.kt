package com.cessup.data.models

import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id

data class UserEntity(
    @BsonId val id: Id<UserEntity>,
    val email: String,
    val password: String
)