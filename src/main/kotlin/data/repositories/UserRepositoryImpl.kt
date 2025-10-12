package com.cessup.data.repositories

import com.cessup.data.entities.toDocument
import com.cessup.data.entities.toRole
import com.cessup.data.entities.toType
import com.cessup.data.entities.toUser
import com.cessup.domain.models.session.Role
import com.cessup.domain.models.session.Type
import com.cessup.domain.repositories.UserRepository
import com.google.inject.Inject
import com.mongodb.client.model.Filters.eq
import com.mongodb.client.model.Updates.set
import com.mongodb.reactivestreams.client.MongoDatabase
import com.cessup.domain.models.session.User
import com.cessup.domain.models.session.UserDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.withContext
import org.bson.types.ObjectId


/**
 * User Repository have every data about the users.
 *
 * This class is a interface with all functions about user information
 * There are actions that the user can perform
 *
 * @constructor Receiver a [MongoDatabase] object is wrap database access in Kotlin coroutines to allow non-blocking, asynchronous operations.
 *
 * @author
 *     Cessup
 * @since 1.0
 */
class UserRepositoryImpl @Inject constructor(database: MongoDatabase) : UserRepository {

    /**
     * This value is to User Collection in DB
     */
    val users = database.getCollection("users")
    /**
     * This value is to Type Collection in DB
     */
    val types = database.getCollection("type")
    /**
     * This value is to Roles Collection in DB
     */
    val roles = database.getCollection("roles")

    /**
     * This function insert a new user in the database
     *
     * @param user the user information from the services is here
     * @return a user
     */
    override suspend fun insertUser(user: User): Boolean = withContext(Dispatchers.IO) {
        try {
            users.insertOne(user.toDocument()).awaitFirstOrNull()
            true
        } catch (_: Exception) {
            false
        }
    }
    /**
     * This function insert a new user in the database
     *
     * @param type the id to identify the user
     * @return result in boolean value
     */
    override suspend fun insertType(type: Type): Boolean  = withContext(Dispatchers.IO) {
        try {
            types.insertOne( type.toDocument() ).awaitFirstOrNull()
            true
        } catch (_: Exception) {
            false
        }
    }
    /**
     * This function insert a new role to user in the database
     *
     * @param role the user information from the services is here
     * @return result in boolean value
     */
    override suspend fun insertRole(role: Role): Boolean  = withContext(Dispatchers.IO) {
        try {
            roles.insertOne(role.toDocument()).awaitFirstOrNull()
            true
        } catch (_: Exception) {
            false
        }
    }

    /**
     * The system can update the user details data to the user
     *
     * @param id the identify to user
     * @param userDetails the userDetails contains the information to change
     * @return a Boolean
     */
    override suspend fun updateUserDetails(id: ObjectId, userDetails: UserDetails) : Boolean = withContext(Dispatchers.IO) {
        val updateResult = users.updateOne(
            eq("_id", id),
            set("details", userDetails)
        ).awaitFirstOrNull()
        updateResult?.matchedCount == 1L
    }
    /**
     * The system can update the user details data to the user
     *
     * @param idUser the id to identify the user
     * @param idNewRole the id to identify the role will assign
     * @return result in boolean value
     */
    override suspend fun updateType(
        idUser: ObjectId,
        idNewRole: ObjectId
    ): Boolean = withContext(Dispatchers.IO) {
        val updateResult = types.updateOne(
            eq("idUser", idUser),
            set("idRole", idNewRole)
        ).awaitFirstOrNull()
        updateResult?.matchedCount == 1L
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
            eq("email", email),
            set("password", password)
        ).awaitFirstOrNull()
        updateResult?.modifiedCount == 1L
    }
    /**
     * This function delete a user in the database
     *
     * @param role the id is identification to search the object in database
     * @return result in boolean value
     */
    override suspend fun updateRole(role: Role): Boolean  = withContext(Dispatchers.IO) {
        val updateResult = roles.replaceOne(
            eq("_id", role.id),
            role.toDocument()
        ).awaitFirstOrNull()
        updateResult?.modifiedCount == 1L
    }
    /**
     * This function delete a user in the database
     *
     * @param id the id is identification to search the object in database
     * @return a user
     */
    override suspend fun deleteUser(id: ObjectId) : Boolean = withContext(Dispatchers.IO) {
        val deleteResult = users.deleteOne(eq("_id", id)).awaitFirstOrNull()
        deleteResult?.deletedCount == 1L
    }
    /**
     * This function delete a role of user in the database
     *
     * @param id the id is identification to search the object in database
     * @return a Boolean
     */
    override suspend fun deleteType(id: ObjectId): Boolean  = withContext(Dispatchers.IO) {
        val deleteResult = types.deleteOne(eq("_id", id)).awaitFirstOrNull()
        deleteResult?.deletedCount == 1L
    }
    /**
     * This function delete a role of user in the database
     *
     * @param id the id is identification to search the object in database
     * @return a Boolean
     */
    override suspend fun deleteRole(id: ObjectId): Boolean  = withContext(Dispatchers.IO) {
        val deleteResult = roles.deleteOne(eq("_id", id)).awaitFirstOrNull()
        deleteResult?.deletedCount == 1L
    }

    /**
     * Find a user in the database by id.
     *
     * @param id the user's login name or email
     * @return a user
     */
    override suspend fun findById(id: ObjectId): User? =
        users.find(eq("_id", id)).first().awaitFirstOrNull()?.toUser()

    /**
     * Find a user in the database by email.
     *
     * @param email the user's login name or email
     * @return a user
     */
    override suspend fun findByEmail(email: String): User? =
        users.find(eq("email", email)).first().awaitFirstOrNull()?.toUser()
    /**
     * Find a user in the database by phone.
     *
     * @param phone the user's login name or email
     * @return a user
     */
    override suspend fun findByPhone(phone: String): User? =
        users.find(eq("phone", phone)).first().awaitFirstOrNull()?.toUser()

    /**
     * This function delete a user in the database
     *
     * @param idUser it is the identify to user
     * @return a user
     */
    override suspend fun findRoleByIdUser(idUser: ObjectId): Role {
        val typeUser = types.find(eq("idUser", idUser)).first().awaitFirstOrNull()?.toType()
        return roles.find(eq("_id", typeUser?.idRole)).first().awaitFirstOrNull()!!.toRole()
    }
    /**
     * Find a list of roles user in the database.
     *
     * @return the result is a list of roles found from the search
     */
    override suspend fun findRoles(): List<Role?> = roles.find().asFlow().map { it.toRole() }.toList()

}