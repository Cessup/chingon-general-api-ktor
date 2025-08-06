package com.cessup.domain.usecases.session

import com.cessup.domain.repositories.UserRepository
import com.cessup.data.services.Encrypt
import com.google.inject.Inject
import domain.models.User

/**
 * Authenticate of user in the system.
 *
 * This class authenticate user for access system.
 *
 * @constructor Receiver a [UserRepository] object because use it to origin data.
 * @author
 *     Cessup
 * @since 1.0
 */
class AuthenticateUseCase @Inject constructor(val userRepository: UserRepository) {

    /**
     * Returns the sum of two integers.
     *
     * @param email the first number
     * @param password the second number
     * @return A new [User] from the previously entered credentials
     */
    suspend fun execute(email: String, password: String): User? {
        val user = userRepository.findByEmail(email) ?: return null
        Encrypt.verifyPassword(password, Encrypt.hashPassword(user.password))
        return user
    }
}