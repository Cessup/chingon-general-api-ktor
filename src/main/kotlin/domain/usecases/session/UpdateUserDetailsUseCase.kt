package com.cessup.domain.usecases.session

import com.cessup.data.services.RegisterUserDetailsRequest
import com.cessup.data.services.RegisterUserRequest
import com.cessup.domain.repositories.UserRepository
import com.cessup.domain.models.session.User
import com.cessup.domain.models.session.UserDetails
import org.bson.types.ObjectId
/**
 * Update of user details by id user in the system.
 *
 * This class update of details user information such as name, email, and age.
 *
 * @constructor Receiver a [UserRepository] is an object because use it to origin data.
 * @author
 *     Cessup
 * @since 1.0
 */
class UpdateUserDetailsUseCase(val repository: UserRepository){

    /**
     * Returns new user.
     *
     * @param RegisterUserRequest this object got information about the account like email,phoneNumber,nickname,password, etc.
     * @return A new [User] from the previously entered credentials
     */
    suspend fun execute(registerRequest: RegisterUserDetailsRequest, id: ObjectId): Boolean {
        val userDetails = UserDetails(
            id,
            registerRequest.name,
            registerRequest.lastName,
            registerRequest.address,
            registerRequest.gender,
            registerRequest.birthdate
        )
        return repository.updateUserDetails(id,userDetails)
    }
}