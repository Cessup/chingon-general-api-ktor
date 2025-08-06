package com.cessup.domain.usecases.session

import com.cessup.data.services.Encrypt
import com.cessup.domain.repositories.UserRepository
import com.google.inject.Inject

/**
 * Reset Password of user in the system.
 *
 * This class authenticate user for access system.
 *
 * @constructor Receiver a [UserRepository] object because use it to origin data.
 * @author
 *     Cessup
 * @since 1.0
 */
class ResetPasswordUseCase @Inject constructor(private val userRepository: UserRepository, private val security: Encrypt) {

    /**
     * Returns the sum of two integers.
     *
     * @param email the email is to search for the user
     * @param password the password is the new value to change in the user
     * @return A [Boolean] that is the result about this process
     */
    suspend fun execute(email:String, password:String): Boolean = userRepository.updatePassword(email,security.hashPassword(password))

}