package com.cessup.domain.usecases.session

import com.cessup.domain.repositories.UserRepository
import com.google.inject.Inject
import com.cessup.domain.models.session.User
import org.bson.types.ObjectId
import com.cessup.domain.models.session.Role

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
class GetRoleUseCase @Inject constructor(val userRepository: UserRepository) {

    /**
     * Returns the sum of two integers.
     *
     * @param idUser the id from user for get their information
     * @return A [User] from the previously email
     */
    suspend fun execute(idUser: ObjectId): Role = userRepository.findRoleByIdUser(idUser)

}