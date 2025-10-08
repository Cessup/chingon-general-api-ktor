package com.cessup.domain.usecases.session

import com.cessup.domain.repositories.UserRepository
import com.cessup.domain.models.session.User
import com.cessup.data.services.Encrypt
import com.cessup.data.services.RegisterUserRequest
import com.cessup.domain.models.session.UserDetails
import com.google.inject.Inject
import org.bson.types.ObjectId

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
    suspend fun execute(registerRequest: RegisterUserRequest): Boolean {
        userRepository.findByEmail(registerRequest.email)?.let { throw IllegalArgumentException("Email already in use") }
        userRepository.findByPhone(registerRequest.phone)?.let { throw IllegalArgumentException("Phone already in use") }
        userRepository.findByPhone(registerRequest.nickname)?.let { throw IllegalArgumentException("NickName already in use") }

        var id = ObjectId()
        val user = User(
            id,
            registerRequest.email,
            registerRequest.phone,
            registerRequest.nickname,
            security.hashPassword(registerRequest.password),
            UserDetails(
                ObjectId(),
                registerRequest.details.name,
                registerRequest.details.lastName,
                registerRequest.details.address,
                registerRequest.details.gender,
                registerRequest.details.birthdate
            ),
        )
        return userRepository.insertUser(user)
    }
}