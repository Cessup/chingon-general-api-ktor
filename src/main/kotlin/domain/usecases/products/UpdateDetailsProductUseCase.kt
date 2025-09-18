package com.cessup.domain.usecases.products

import com.cessup.data.services.Encrypt
import com.cessup.domain.models.products.ProductDetails
import com.cessup.domain.repositories.ProductRepository
import com.google.inject.Inject

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
     * @param ProductDetails this object got information about the product.
     * @return A [Boolean] that is the result about operation.
     */
    suspend fun execute(productDetails: ProductDetails): Boolean {
        return productRepository.updateProductDetails(productDetails)
    }
}