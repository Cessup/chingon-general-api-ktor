package com.cessup.data.services.eatable.drink

import com.cessup.domain.models.eatable.drink.Coffee
import com.cessup.domain.usecases.eatable.drink.DeleteDrinkUseCase
import com.cessup.domain.usecases.eatable.drink.GetDrinksUseCase
import com.cessup.domain.usecases.eatable.drink.NewDrinkUseCase
import com.cessup.domain.usecases.eatable.drink.UpdateDrinkUseCase
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route
import org.bson.types.ObjectId

/**
 * This function have all services in the Product module
 *
 * There are All business cases about product
 *
 * @constructor [NewDrinkUseCase] the NewCoffeeUseCase is a use case to make a new products
 * @constructor [UpdateDrinkUseCase] the UpdateCoffeeUseCase is a use case to update a coffee
 * @constructor [DeleteDrinkUseCase] the DeleteCoffeeUseCase is a use case to delete a coffee
 * @constructor [GetDrinksUseCase] the GetCoffeesUseCase is a use case to give a list of coffees
 *
 * @author
 *     Cessup
 * @since 1.0
 */
fun Route.coffeeRoutes(
    newDrinkUseCase: NewDrinkUseCase,
    updateDrinkUseCase: UpdateDrinkUseCase,
    deleteDrinkUseCase: DeleteDrinkUseCase,
    getDrinksUseCase: GetDrinksUseCase,
){
    route("eatable/drinks/coffee") {
        /*
         All functions needs authorization to access to them
        */

        /*
         This function create a new product with their details.
        */
        post("/new") {
            val coffee = call.receive<Coffee>()
            val result: Boolean = newDrinkUseCase.execute(coffee) { insertCoffee(it) }
            if (result) {
                call.respond(HttpStatusCode.Created, "Success")
            } else {
                call.respond(HttpStatusCode.BadRequest, "Wrong to create")
            }
        }

        /*
         The function give a list of drinks.
        */
        get("/get") {
            val drinks = getDrinksUseCase.execute{ getCoffees() }
            if (drinks != null) {
                call.respond(drinks)
            } else {
                call.respondText("Items not found", status = HttpStatusCode.NotFound)
            }
        }

        /*
         The function to delete a product
        */
        delete("/delete") {
            val idStr = call.request.queryParameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest, "Missing ID")
            val id = ObjectId(idStr)
            val deleted = deleteDrinkUseCase.execute(id){ deleteCoffee(id)}
            if (deleted) {
                call.respond(HttpStatusCode.OK, "Coffee deleted successfully")
            } else {
                call.respond(HttpStatusCode.NotFound, "Coffee not found")
            }
        }

        /*
        The function to update data about product details.
       */
        put("/update") {
            val coffee = call.receive<Coffee>()
            val result: Boolean = updateDrinkUseCase.execute(coffee) { updateCoffee(it) }
            if(result){
                call.respond(HttpStatusCode.OK, "Eatable changed successfully")
            }else{
                call.respond(HttpStatusCode.BadRequest, "Eatable is not changed")
            }
        }
    }
}