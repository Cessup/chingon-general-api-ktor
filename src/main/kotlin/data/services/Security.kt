package com.cessup.data.services

import at.favre.lib.crypto.bcrypt.BCrypt
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.bson.types.ObjectId
import java.util.Date

/**
 * Encrypt is object about data encryption
 *
 * There are two functions for help about it
 *
 * @author
 *     Cessup
 * @since 1.0
 */
object Security{
    const val SECRET = "secret"
    const val ISSUER = "ktor.io"
    const val AUDIENCE = "ktorAudience"
    const val REALM = "ktor-chingon"

    /**
     * This function generate a token to the connection
     *
     * @param userId the user id is the data for create a unique token
     */
    fun generateToken(userId: ObjectId): String? =
        JWT.create()
            .withAudience(AUDIENCE)
            .withIssuer(ISSUER)
            .withClaim("id", userId.toString())
            .withExpiresAt(Date(System.currentTimeMillis() + 5 * 60 * 1000)) //5 min
            .sign(Algorithm.HMAC256(SECRET))

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