package com.cessup.routes

import io.ktor.http.*
import io.ktor.server.testing.*
import io.ktor.server.routing.*
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.serialization.json.Json
import org.junit.Test
import kotlin.test.assertEquals
import com.cessup.data.services.Credentials
import com.cessup.data.services.JwtProvider
import com.cessup.data.services.userRoutes
import com.cessup.domain.usecases.SignInUseCase
import com.cessup.domain.usecases.SignUpUseCase
import domain.models.User
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.gson.*
import io.ktor.server.application.install

class UserRoutesTest {

    private val signUpUseCase = mockk<SignUpUseCase>()
    private val signInUseCase = mockk<SignInUseCase>()
    val creds = Credentials("cessupx@gmail.com", "pass123")

    @Test
    fun testSignupReturnsCreated() = testApplication {
        coEvery { signUpUseCase.execute(creds.email, creds.password) } returns
                User(
                    "68800c5ac8d03b1550067923",
                    creds.email,
                    "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJrdG9yQXVkaWVuY2UiLCJpc3MiOiJrdG9yLmlvIiwidXNlcm5hbWUiOiIiLCJleHAiOjE3NTMyMDU5ODB9.LeuiObtsaayX12nvlA_aICyzdKj3Amf7UDaKPookro0"
                )


        application {
            this@application.install(ContentNegotiation) {
                gson()
            }
            val jwt = JwtProvider
            jwt.configure(this)

            routing {
                userRoutes(signUpUseCase, signInUseCase,jwt)
            }
        }

        val response = client.post("/users/signup") {
            contentType(ContentType.Application.Json)
            setBody(Json.encodeToString(creds))
        }

        assertEquals(HttpStatusCode.Created, response.status)
        assert(response.bodyAsText().contains("id"))

    }

    @Test
    fun testSigninReturnsToken() = testApplication {
        coEvery { signInUseCase.execute(creds.email, creds.password) } returns
                User("68800c5ac8d03b1550067923", creds.email, "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJrdG9yQXVkaWVuY2UiLCJpc3MiOiJrdG9yLmlvIiwidXNlcm5hbWUiOiIiLCJleHAiOjE3NTMyMDU5ODB9.LeuiObtsaayX12nvlA_aICyzdKj3Amf7UDaKPookro0")
        coEvery { signInUseCase.userRepository.findByEmail(creds.email) } returns
                User("68800c5ac8d03b1550067923", creds.email, "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJrdG9yQXVkaWVuY2UiLCJpc3MiOiJrdG9yLmlvIiwidXNlcm5hbWUiOiIiLCJleHAiOjE3NTMyMDU5ODB9.LeuiObtsaayX12nvlA_aICyzdKj3Amf7UDaKPookro0")


        application {
            val jwt = JwtProvider
            jwt.configure(this)

            this@testApplication.install(ContentNegotiation) { gson() }

            routing {
                userRoutes(signUpUseCase, signInUseCase,jwt)
            }
        }

        val response = client.post("/users/signin") {
            contentType(ContentType.Application.Json)
            setBody(Json.encodeToString(creds))
        }

        assertEquals(HttpStatusCode.OK, response.status)
        assert(response.bodyAsText().contains("token"))
    }
}