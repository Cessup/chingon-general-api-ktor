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

@Singleton
object JwtProvider{
    private const val secret = "secret"
    private const val issuer = "ktor.io"
    private const val audience = "ktorAudience"
    private const val realm = "ktor-cacao"

    fun configure(app: Application) {
        app.install(Authentication) {
            jwt {
                realm = JwtProvider.realm
                verifier(
                    JWT.require(Algorithm.HMAC256(secret))
                        .withAudience(audience)
                        .withIssuer(issuer).build()
                )
                validate { creds -> JWTPrincipal(creds.payload) }
            }
        }
    }

    fun generateToken(username: String) =
        JWT.create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withClaim("username", username)
            .withExpiresAt(Date(System.currentTimeMillis() + 600000)) // 10 minutes
            .sign(Algorithm.HMAC256(secret))
}