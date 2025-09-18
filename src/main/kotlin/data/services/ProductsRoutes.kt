package com.cessup.data.services

import com.cessup.domain.models.products.Product
import com.cessup.domain.models.products.ProductDetails
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
                val registerProductRequest = call.receive<Product>()
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
                val request = call.receive<Product>()
                if(updateProductUseCase.execute(request)){
                    call.respond(HttpStatusCode.OK, "Product changed successfully")
                }else{
                    call.respond(HttpStatusCode.BadRequest, "Product is not changed")
                }
            }

            /*
            The function to update data about product details.
           */
            put("/{id}/product/") {
                val request = call.receive<ProductDetails>()
                if(updateDetailsProductUseCase.execute(request)){
                    call.respond(HttpStatusCode.OK, "Product details changed successfully")
                }else{
                    call.respond(HttpStatusCode.BadRequest, "Product details is not changed")
                }
            }
        }
    }
}

