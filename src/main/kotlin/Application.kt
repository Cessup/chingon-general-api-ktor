package com.cessup

import com.cessup.data.exceptions.AuthorizationException
import com.cessup.data.services.JwtProvider
import com.cessup.data.services.eatable.drinkRoutes
import com.cessup.data.services.eatable.mealRoutes
import com.cessup.data.services.productsRoutes
import com.cessup.data.services.userRoutes
import com.cessup.di.AppModule
import com.cessup.domain.usecases.eatable.drink.DeleteDrinkUseCase
import com.cessup.domain.usecases.eatable.drink.GetDrinksUseCase
import com.cessup.domain.usecases.eatable.drink.NewDrinkUseCase
import com.cessup.domain.usecases.eatable.drink.UpdateDrinkUseCase
import com.cessup.domain.usecases.eatable.meal.DeleteMealUseCase
import com.cessup.domain.usecases.eatable.meal.GetMealsUseCase
import com.cessup.domain.usecases.eatable.meal.NewMealUseCase
import com.cessup.domain.usecases.eatable.meal.UpdateMealUseCase
import com.cessup.domain.usecases.products.DeleteProductUseCase
import com.cessup.domain.usecases.products.FindBySerialNumberUseCase
import com.cessup.domain.usecases.products.RegisterProductUseCase
import com.cessup.domain.usecases.products.UpdateDetailsProductUseCase
import com.cessup.domain.usecases.products.UpdateProductUseCase
import com.cessup.domain.usecases.session.AuthenticateUseCase
import com.cessup.domain.usecases.session.DeleteUserUseCase
import com.cessup.domain.usecases.session.GetUserUseCase
import com.cessup.domain.usecases.session.RegisterUserUseCase
import com.cessup.domain.usecases.session.ResetPasswordUseCase
import com.cessup.domain.usecases.session.UpdateUserDetailsUseCase
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

    val registerProduct = injector.getInstance(RegisterProductUseCase::class.java)
    val findProduct = injector.getInstance(FindBySerialNumberUseCase::class.java)
    val deleteProduct = injector.getInstance(DeleteProductUseCase::class.java)
    val updateProduct = injector.getInstance(UpdateProductUseCase::class.java)
    val updateProductDetails = injector.getInstance(UpdateDetailsProductUseCase::class.java)

    val newDrinkUseCase = injector.getInstance(NewDrinkUseCase::class.java)
    val updateDrinkUseCase = injector.getInstance(UpdateDrinkUseCase::class.java)
    val deleteDrinkUseCase = injector.getInstance(DeleteDrinkUseCase::class.java)
    val getDrinksUseCase = injector.getInstance(GetDrinksUseCase::class.java)

    val newMealUseCase = injector.getInstance(NewMealUseCase::class.java)
    val updateMealUseCase = injector.getInstance(UpdateMealUseCase::class.java)
    val deleteMealUseCase = injector.getInstance(DeleteMealUseCase::class.java)
    val getMealsUseCase = injector.getInstance(GetMealsUseCase::class.java)

    routing {
        userRoutes(register, authentication,resetPassword,getUser, updateUserDetails,deleteUser, jwt)
        productsRoutes(registerProduct,findProduct,deleteProduct,updateProduct,updateProductDetails)
        drinkRoutes(newDrinkUseCase,updateDrinkUseCase,deleteDrinkUseCase,getDrinksUseCase)
        mealRoutes(newMealUseCase,updateMealUseCase,deleteMealUseCase,getMealsUseCase)

    }
}
