package com.cessup.domain.usecases.products

import com.cessup.data.models.others.ColorEntity
import com.cessup.data.models.others.DimensionsEntity
import com.cessup.data.models.others.ReviewEntity
import com.cessup.data.models.products.ProductDetailsEntity
import com.cessup.data.models.products.ProductEntity
import com.cessup.data.services.Encrypt
import com.cessup.data.services.RegisterProductRequest
import com.cessup.domain.models.products.Product
import com.cessup.domain.repositories.ProductRepository
import com.cessup.domain.repositories.UserRepository
import com.google.inject.Inject
import domain.models.User
import org.litote.kmongo.newId

/**
 * Register of user in the system.
 *
 * This class make register of user information such as name, email, and age.
 *
 * @constructor Receiver a [UserRepository] and a [Encrypt] object because use it to origin data.
 * @author
 *     Cessup
 * @since 1.0
 */
class RegisterProductUseCase @Inject constructor(private val productRepository: ProductRepository) {

    /**
     * Returns new product.
     *
     * @param RegisterProductRequest this object got information about the account like email,phoneNumber,nickname,password, etc.
     * @return A new [User] from the previously entered credentials
     */
    suspend fun execute(registerRequest: RegisterProductRequest): Product? {
        productRepository.findProductBySerialNumber(registerRequest.serialNumber)?.let { throw IllegalArgumentException("Email already in use") }

        val productEntity = ProductEntity(
            newId(),
            registerRequest.serialNumber,
            registerRequest.price,
            registerRequest.currency,
            registerRequest.category,
            registerRequest.subcategory,
            registerRequest.stock,
            registerRequest.rating,
            ProductDetailsEntity(
                newId(),
                registerRequest.details.name,
                registerRequest.details.description,
                registerRequest.details.model,
                registerRequest.details.version,
                registerRequest.details.brand,
                DimensionsEntity(
                    newId(),
                    registerRequest.details.dimensions.width,
                    registerRequest.details.dimensions.height,
                    registerRequest.details.dimensions.depth
                ),
                ColorEntity(
                    newId(),
                    registerRequest.details.color.code,
                    registerRequest.details.color.name
                ),
                registerRequest.details.tags,
            ),
            registerRequest.reviews.map { reviewRequest->
                ReviewEntity(newId(), newId(),reviewRequest.rating,reviewRequest.comment,reviewRequest.date)
            }
        )

        return productRepository.insertProduct(productEntity)
    }
}