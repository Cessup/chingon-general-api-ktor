package com.cessup

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.cessup.data.exceptions.AuthorizationException
import com.cessup.data.services.Security
import com.cessup.data.services.eatable.drinkRoutes
import com.cessup.data.services.eatable.mealRoutes
import com.cessup.data.services.productsRoutes
import com.cessup.data.services.salesRoutes
import com.cessup.data.services.userRoutes
import com.cessup.di.appModule
import com.cessup.di.useCaseModule
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.gson.gson
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.routing.routing
import io.ktor.server.plugins.cors.routing.CORS
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import org.koin.core.context.startKoin

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {

    val koin = startKoin {
        modules(appModule, useCaseModule)
    }.koin

    install(ContentNegotiation) {
        gson{
            setPrettyPrinting()
            serializeNulls()
        }
    }
    install(CORS) { anyHost() }

    install(StatusPages) {
        exception<Throwable> { call, cause ->
            if(cause is AuthorizationException) {
                call.respondText(text = "403: $cause" , status = HttpStatusCode.Forbidden)
            } else {
                call.respond(HttpStatusCode.BadRequest, cause.message ?: "")
                //call.respondText(text = "500: $cause" , status = HttpStatusCode.InternalServerError)
            }
        }
    }

    install(Authentication) {
        jwt {
            val blacklistedTokens = mutableSetOf<String>()
            realm = Security.REALM
            verifier(
                JWT.require(Algorithm.HMAC256(Security.SECRET))
                    .withAudience(Security.AUDIENCE)
                    .withIssuer(Security.ISSUER).build()
            )

            validate { credential ->
                val token = credential.payload.getClaim("id").asString()
                if (!token.isNullOrBlank()) JWTPrincipal(credential.payload) else null
                if (!blacklistedTokens.contains(token)) JWTPrincipal(credential.payload) else null
            }
        }
    }

    routing {
        userRoutes(koin)
        productsRoutes(koin)
        drinkRoutes(koin)
        mealRoutes(koin)
        salesRoutes(koin)
    }
}
