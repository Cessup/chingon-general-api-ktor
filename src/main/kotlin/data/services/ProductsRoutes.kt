package com.cessup.data.services

import com.cessup.domain.usecases.products.DeleteProductUseCase
import com.cessup.domain.usecases.products.FindBySerialNumberUseCase
import com.cessup.domain.usecases.products.RegisterProductUseCase
import com.cessup.domain.usecases.products.UpdateDetailsProductUseCase
import com.cessup.domain.usecases.products.UpdateProductUseCase
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
import kotlinx.serialization.Serializable

/**
 * This function have all services in the Product module
 *
 * There are All business cases about product
 *
 * @constructor [RegisterProductUseCase] the RegisterUser is a use case about make a new products
 *
 * @author
 *     Cessup
 * @since 1.0
 */
fun Route.productsRoutes(registerProductUseCase: RegisterProductUseCase,
                         findBySerialNumberUseCase:FindBySerialNumberUseCase,
                         deleteProductUseCase:DeleteProductUseCase,
                         updateProductUseCase:UpdateProductUseCase,
                         updateDetailsProductUseCase:UpdateDetailsProductUseCase
) {
    route("/products") {
        /*
         All functions needs authorization to access to them
        */
        authenticate{
            /*
             This function create a new product with their details.
            */
            post("/register") {
                val registerProductRequest = call.receive<RegisterProductRequest>()
                val product = registerProductUseCase.execute(   registerProductRequest)
                if (product != null) {
                    call.respond(HttpStatusCode.Created, product)
                } else {
                    call.respond(HttpStatusCode.BadRequest, "Wrong to create")
                }
            }

            /*
             The function find a product by serial number.
            */
            get("/find") {
                val serialNumber = call.request.queryParameters["serialNumber"]?: return@get call.respond(HttpStatusCode.BadRequest, "Missing ID")
                val product = findBySerialNumberUseCase.execute(serialNumber)
                if (product != null) {
                    call.respond(product)
                } else {
                    call.respondText("Product not found $serialNumber", status = HttpStatusCode.NotFound)
                }
            }

            /*
             The function to delete a product
            */
            delete("/delete") {
                val id = call.request.queryParameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest, "Missing ID")
                val deleted = deleteProductUseCase.execute(id)
                if (deleted) {
                    call.respond(HttpStatusCode.OK, "Product deleted successfully")
                } else {
                    call.respond(HttpStatusCode.NotFound, "Product not found")
                }
            }

            /*
            The function to update data about product.
           */
            put("/{id}/details/") {
                val request = call.receive<RegisterProductRequest>()
                val id = call.parameters["id"] ?: return@put call.respond(HttpStatusCode.BadRequest, "Missing ID")
                if(updateProductUseCase.execute(request,id)){
                    call.respond(HttpStatusCode.OK, "Product changed successfully")
                }else{
                    call.respond(HttpStatusCode.BadRequest, "Product is not changed")
                }
            }

            /*
            The function to update data about product details.
           */
            put("/{id}/product/") {
                val request = call.receive< RegisterProductDetailsRequest>()
                val id = call.parameters["id"] ?: return@put call.respond(HttpStatusCode.BadRequest, "Missing ID")
                if(updateDetailsProductUseCase.execute(request,id)){
                    call.respond(HttpStatusCode.OK, "Product details changed successfully")
                }else{
                    call.respond(HttpStatusCode.BadRequest, "Product details is not changed")
                }
            }
        }
    }
}


/**
 * Represents a Product.
 *
 * @property serialNumber the code for identify every product
 * @property price the price is about value of product
 * @property currency the currency is a references of prices
 * @property category the category is the classification about it
 * @property subcategory the subcategory is the classification inside category about it so it is more specify
 * @property stock the stock is the count of product there are
 * @property rating the rating is a classification about of product
 * @property details All information about this product
 * @property reviews That is a list with all reviews from users
 */
@Serializable
data class RegisterProductRequest(
    val serialNumber: String,
    val price: Double,
    val currency: Double,
    val category: String,
    val subcategory: String,
    val stock: Int,
    val rating: Double,
    val details: RegisterProductDetailsRequest,
    val reviews: List<RegisterProductReviewRequest>
)

/**
 * Represents a user.
 *
 * @property name the name of the user
 * @property description the lastname of the user
 * @property model the birthday of the user
 * @property version the birthday of the user
 * @property brand the birthday of the user
 * @property dimensions the gender of the user
 * @property color the birthday of the user
 * @property tags the birthday of the user
 */
@Serializable
data class RegisterProductDetailsRequest(
    val name: String,
    val description: String,
    val model: String,
    val version: String,
    val brand: String,
    val dimensions: RegisterProductDimensionRequest,
    val color: RegisterProductColorRequest,
    val tags: List<String>
)

/**
 * Represents a Dimensions.
 *
 * @property width the width is a parameter of dimension.
 * @property height the height is a parameter of dimension.
 * @property depth the depth is a parameter of dimension.
 */
@Serializable
data class RegisterProductDimensionRequest(
    val width: Double,
    val height: Double,
    val depth: Double,
)

/**
 * Represents a Color.
 *
 * @property code the code for identify every color
 * @property name the name the color
 */
@Serializable
data class RegisterProductColorRequest (
    val code: String,
    val name: String,
)

/**
 * Represents a Dimensions.
 *
 * @property userId the id of the user from comment.
 * @property rating the rating is the value of product from user.
 * @property comment the comment is the opinion from user.
 * @property date the date is the date of made it.
 */
@Serializable
data class RegisterProductReviewRequest(
    val userId: String,
    val rating: Double,
    val comment: String,
    val date: Long,
)

