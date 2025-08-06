package com.cessup.data.services

import com.cessup.domain.usecases.products.RegisterProductUseCase
import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import kotlinx.serialization.Serializable

/**
 * This function have all services in the Product module
 *
 * There are All business cases about product
 *
 * @constructor [RegisterProductUseCase] the RegisterUser is a use case about make a new users
 * @constructor [JwtProvider] the RegisterUser is a use case about make a new users
 *
 * @author
 *     Cessup
 * @since 1.0
 */
fun Route.productsRoutes(registerProductUseCase: RegisterProductUseCase,
                         jwt: JwtProvider) {
    route("/product") {
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
                    val token = jwt.generateToken(product.id)
                    call.respond(HttpStatusCode.Created,mapOf("token" to token))
                } else {
                    call.respond(HttpStatusCode.BadRequest, "Wrong to create")
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

