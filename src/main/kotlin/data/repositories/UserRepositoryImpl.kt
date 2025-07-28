package com.cessup.data.repositories

import com.cessup.data.models.UserDetailsEntity
import com.cessup.data.models.UserEntity
import com.cessup.domain.models.UserDetails
import com.cessup.domain.repositories.UserRepository
import com.google.inject.Inject
import com.mongodb.client.model.Filters
import domain.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.setValue
import org.bson.types.ObjectId
import org.litote.kmongo.div


/**
 * User Repository have every data about the users.
 *
 * This class is a interface with all functions about user information
 * There are actions that the user can perform
 *
 * @constructor Receiver a [CoroutineDatabase] object is wrap database access in Kotlin coroutines to allow non-blocking, asynchronous operations.
 *
 * @author
 *     Cessup
 * @since 1.0
 */
class UserRepositoryImpl @Inject constructor(database: CoroutineDatabase) : UserRepository {

    /**
     * This function insert a new user in the database
     */
    private val users = database.getCollection<UserEntity>("users")

    /**
     * This function insert a new user in the database
     *
     * @param userEntity the user information from the services is here
     * @return a user
     */
    override suspend fun insertUser(userEntity: UserEntity): User = withContext(Dispatchers.IO) {
        users.insertOne(userEntity)

        User(userEntity.id.toString(),userEntity.email,userEntity.phone,userEntity.password,
            UserDetails(
                userEntity.details.id.toString(), userEntity.details.name, userEntity.details.lastName,userEntity.details.address,userEntity.details.gender,userEntity.details.birthdate
            )
        )
    }

    /**
     * The system can update the user details data to the user
     *
     * @param userDetailsEntity the email is a filter to search the user in database
     * @return a Boolean
     */
    override suspend fun updateUserDetails(userDetailsEntity: UserDetailsEntity) : Boolean = withContext(Dispatchers.IO) {
        val filter = Filters.eq((UserEntity::details / UserDetailsEntity::id).toString(), ObjectId(userDetailsEntity.id.toString()))
        val update = setValue(UserEntity::details, userDetailsEntity)
        val updateResult = users.updateOne(filter,update)
        updateResult.matchedCount > 0 && updateResult.modifiedCount > 0
    }

    /**
     * The system can update the password to the user
     *
     * @param email the email is a filter to search the user in database
     * @param password the password is a new value to insert in the database
     * @return a user
     */
    override suspend fun updatePassword(email: String, password: String): Boolean = withContext(Dispatchers.IO) {
        val updateResult = users.updateOne(
            UserEntity::email eq email,
            setValue(UserEntity::password, password)
        )
        updateResult.modifiedCount == 1L
    }

    /**
     * This function delete a user in the database
     *
     * @param id the id is identification to search the object in database
     * @return a user
     */
    override suspend fun deleteUser(id: ObjectId) : Boolean = withContext(Dispatchers.IO) {
        val deleteResult = users.deleteOneById(id)
        deleteResult.deletedCount == 1L
    }

    /**
     * Find a user in the database by id.
     *
     * @param id the user's login name or email
     * @return a user
     */
    override suspend fun findById(id: ObjectId): User? =
        users.findOneById(id)
            ?.let {
                User(it.id.toString(), it.email, it.phone, it.password, UserDetails(it.details.id.toString(),it.details.name,it.details.lastName,it.details.address,it.details.gender,it.details.birthdate)) }

    /**
     * Find a user in the database by email.
     *
     * @param email the user's login name or email
     * @return a user
     */
    override suspend fun findByEmail(email: String): User? =
        users.findOne(UserEntity::email eq email)
            ?.let { User(it.id.toString(), it.email, it.phone, it.password, UserDetails(it.details.id.toString(),it.details.name,it.details.lastName,it.details.address,it.details.gender,it.details.birthdate)) }

    /**
     * Find a user in the database by phone.
     *
     * @param phone the user's login name or email
     * @return a user
     */
    override suspend fun findByPhone(phone: String): User? =
        users.findOne(UserEntity::phone eq phone)
            ?.let { User(it.id.toString(), it.email, it.phone, it.password, UserDetails(it.details.id.toString(),it.details.name,it.details.lastName,it.details.address,it.details.gender,it.details.birthdate)) }
}