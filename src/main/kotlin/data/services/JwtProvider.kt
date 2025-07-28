package com.cessup.data.services

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.google.inject.Singleton
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt
import java.util.Date

/**
 * JWT is an object about security
 *
 * There are configurations for this framework
 *
 * @author
 *     Cessup
 * @since 1.0
 */
@Singleton
object JwtProvider{
    private const val secret = "secret"
    private const val issuer = "ktor.io"
    private const val audience = "ktorAudience"
    private const val realm = "ktor-cacao"
    val blacklistedTokens = mutableSetOf<String>()

    /**
     * This function start to configure the framework
     *
     * @param app the app is the context to install this module
     */
    fun configure(app: Application) {
        app.install(Authentication) {
            jwt {
                realm = JwtProvider.realm
                verifier(
                    JWT.require(Algorithm.HMAC256(secret))
                        .withAudience(audience)
                        .withIssuer(issuer).build()
                )

                validate { credential ->
                    val token = credential.payload.getClaim("id").asString()
                    if (blacklistedTokens.contains(token)) null else JWTPrincipal(credential.payload)
                    if (!token.isNullOrBlank()) JWTPrincipal(credential.payload) else null
                }
            }
        }
    }

    /**
     * This function generate a token to the connection
     *
     * @param userId the user id is the data for create a unique token
     */
    fun generateToken(userId: String): String? =
        JWT.create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withClaim("id", userId)
            .withExpiresAt(Date(System.currentTimeMillis() + 5 * 60 * 1000)) //5 min
            .sign(Algorithm.HMAC256(secret))

}