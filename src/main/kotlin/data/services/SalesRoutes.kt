package com.cessup.data.services

import com.cessup.domain.models.sales.Merchant
import com.cessup.domain.models.sales.Price
import com.cessup.domain.models.sales.Promotion
import com.cessup.domain.usecases.sales.AssignmentPriceUseCase
import com.cessup.domain.usecases.sales.ChangePriceUseCase
import com.cessup.domain.usecases.sales.ChangePromotionUseCase
import com.cessup.domain.usecases.sales.DeleteMerchantUseCase
import com.cessup.domain.usecases.sales.DeletePriceUseCase
import com.cessup.domain.usecases.sales.DeletePromotionUseCase
import com.cessup.domain.usecases.sales.GetPricesToMerchantUseCase
import com.cessup.domain.usecases.sales.GetPricesUseCase
import com.cessup.domain.usecases.sales.GetPromotionByIdUseCase
import com.cessup.domain.usecases.sales.GetPromotionsUseCase
import com.cessup.domain.usecases.sales.NewMerchantUseCase
import com.cessup.domain.usecases.sales.NewPromotionUseCase
import com.cessup.domain.usecases.sales.UpdateMerchantUseCase
import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.authenticate
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
import org.koin.core.Koin

/**
 * This function have all services in the Sales module
 *
 * There are All business cases about product
 *
 * @constructor [koin] the koin object use for inject the uses cases
 *
 * @author
 *     Cessup
 * @since 1.0
 */
