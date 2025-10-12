package com.cessup.domain.repositories

import com.cessup.domain.models.session.Role
import com.cessup.domain.models.session.Type
import com.cessup.domain.models.session.UserDetails
import com.cessup.domain.models.session.User
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
     * @param user the user information from the services is here
     * @return result in boolean value
     */
    suspend fun insertUser(user: User): Boolean

    /**
     * This function insert a new user in the database
     *
     * @param type the id to identify the user
     * @return result in boolean value
     */
    suspend fun insertType(type: Type): Boolean

    /**
     * This function insert a new role to user in the database
     *
     * @param role the user information from the services is here
     * @return result in boolean value
     */
    suspend fun insertRole(role: Role): Boolean

    /**
     * The system can update the user details data to the user
     *
     * @param id the id to identify
     * @param userDetails the user details is to update the object
     * @return result in boolean value
     */
    suspend fun updateUserDetails(id: ObjectId, userDetails: UserDetails): Boolean

    /**
     * The system can update the user details data to the user
     *
     * @param idUser the id to identify the user
     * @param idNewRole the id to identify the role will assign
     * @return result in boolean value
     */
    suspend fun updateType(idUser: ObjectId, idNewRole: ObjectId): Boolean

    /**
     * This function delete a user in the database
     *
     * @param email the id is identification to search the object in database
     * @param password the password is the new value to change it
     * @return result in boolean value
     */
    suspend fun updatePassword(email: String, password:String): Boolean

    /**
     * This function delete a user in the database
     *
     * @param role the id is identification to search the object in database
     * @return result in boolean value
     */
    suspend fun updateRole(role: Role): Boolean
    /**
     * This function delete a user in the database
     *
     * @param id the id is identification to search the object in database
     * @return result in boolean value
     */
    suspend fun deleteUser(id: ObjectId) : Boolean

    /**
     * This function delete a role of user in the database
     *
     * @param id the id is identification to search the object in database
     * @return a Boolean
     */
    suspend fun deleteType(id: ObjectId) : Boolean

    /**
     * This function delete a role of user in the database
     *
     * @param id the id is identification to search the object in database
     * @return a Boolean
     */
    suspend fun deleteRole(id: ObjectId) : Boolean
    /**
     * Find a user in the database by id.
     *
     * @param id the id is identification to search the object in database
     * @return the result is a user found from the search
     */
    suspend fun findById(id: ObjectId): User?
    /**
     * Find a user in the database by email.
     *
     * @param email the email is identification to search the object in database
     * @return the result is a user found from the search
     */
    suspend fun findByEmail(email: String): User?
    /**
     * Find a user in the database by phone.
     *
     * @param phone the phone is identification to search the object in database
     * @return the result is a user found from the search
     */
    suspend fun findByPhone(phone: String): User?
    /**
     * This function delete a user in the database
     *
     * @param idUser it is the identify to user
     * @return a user
     */
    suspend fun findRoleByIdUser(idUser: ObjectId): Role

    /**
     * Find a list of roles user in the database.
     *
     * @return the result is a list of roles found from the search
     */
    suspend fun findRoles(): List<Role?>
}