package com.cessup.domain.usecases.session

import com.cessup.data.services.Encrypt
import com.cessup.data.services.RegisterRoleRequest
import com.cessup.domain.models.session.Role
import com.cessup.domain.repositories.UserRepository
import com.google.inject.Inject
import org.bson.types.ObjectId

/**
 * Update of user details by id user in the system.
 *
 * This class update of details user information such as name, email, and age.
 *
 * @constructor Receiver a [UserRepository] and a [Encrypt] object because use it to origin data.
 * @author
 *     Cessup
 * @since 1.0
 */
class UpdateRoleUseCase @Inject constructor(private val userRepository: UserRepository) {

    /**
     * Returns new user.
     *
     * @param RegisterRoleRequest this object got information about the account like email,phoneNumber,nickname,password, etc.
     * @return A [Boolean] that is the result about this process
     */
    suspend fun execute(updateRoleRequest: RegisterRoleRequest, id: ObjectId): Boolean {
        userRepository.findRoles().filter { role ->
            id == role?.id
        }.let { if (it.isEmpty()) throw IllegalArgumentException("Role not exist") }

        val role = Role(
            id,
            updateRoleRequest.code,
            updateRoleRequest.name,
        )

        return userRepository.updateRole(role)
    }
}