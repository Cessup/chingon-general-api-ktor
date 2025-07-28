package com.cessup

import com.cessup.data.exceptions.AuthorizationException
import com.cessup.data.services.JwtProvider
import com.cessup.data.services.userRoutes
import com.cessup.di.AppModule
import com.cessup.domain.usecases.AuthenticateUseCase
import com.cessup.domain.usecases.DeleteUserUseCase
import com.cessup.domain.usecases.GetUserUseCase
import com.cessup.domain.usecases.RegisterUserUseCase
import com.cessup.domain.usecases.ResetPasswordUseCase
import com.cessup.domain.usecases.UpdateUserDetailsUseCase
import com.google.inject.Guice
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.gson.gson
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.routing.routing
import io.ktor.server.plugins.cors.routing.CORS
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import kotlin.jvm.java

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
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

    val jwt = JwtProvider
    jwt.configure(this)

    val injector = Guice.createInjector(AppModule())

    val register = injector.getInstance(RegisterUserUseCase::class.java)
    val authentication = injector.getInstance(AuthenticateUseCase::class.java)
    val resetPassword = injector.getInstance(ResetPasswordUseCase::class.java)
    val getUser = injector.getInstance(GetUserUseCase::class.java)
    val updateUserDetails = injector.getInstance(UpdateUserDetailsUseCase::class.java)
    val deleteUser = injector.getInstance(DeleteUserUseCase::class.java)

    routing {
        userRoutes(register, authentication,resetPassword,getUser, updateUserDetails,deleteUser, jwt)
    }
}
