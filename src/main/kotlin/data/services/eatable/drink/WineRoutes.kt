package com.cessup.data.services.eatable.drink

import com.cessup.domain.models.eatable.drink.Wine
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
 * @constructor [NewDrinkUseCase] the NewWineUseCase is a use case to make a new products
 * @constructor [UpdateDrinkUseCase] the UpdateWineUseCase is a use case to update a wine
 * @constructor [DeleteDrinkUseCase] the DeleteWineUseCase is a use case to delete a wine
 * @constructor [GetDrinksUseCase] the GetWinesUseCase is a use case to give a list of wines
 *
 * @author
 *     Cessup
 * @since 1.0
 */
fun Route.wineRoutes(
    newDrinkUseCase: NewDrinkUseCase,
    updateDrinkUseCase: UpdateDrinkUseCase,
    deleteDrinkUseCase: DeleteDrinkUseCase,
    getDrinksUseCase: GetDrinksUseCase,
){
    route("eatable/drinks/wine") {
        /*
         All functions needs authorization to access to them
        */

        /*
         This function create a new product with their details.
        */
        post("/new") {
            val wine = call.receive<Wine>()
            val result: Boolean = newDrinkUseCase.execute(wine) { insertWine(it) }
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
            val drinks = getDrinksUseCase.execute{ getWines() }
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
            val deleted = deleteDrinkUseCase.execute(id){ deleteWine(id)}
            if (deleted) {
                call.respond(HttpStatusCode.OK, "Wine deleted successfully")
            } else {
                call.respond(HttpStatusCode.NotFound, "Wine not found")
            }
        }

        /*
        The function to update data about product details.
       */
        put("/update") {
            val wine = call.receive<Wine>()
            val result: Boolean = updateDrinkUseCase.execute(wine) { updateWine(it) }
            if(result){
                call.respond(HttpStatusCode.OK, "Eatable changed successfully")
            }else{
                call.respond(HttpStatusCode.BadRequest, "Eatable is not changed")
            }
        }
    }
}