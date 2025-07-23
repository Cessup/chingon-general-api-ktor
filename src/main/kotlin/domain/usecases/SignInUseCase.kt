package com.cessup.domain.usecases

import com.cessup.domain.repositories.UserRepository
import com.cessup.data.services.Encrypt
import com.google.inject.Inject
import domain.models.User

class SignInUseCase @Inject constructor(val userRepository: UserRepository) {
    suspend fun execute(email: String, password: String): User? {
        val user = userRepository.findByEmail(email) ?: return null
        Encrypt.verifyPassword(password, Encrypt.hashPassword(user.password))
        return user
    }
}