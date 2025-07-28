package com.cessup.domain.usecases

import com.cessup.domain.repositories.UserRepository
import com.google.inject.Inject
import org.bson.types.ObjectId

/**
 * Delete any user of the system.
 *
 * This class delete any user of the system.
 *
 * @constructor Receiver a [UserRepository] object because use it to origin data.
 * @author
 *     Cessup
 * @since 1.0
 */
class DeleteUserUseCase @Inject constructor(private val userRepository: UserRepository) {

    /**
     * Returns the sum of two integers.
     *
     * @param id the id from user for get their information
     * @return A [Boolean] that is the result about this process
     */
    suspend fun execute(id: String): Boolean = userRepository.deleteUser(ObjectId(id))
}
