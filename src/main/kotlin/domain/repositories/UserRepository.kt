package com.cessup.domain.repositories

import com.cessup.data.models.UserDetailsEntity
import com.cessup.data.models.UserEntity
import domain.models.User
import org.bson.types.ObjectId

/**
 * User Repository have every data about the users.
 *
 * This class is a interface with all functions about user information
 * There are actions that the user can perform
 *
 * @author
 *     Cessup
 * @since 1.0
 */
interface UserRepository {
    /**
     * This function insert a new user in the database
     *
     * @param userEntity the user information from the services is here
     * @return a user
     */
    suspend fun insertUser(userEntity: UserEntity): User

    /**
     * The system can update the user details data to the user
     *
     * @param userDetailsEntity the email is a filter to search the user in database
     * @return a Boolean
     */
    suspend fun updateUserDetails(userDetailsEntity: UserDetailsEntity): Boolean

    /**
     * This function delete a user in the database
     *
     * @param email the id is identification to search the object in database
     * @param password the password is the new value to change it
     * @return a user
     */
    suspend fun updatePassword(email: String, password:String): Boolean
    /**
     * This function delete a user in the database
     *
     * @param id the id is identification to search the object in database
     * @return a Boolean
     */
    suspend fun deleteUser(id: ObjectId) : Boolean
    /**
     * Find a user in the database by id.
     *
     * @param id the id is identification to search the object in database
     * @return a user
     */
    suspend fun findById(id: ObjectId): User?
    /**
     * Find a user in the database by email.
     *
     * @param email the email is identification to search the object in database
     * @return a user
     */
    suspend fun findByEmail(email: String): User?
    /**
     * Find a user in the database by phone.
     *
     * @param phone the phone is identification to search the object in database
     * @return a user
     */
    suspend fun findByPhone(phone: String): User?
}