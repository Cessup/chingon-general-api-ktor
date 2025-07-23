package com.cessup.data.models

import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id

data class UserDetailsEntity(
    @BsonId val id: Id<UserEntity>,
    val name: String,
    val phoneNumber: String,
    val address: String,
    val gender: String,
    val birthdate : Long
)