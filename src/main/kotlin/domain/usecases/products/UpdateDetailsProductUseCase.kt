package com.cessup.domain.usecases.products

import com.cessup.data.models.others.ColorEntity
import com.cessup.data.models.others.DimensionsEntity
import com.cessup.data.models.products.ProductDetailsEntity
import com.cessup.data.services.Encrypt
import com.cessup.data.services.RegisterProductDetailsRequest
import com.cessup.domain.repositories.ProductRepository
import com.google.inject.Inject
import org.litote.kmongo.newId

/**
 * Update of product details by id of the product in the system.
 *
 * This class update of details product information such as name, description, etc..
 *
 * @constructor Receiver a [ProductRepository] and a [Encrypt] object because use it to origin data.
 * @author
 *     Cessup
 * @since 1.0
 */
class UpdateDetailsProductUseCase @Inject constructor(private val productRepository: ProductRepository) {

    /**
     * Update a product.
     *
     * @param RegisterProductDetailsRequest this object got information about the product.
     * @return A [Boolean] that is the result about operation.
     */
    suspend fun execute(registerRequest: RegisterProductDetailsRequest, id: String): Boolean {

        val productDetailsEntity = ProductDetailsEntity(
            newId(),
            registerRequest.name,
            registerRequest.description,
            registerRequest.model,
            registerRequest.version,
            registerRequest.brand,
            DimensionsEntity(
                newId(),
                registerRequest.dimensions.width,
                registerRequest.dimensions.height,
                registerRequest.dimensions.depth
            ),
            ColorEntity(
                newId(),
                registerRequest.color.code,
                registerRequest.color.name
            ),
            registerRequest.tags,
        )

        return productRepository.updateProductDetails(productDetailsEntity)
    }
}