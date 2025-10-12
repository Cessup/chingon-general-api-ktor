package com.cessup.domain.usecases.session

import com.cessup.domain.repositories.UserRepository
import com.cessup.data.services.RegisterRoleRequest
import com.cessup.data.services.RegisterUserRequest
import com.cessup.domain.models.session.Role
import com.google.inject.Inject
import org.bson.types.ObjectId

/**
 * Register of user in the system.
 *
 * This class make register of user information such as name, email, and age.
 *
 * @constructor Receiver a [UserRepository] because there are information about it
 * @author
 *     Cessup
 * @since 1.0
 */
class RegisterRoleUseCase @Inject constructor(private val userRepository: UserRepository) {

    /**
     * Returns new user.
     *
     * @param RegisterUserRequest this object got information about the account like email,phoneNumber,nickname,password, etc.
     * @return A [Boolean] that is the result about this process
     */
    suspend fun execute(registerRoleRequest: RegisterRoleRequest): Boolean {
        val list = userRepository.findRoles().filter { role ->
            registerRoleRequest == role
        }.let { if (it.isNotEmpty()) throw IllegalArgumentException("Role already in use") }

        val role = Role(
            ObjectId(),
            registerRoleRequest.code,
            registerRoleRequest.name,
        )

        return userRepository.insertRole(role)
    }
}