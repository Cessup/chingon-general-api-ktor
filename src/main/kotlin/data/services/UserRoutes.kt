package com.cessup.data.services

import com.cessup.domain.usecases.SignInUseCase
import com.cessup.domain.usecases.SignUpUseCase
import io.ktor.http.*
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import kotlinx.serialization.Serializable

fun Route.userRoutes(signUp: SignUpUseCase, signIn: SignInUseCase, jwt: JwtProvider) {
    route("/users") {
        post("/signup") {
            val req = call.receive<Credentials>()
            val user = signUp.execute(req.email, req.password)
            call.respond(HttpStatusCode.Created, mapOf("id" to user.id))
        }
        post("/signin") {
            val req = call.receive<Credentials>()
            val user = signIn.execute(email = req.email, password = req.password)
            if (user!=null) {
                val token = jwt.generateToken(user.id)
                call.respond(mapOf("token" to token))
            } else {
                call.respond(HttpStatusCode.Unauthorized, "Invalid credentials")
            }
        }
        authenticate{
            get("/me") {
                val principal = call.principal<JWTPrincipal>()
                val userId = principal!!.subject
                call.respond(mapOf("userId" to userId))
            }
        }
    }
}

@Serializable
data class Credentials(val email: String, val password: String)