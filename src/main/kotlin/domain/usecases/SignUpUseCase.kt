package com.cessup.domain.usecases

import com.cessup.domain.repositories.UserRepository
import domain.models.User
import com.cessup.data.services.Encrypt
import com.google.inject.Inject

class SignUpUseCase @Inject constructor(private val userRepository: UserRepository, private val security: Encrypt) {
    suspend fun execute(email: String, password: String): User {
        userRepository.findByEmail(email)?.let { throw IllegalArgumentException("Email already in use") }
        return userRepository.insert(email, security.hashPassword(password))
    }
}