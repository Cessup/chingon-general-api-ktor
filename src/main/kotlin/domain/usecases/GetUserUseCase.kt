package com.cessup.domain.usecases

import com.cessup.domain.repositories.UserRepository
import com.google.inject.Inject
import domain.models.User
import org.bson.types.ObjectId
import com.cessup.data.services.Encrypt

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
class GetUserUseCase @Inject constructor(val userRepository: UserRepository, val security: Encrypt) {

    /**
     * Returns the sum of two integers.
     *
     * @param id the id from user for get their information
     * @return A [User] from the previously email
     */
    suspend fun execute(id: String): User? {
        val userUnsure = userRepository.findById(ObjectId(id))
        return userUnsure?.copy(password = security.hashPassword(password = userUnsure.password))
    }
}