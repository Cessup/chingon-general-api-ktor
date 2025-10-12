package com.cessup.domain.usecases.session

import com.cessup.domain.repositories.UserRepository
import com.cessup.domain.models.session.User
import com.cessup.data.services.Encrypt
import com.cessup.data.services.RegisterUserRequest
import com.cessup.domain.models.session.Role
import com.cessup.domain.models.session.Type
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

        val user = User(
            ObjectId(),
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
        //Add user in user collection
        val resultNewUser = userRepository.insertUser(user)
        //Find the role
        val list = userRepository.findRoles()

        val role: Role? = list.filter { role ->
            registerRequest.role == role?.code
        }[0]

        val type = Type(ObjectId(),user.id, role?.id ?: ObjectId())
        val resultTypeUser = userRepository.insertType(type)
        return resultNewUser && resultTypeUser
    }
}