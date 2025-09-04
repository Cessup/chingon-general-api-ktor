package com.cessup.data.services.eatable.meal

import com.cessup.domain.models.eatable.meal.Wrap
import com.cessup.domain.usecases.eatable.meal.DeleteMealUseCase
import com.cessup.domain.usecases.eatable.meal.GetMealsUseCase
import com.cessup.domain.usecases.eatable.meal.NewMealUseCase
import com.cessup.domain.usecases.eatable.meal.UpdateMealUseCase
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
 * @constructor [NewMealUseCase] the NewBeerUseCase is a use case to make a new products
 * @constructor [UpdateMealUseCase] the UpdateBeerUseCase is a use case to update a beer
 * @constructor [DeleteMealUseCase] the DeleteBeerUseCase is a use case to delete a beer
 * @constructor [GetMealsUseCase] the GetBeersUseCase is a use case to give a list of beers
 *
 * @author
 *     Cessup
 * @since 1.0
 */
fun Route.wrapRoutes(
    newMealUseCase: NewMealUseCase,
    updateMealUseCase: UpdateMealUseCase,
    deleteMealUseCase: DeleteMealUseCase,
    getMealsUseCase: GetMealsUseCase,
){
    route("eatable/meals/wrap") {
        /*
         All functions needs authorization to access to them
        */

        /*
         This function create a new product with their details.
        */
        post("/new") {
            val beer = call.receive<Wrap>()
            val result: Boolean = newMealUseCase.execute(beer) { insertWrap(it) }
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
            val drinks = getMealsUseCase.execute{ getWraps() }
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
            val deleted = deleteMealUseCase.execute(id){ deleteWrap(id)}
            if (deleted) {
                call.respond(HttpStatusCode.OK, "Wrap deleted successfully")
            } else {
                call.respond(HttpStatusCode.NotFound, "Wrap not found")
            }
        }

        /*
        The function to update data about product details.
       */
        put("/update") {
            val beer = call.receive<Wrap>()
            val result: Boolean = updateMealUseCase.execute(beer) { updateWrap(it) }
            if(result){
                call.respond(HttpStatusCode.OK, "Eatable changed successfully")
            }else{
                call.respond(HttpStatusCode.BadRequest, "Eatable is not changed")
            }
        }
    }
}