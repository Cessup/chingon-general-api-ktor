package com.cessup.data.services

import at.favre.lib.crypto.bcrypt.BCrypt
import com.google.inject.Singleton

@Singleton
object Encrypt{
    fun hashPassword(password: String): String {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray())
    }

    fun verifyPassword(password: String, hashed: String): Boolean {
        val result = BCrypt.verifyer().verify(password.toCharArray(), hashed)
        return result.verified
    }
}