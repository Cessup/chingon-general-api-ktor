package com.cessup.routes

import com.cessup.data.exceptions.AuthorizationException
import com.cessup.data.services.AuthenticateRequest
import io.ktor.http.*
import io.ktor.server.testing.*
import io.ktor.server.routing.*
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.serialization.json.Json
import org.junit.Test
import kotlin.test.assertEquals
import com.cessup.data.services.JwtProvider
import com.cessup.data.services.RegisterUserDetailsRequest
import com.cessup.data.services.RegisterUserRequest
import com.cessup.data.services.userRoutes
import com.cessup.domain.models.session.UserDetails
import com.cessup.domain.usecases.session.AuthenticateUseCase
import com.cessup.domain.usecases.session.DeleteUserUseCase
import com.cessup.domain.usecases.session.GetUserUseCase
import com.cessup.domain.usecases.session.RegisterUserUseCase
import com.cessup.domain.usecases.session.ResetPasswordUseCase
import com.cessup.domain.usecases.session.UpdateUserDetailsUseCase
import domain.models.User
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.gson.*
import io.ktor.server.application.install
import io.ktor.server.plugins.cors.routing.CORS
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond
import io.ktor.server.response.respondText

class UserRoutesTest {
    private val registerUseCase = mockk<RegisterUserUseCase>()
    private val authenticateUseCase = mockk<AuthenticateUseCase>()
    val resetPassword = mockk<ResetPasswordUseCase>()
    val getUser = mockk<GetUserUseCase>()
    val updateUserDetails = mockk<UpdateUserDetailsUseCase>()
    val deleteUser = mockk<DeleteUserUseCase>()
    val authenticateRequest = AuthenticateRequest("cessupx@gmail.com", "SecretWord.00")
    val registerRequest = RegisterUserRequest("cessupx@gmail.com", "5511223344","SecretWord.00","exampleNick",
        RegisterUserDetailsRequest("Jhon","lastName","address","male",1722052943876))

    val user = User(
        "688705e5dd70e2198eb31b6e",
        registerRequest.email,
        registerRequest.phone,
        registerRequest.password,
        UserDetails(
            "688705e5dd70e2198eb31b6f",
            registerRequest.details.name,
            registerRequest.details.lastName,
            registerRequest.details.address,
            registerRequest.details.gender,
            registerRequest.details.birthdate
        ),
    )

    @Test
    fun testRegisterReturnsCreated() = testApplication {
        coEvery { registerUseCase.execute(registerRequest) } returns
                user
        application {
            this@application.install(ContentNegotiation) {
                gson{
                    setPrettyPrinting()
                    serializeNulls()
                }
            }
            val jwt = JwtProvider
            jwt.configure(this)

            routing {
                userRoutes(registerUseCase, authenticateUseCase, resetPassword, getUser, updateUserDetails,deleteUser,jwt)
            }
        }

        val response = client.post("/session/register") {
            contentType(ContentType.Application.Json)
            setBody(Json.encodeToString(registerRequest))
        }

        assertEquals(HttpStatusCode.Created, response.status)
        assert(response.bodyAsText().contains("id"))
    }

    @Test
    fun testAuthenticateReturnsToken() = testApplication {
        coEvery { authenticateUseCase.execute(authenticateRequest.email, authenticateRequest.password) } returns
                user
        application {
            val jwt = JwtProvider
            jwt.configure(this)
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
                    }
                }
            }

            routing {
                userRoutes(registerUseCase, authenticateUseCase, resetPassword, getUser, updateUserDetails,deleteUser,jwt)
            }
        }

        val response = client.post("/session/authenticate") {
            contentType(ContentType.Application.Json)
            setBody(Json.encodeToString(authenticateRequest))
        }

        assertEquals(HttpStatusCode.OK, response.status)
        assert(response.bodyAsText().contains("token"))
    }

    @Test
    fun testGetUserReturnsUser() = testApplication {
        coEvery { authenticateUseCase.execute(authenticateRequest.email, authenticateRequest.password) } returns
                user
        coEvery { getUser.execute(user.id) } returns
                user
        application {
            val jwt = JwtProvider
            jwt.configure(this)
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
                    }
                }
            }

            routing {
                userRoutes(registerUseCase, authenticateUseCase, resetPassword, getUser, updateUserDetails,deleteUser,jwt)
            }
        }

        val response = client.post("/session/profile") {
            contentType(ContentType.Application.Json)
        }

        assertEquals(HttpStatusCode.OK, response.status)
        assert(response.bodyAsText().contains("id"))
    }
}