fun Route.salesRoutes(koin: Koin) {
    val assignmentPriceUseCase = koin.get<AssignmentPriceUseCase>()
    val changePriceUseCase= koin.get<ChangePriceUseCase>()
    val changePromotionUseCase= koin.get<ChangePromotionUseCase>()
    val deleteMerchantUseCase= koin.get<DeleteMerchantUseCase>()
    val deletePriceUseCase= koin.get<DeletePriceUseCase>()
    val deletePromotionUseCase= koin.get<DeletePromotionUseCase>()
    val getPricesToMerchantUseCase= koin.get<GetPricesToMerchantUseCase>()
    val getPricesUseCase= koin.get<GetPricesUseCase>()
    val getPromotionByIdUseCase= koin.get<GetPromotionByIdUseCase>()
    val getPromotionsUseCase= koin.get<GetPromotionsUseCase>()
    val newMerchantUseCase= koin.get<NewMerchantUseCase>()
    val newPromotionUseCase= koin.get<NewPromotionUseCase>()
    val updateMerchantUseCase= koin.get<UpdateMerchantUseCase>()

    route("/sales") {
        /*
         All functions needs authorization to access to them
        */
        authenticate{
            /*
             This function create a new price.
            */
            post("/assignmentPrice/{idMerchant}") {
                try {
                    val request = call.receive<Price>()
                    val idMerchant = call.request.queryParameters["idMerchant"] ?: return@post call.respond(HttpStatusCode.BadRequest, "Missing ID")
                    val price = assignmentPriceUseCase.execute(request, ObjectId(idMerchant))
                    call.respond(HttpStatusCode.Created, price)
                }catch (e: Exception){
                    call.respondText(e.localizedMessage, status = HttpStatusCode.BadRequest)
                }
            }

            /*
             This function create a new merchant.
            */
            post("/newMerchant") {
                try {
                    val request = call.receive<Merchant>()
                    val merchant = newMerchantUseCase.execute(request)
                    call.respond(HttpStatusCode.Created, merchant)
                }catch (e: Exception){
                    call.respondText(e.localizedMessage, status = HttpStatusCode.BadRequest)
                }
            }

            /*
             This function create a new Promotion.
            */
            post("/newPromotion") {
                try {
                    val request = call.receive<Promotion>()
                    val promotion = newPromotionUseCase.execute(request)
                    call.respond(HttpStatusCode.Created, promotion)
                }catch (e: Exception){
                    call.respondText(e.localizedMessage, status = HttpStatusCode.BadRequest)
                }
            }

            /*
            The function to update to price object.
            */
            put("/changePrice/") {
                try{
                    val request = call.receive<Price>()
                    val result = changePriceUseCase.execute(request)
                    call.respond(HttpStatusCode.OK, result)
                }catch (e: Exception){
                    call.respondText(e.localizedMessage, status = HttpStatusCode.BadRequest)
                }
            }

            /*
            The function to update to price object.
            */
            put("/changePromotion/") {
                try{
                    val request = call.receive<Promotion>()
                    val result = changePromotionUseCase.execute(request)
                    call.respond(HttpStatusCode.OK, result)
                }catch (e: Exception){
                    call.respondText(e.localizedMessage, status = HttpStatusCode.BadRequest)
                }
            }

            /*
            The function to update to merchant object.
            */
            put("/updateMerchant/") {
                try{
                    val request = call.receive<Merchant>()
                    val result = updateMerchantUseCase.execute(request)
                    call.respond(HttpStatusCode.OK, result)
                }catch (e: Exception){
                    call.respondText(e.localizedMessage, status = HttpStatusCode.BadRequest)
                }
            }


            /*
             The function to delete a price
            */
            delete("/deletePrice/{idPrice}") {
                try{
                    val idPrice = call.request.queryParameters["idPrice"] ?: return@delete call.respond(HttpStatusCode.BadRequest, "Missing ID")
                    val deleted = deletePriceUseCase.execute(ObjectId(idPrice))
                    call.respond(HttpStatusCode.OK, deleted)
                }catch (e: Exception){
                    call.respondText(e.localizedMessage, status = HttpStatusCode.BadRequest)
                }
            }

            /*
             The function to delete a promotion
            */
            delete("/deletePromotion/{idPromotion}") {
                try{
                    val id = call.request.queryParameters["idPromotion"] ?: return@delete call.respond(HttpStatusCode.BadRequest, "Missing ID")
                    val deleted = deletePromotionUseCase.execute(ObjectId(id))
                    call.respond(HttpStatusCode.OK, deleted)
                }catch (e: Exception){
                    call.respondText(e.localizedMessage, status = HttpStatusCode.BadRequest)
                }
            }
            /*
             The function to delete a merchant
            */
            delete("/deletePromotion/{idMerchant}") {
                try{
                    val id = call.request.queryParameters["idMerchant"] ?: return@delete call.respond(HttpStatusCode.BadRequest, "Missing ID")
                    val deleted = deleteMerchantUseCase.execute(ObjectId(id))
                    call.respond(HttpStatusCode.OK, deleted)
                }catch (e: Exception){
                    call.respondText(e.localizedMessage, status = HttpStatusCode.BadRequest)
                }
            }

            /*
             The function find a prices by merchant.
            */
            get("/priceListToMerchant/{idMerchant}") {
                try{
                    val idMerchant = call.request.queryParameters["idMerchant"]?: return@get call.respond(HttpStatusCode.BadRequest, "Missing ID")
                    val list = getPricesToMerchantUseCase.execute(ObjectId(idMerchant))
                    call.respond(list)
                }catch (e: Exception){
                    call.respondText(e.localizedMessage, status = HttpStatusCode.BadRequest)
                }
            }

            /*
             The function find a list of prices.
            */
            get("/pricesList/") {
                try{
                    call.respond(getPricesUseCase.execute())
                }catch (e: Exception){
                    call.respondText(e.localizedMessage, status = HttpStatusCode.BadRequest)
                }
            }

            /*
             The function find a list of promotions.
            */
            get("/promotionsList/") {
                try{
                    call.respond(getPromotionsUseCase.execute())
                }catch (e: Exception){
                    call.respondText(e.localizedMessage, status = HttpStatusCode.BadRequest)
                }
            }

            /*
             The function find a promotion by id.
            */
            get("/promotion/{idPromotion}") {
                try{
                    val idPromotion = call.request.queryParameters["idPromotion"]?: return@get call.respond(HttpStatusCode.BadRequest, "Missing ID")
                    val result = getPromotionByIdUseCase.execute(ObjectId(idPromotion))
                    if(result!=null){
                        call.respond(HttpStatusCode.OK, result)
                    }else{
                        call.respond(HttpStatusCode.NotFound)
                    }
                }catch (e: Exception){
                    call.respondText(e.localizedMessage, status = HttpStatusCode.BadRequest)
                }
            }
        }
    }
}