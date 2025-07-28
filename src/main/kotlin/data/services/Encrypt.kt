package com.cessup.data.services

import at.favre.lib.crypto.bcrypt.BCrypt
import com.google.inject.Singleton

/**
 * Encrypt is object about data encryption
 *
 * There are two functions for help about it
 *
 * @author
 *     Cessup
 * @since 1.0
 */
@Singleton
object Encrypt{
    /**
     * This function encrypt the password to use in the services
     *
     * @param password the app is the context to install this module
     */
    fun hashPassword(password: String): String {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray())
    }

    /**
     * This function verify password to identify is well or not.
     *
     * @param password the password is value from user to access this system.
     * @param hashed the hashed is the value with encryption
     */
    fun verifyPassword(password: String, hashed: String): Boolean {
        val result = BCrypt.verifyer().verify(password.toCharArray(), hashed)
        return result.verified
    }
}