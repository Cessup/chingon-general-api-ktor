package com.cessup.domain.usecases.products

import com.cessup.data.services.Encrypt
import com.cessup.domain.models.products.Product
import com.cessup.domain.repositories.ProductRepository
import com.google.inject.Inject

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
     * @param product this object got information about the product.
     * @return A [Boolean] that is the result about operation.
     */
    suspend fun execute(product: Product): Boolean {
        return productRepository.updateProduct(product)
    }
}