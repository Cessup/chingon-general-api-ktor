package com.cessup.data.services.eatable.drink

import com.cessup.domain.models.eatable.drink.Liqueur
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
 * @constructor [NewDrinkUseCase] the NewLiqueurUseCase is a use case to make a new products
 * @constructor [UpdateDrinkUseCase] the UpdateLiqueurUseCase is a use case to update a liqueur
 * @constructor [DeleteDrinkUseCase] the DeleteLiqueurUseCase is a use case to delete a liqueur
 * @constructor [GetDrinksUseCase] the GetLiqueursUseCase is a use case to give a list of liqueurs
 *
 * @author
 *     Cessup
 * @since 1.0
 */
fun Route.liqueurRoutes(
    newDrinkUseCase: NewDrinkUseCase,
    updateDrinkUseCase: UpdateDrinkUseCase,
    deleteDrinkUseCase: DeleteDrinkUseCase,
    getDrinksUseCase: GetDrinksUseCase,
){
    route("eatable/drinks/liqueur") {
        /*
         All functions needs authorization to access to them
        */

        /*
         This function create a new product with their details.
        */
        post("/new") {
            val liqueur = call.receive<Liqueur>()
            val result: Boolean = newDrinkUseCase.execute(liqueur) { insertLiqueur(it) }
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
            val drinks = getDrinksUseCase.execute{ getLiqueurs() }
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
            val deleted = deleteDrinkUseCase.execute(id){ deleteLiqueur(id)}
            if (deleted) {
                call.respond(HttpStatusCode.OK, "Liqueur deleted successfully")
            } else {
                call.respond(HttpStatusCode.NotFound, "Liqueur not found")
            }
        }

        /*
        The function to update data about product details.
       */
        put("/update") {
            val liqueur = call.receive<Liqueur>()
            val result: Boolean = updateDrinkUseCase.execute(liqueur) { updateLiqueur(it) }
            if(result){
                call.respond(HttpStatusCode.OK, "Eatable changed successfully")
            }else{
                call.respond(HttpStatusCode.BadRequest, "Eatable is not changed")
            }
        }
    }
}