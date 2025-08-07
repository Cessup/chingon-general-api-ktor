package com.cessup.domain.usecases.products

import com.cessup.data.models.others.ColorEntity
import com.cessup.data.models.others.DimensionsEntity
import com.cessup.data.models.others.ReviewEntity
import com.cessup.data.models.products.ProductDetailsEntity
import com.cessup.data.models.products.ProductEntity
import com.cessup.data.models.session.UserEntity
import com.cessup.data.services.Encrypt
import com.cessup.data.services.RegisterProductDetailsRequest
import com.cessup.data.services.RegisterProductRequest
import com.cessup.domain.repositories.ProductRepository
import com.google.inject.Inject
import org.litote.kmongo.newId
import org.litote.kmongo.toId

/**
 * Update of product by id of the product in the system.
 *
 * This class update of details product information such as name, description, etc..
 *
 * @constructor Receiver a [ProductRepository] and a [Encrypt] object because use it to origin data.
 * @author
 *     Cessup
 * @since 1.0
 */
class UpdateProductUseCase @Inject constructor(private val productRepository: ProductRepository) {

    /**
     * Update a product.
     *
     * @param RegisterProductDetailsRequest this object got information about the product.
     * @return A [Boolean] that is the result about operation.
     */
    suspend fun execute(registerProductRequest: RegisterProductRequest, id: String): Boolean {

        val productEntity = ProductEntity(
            newId(),
            registerProductRequest.serialNumber,
            registerProductRequest.price,
            registerProductRequest.currency,
            registerProductRequest.category,
            registerProductRequest.subcategory,
            registerProductRequest.stock,
            registerProductRequest.rating,
            ProductDetailsEntity(
                newId(),
                registerProductRequest.details.name,
                registerProductRequest.details.description,
                registerProductRequest.details.model,
                registerProductRequest.details.version,
                registerProductRequest.details.brand,
                DimensionsEntity(
                    newId(),
                    registerProductRequest.details.dimensions.width,
                    registerProductRequest.details.dimensions.height,
                    registerProductRequest.details.dimensions.depth
                ),
                ColorEntity(
                    newId(),
                    registerProductRequest.details.color.code,
                    registerProductRequest.details.color.name
                ),
                registerProductRequest.details.tags,
            ),
            registerProductRequest.reviews.map { reviewRequest->
                ReviewEntity(newId(), reviewRequest.userId.toId<UserEntity>(),reviewRequest.rating,reviewRequest.comment,reviewRequest.date)
            }
        )

        return productRepository.updateProduct(productEntity)
    }
}