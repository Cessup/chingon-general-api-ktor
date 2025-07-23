package com.cessup.domain.repositories

import domain.models.User
import org.bson.types.ObjectId

interface UserRepository {
    suspend fun findByEmail(email: String): User?
    suspend fun insert(email: String, password:String): User
    suspend fun findById(id: ObjectId): User?
}