package com.cessup.domain.usecases.session

import com.cessup.data.services.Security
import com.cessup.domain.repositories.UserRepository
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
class ResetPasswordUseCase(val repository: UserRepository, val security: Security){

    /**
     * Returns the sum of two integers.
     *
     * @param email the email is to search for the user
     * @param password the password is the new value to change in the user
     * @return A [Boolean] that is the result about this process
     */
    suspend fun execute(email:String, password:String): Boolean = repository.updatePassword(email,security.hashPassword(password))

}