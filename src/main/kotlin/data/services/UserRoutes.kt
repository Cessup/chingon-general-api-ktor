package com.cessup.data.services

import com.cessup.domain.usecases.session.AuthenticateUseCase
import com.cessup.domain.usecases.session.DeleteUserUseCase
import com.cessup.domain.usecases.session.GetUserUseCase
import com.cessup.domain.usecases.session.RegisterUserUseCase
import com.cessup.domain.usecases.session.ResetPasswordUseCase
import com.cessup.domain.usecases.session.UpdateUserDetailsUseCase
import io.ktor.http.*
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route
import kotlinx.serialization.Serializable

/**
 * This function have all services in the User module
 *
 * There are All business cases about users because it
 *
 * @constructor [RegisterUserUseCase] the RegisterUser is a use case about make a new users
 * @constructor [AuthenticateUseCase] the RegisterUser is a use case about make a new users
 * @constructor [RegisterUserUseCase] the RegisterUser is a use case about make a new users
 * @constructor [ResetPasswordUseCase] the RegisterUser is a use case about make a new users
 * @constructor [GetUserUseCase] the RegisterUser is a use case about make a new users
 * @constructor [DeleteUserUseCase] the RegisterUser is a use case about make a new users
 * @constructor [JwtProvider] the RegisterUser is a use case about make a new users
 *
 * @author
 *     Cessup
 * @since 1.0
 */
fun Route.userRoutes(registerUser: RegisterUserUseCase,
                     authenticate: AuthenticateUseCase,
                     resetPassword: ResetPasswordUseCase,
                     getUser: GetUserUseCase,
                     updateUserDetails : UpdateUserDetailsUseCase,
                     deleteUser: DeleteUserUseCase,
                     jwt: JwtProvider) {
    route("/session") {
        /*
         This function create a new user with their details.
        */
        post("/register") {
            val userEntity = call.receive<RegisterUserRequest>()
            val user = registerUser.execute(userEntity)
            if (user!=null) {
                val token = jwt.generateToken(user.id)
                call.respond(HttpStatusCode.Created,mapOf("token" to token))
            } else {
                call.respond(HttpStatusCode.BadRequest, "Wrong to create")
            }
        }

        /*
         This function gives an authorization to access
        */
        post("/authenticate") {
            val req = call.receive<AuthenticateRequest>()
            val user = authenticate.execute(email = req.email, password = req.password)
            if (user!=null) {
                val token = jwt.generateToken(user.id)
                call.respond(mapOf("token" to token))
            } else {
                call.respond(HttpStatusCode.Unauthorized, "Invalid credentials")
            }
        }

        /*
         The function to rest password if the user forgot it
        */
        put("/resetPassword") {
            val req = call.receive<ResetPasswordRequest>()
            if(resetPassword.execute(req.email, req.password)){
                call.respond(HttpStatusCode.OK, "Password changed successfully")
            }else{
                call.respond(HttpStatusCode.BadRequest, "Password is not changed")
            }
        }

        /*
         All functions needs authorization to access to them
        */
        authenticate{
            /*
             The function to access user data
            */
            get("/profile") {
                val principal = call.principal<JWTPrincipal>()
                val id = principal!!.payload.getClaim("id").asString()
                val user = getUser.execute(id)
                if (user != null) {
                    call.respond(user)
                } else {
                    call.respondText("User not found $id", status = HttpStatusCode.NotFound)
                }
            }

            /*
             The function to rest password if the user forgot it
            */
            put("/{id}/userDetails/") {
                val request = call.receive<RegisterUserDetailsRequest>()
                val id = call.parameters["id"] ?: return@put call.respond(HttpStatusCode.BadRequest, "Missing ID")
                if(updateUserDetails.execute(request,id)){
                    call.respond(HttpStatusCode.OK, "User details changed successfully")
                }else{
                    call.respond(HttpStatusCode.BadRequest, "User details is not changed")
                }
            }

            /*
             The function to delete account
            */
            delete("/deleteAccount") {
                val principal = call.principal<JWTPrincipal>()
                val id = principal!!.payload.getClaim("id").asString()
                val deleted = deleteUser.execute(id)
                if (deleted) {
                    call.respond(HttpStatusCode.OK, "User deleted successfully")
                } else {
                    call.respond(HttpStatusCode.NotFound, "User not found")
                }
            }

            post("/closeSession") {
                call.response.cookies.append(
                    Cookie(
                        name = "refresh_token",
                        value = "",
                        maxAge = 0,
                        path = "/",
                        httpOnly = true
                    )
                )
                call.respond(HttpStatusCode.OK, "Logged out")
            }
        }
    }
}

/**
 * Request to Authenticate the user
 *
 * @property email the email the user belongs to
 * @property password the password of the user for account
 */
@Serializable
data class AuthenticateRequest(val email: String, val password: String)

/**
 * Request to Register a new user
 *
 * @property email the email the user belongs to
 * @property phone the phone of the user for account
 * @property password the password of the user for account
 * @property nickname All information about this user
 */
@Serializable
data class RegisterUserRequest(val email: String, val phone: String, val password: String, val nickname:String, val details:RegisterUserDetailsRequest)

/**
 * Request to Register Details of a new user
 *
 * @property name All information about this user
 * @property lastName All information about this user
 * @property address All information about this user
 * @property gender All information about this user
 * @property birthdate All information about this user
 */
@Serializable
data class RegisterUserDetailsRequest(val name:String, val lastName:String,val address: String, val gender:String, val birthdate:Long)


/**
 * Request to Reset Password of the user
 *
 * @property email the email the user belongs to
 * @property password the password of the user for account
 */
@Serializable
data class ResetPasswordRequest(val email: String, val password: String)