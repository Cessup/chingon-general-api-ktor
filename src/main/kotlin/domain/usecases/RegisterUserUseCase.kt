package com.cessup.domain.usecases

import com.cessup.data.models.UserDetailsEntity
import com.cessup.data.models.UserEntity
import com.cessup.domain.repositories.UserRepository
import domain.models.User
import com.cessup.data.services.Encrypt
import com.cessup.data.services.RegisterUserRequest
import com.google.inject.Inject
import org.litote.kmongo.newId

/**
 * Register of user in the system.
 *
 * This class make register of user information such as name, email, and age.
 *
 * @constructor Receiver a [UserRepository] and a [Encrypt] object because use it to origin data.
 * @author
 *     Cessup
 * @since 1.0
 */
class RegisterUserUseCase @Inject constructor(private val userRepository: UserRepository, private val security: Encrypt) {

    /**
     * Returns new user.
     *
     * @param RegisterUserRequest this object got information about the account like email,phoneNumber,nickname,password, etc.
     * @return A new [User] from the previously entered credentials
     */
    suspend fun execute(registerRequest: RegisterUserRequest): User? {
        userRepository.findByEmail(registerRequest.email)?.let { throw IllegalArgumentException("Email already in use") }
        userRepository.findByPhone(registerRequest.phone)?.let { throw IllegalArgumentException("Phone already in use") }
        userRepository.findByPhone(registerRequest.nickname)?.let { throw IllegalArgumentException("NickName already in use") }

        val userEntity = UserEntity(
            newId(),
            registerRequest.email,
            registerRequest.phone,
            registerRequest.nickname,
            security.hashPassword(registerRequest.password),
            UserDetailsEntity(
                newId(),
                registerRequest.details.name,
                registerRequest.details.lastName,
                registerRequest.details.address,
                registerRequest.details.gender,
                registerRequest.details.birthdate
            ),
        )

        return userRepository.insertUser(userEntity)
    }
}