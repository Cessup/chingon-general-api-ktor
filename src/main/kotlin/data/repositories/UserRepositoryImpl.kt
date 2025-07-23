package com.cessup.data.repositories

import com.cessup.data.models.UserEntity
import com.cessup.domain.repositories.UserRepository
import com.google.inject.Inject
import domain.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.bson.types.ObjectId
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.newId
import org.litote.kmongo.util.idValue

class UserRepositoryImpl @Inject constructor(database: CoroutineDatabase) : UserRepository {

    private val users = database.getCollection<UserEntity>("users")

    override suspend fun insert(email: String,password: String): User = withContext(Dispatchers.IO) {
        val userEntity = UserEntity(newId(), email, password)
        users.insertOne(userEntity)
        User(userEntity.id.toString(),userEntity.email,userEntity.password)
    }

    override suspend fun findByEmail(email: String): User? =
        users.findOne(UserEntity::email eq email)
            ?.let { User(it.id.toString(), it.email, it.password) }

    override suspend fun findById(id: ObjectId): User? =
        users.findOne(UserEntity::idValue eq id)
        ?.let { User(it.id.toString(), it.email, it.password) }
}