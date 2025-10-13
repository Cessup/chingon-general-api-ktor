package com.cessup.domain.usecases.session

import com.cessup.domain.repositories.UserRepository
import com.cessup.domain.models.session.User
import org.bson.types.ObjectId
import com.cessup.data.services.Security

/**
 * Get User of user in the system.
 *
 * This class give every data about user .
 *
 * @constructor Receiver a [UserRepository] object because use it to origin data.
 * @author
 *     Cessup
 * @since 1.0
 */
class GetUserUseCase(val repository: UserRepository, val security: Security){

    /**
     * Returns the sum of two integers.
     *
     * @param id the id from user for get their information
     * @return A [User] from the previously email
     */
    suspend fun execute(id: String): User? {
        val userUnsure = repository.findById(ObjectId(id))
        return userUnsure?.copy(password = security.hashPassword(password = userUnsure.password))
    }
}