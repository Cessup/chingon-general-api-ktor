package com.cessup.domain.usecases.products

import com.cessup.domain.models.products.Product
import com.cessup.domain.repositories.ProductRepository
import com.google.inject.Inject

/**
 * Find of product in the system by SerialNumber.
 *
 * This class find a product.
 *
 * @constructor Receiver a [ProductRepository] object because use it to origin data.
 * @author
 *     Cessup
 * @since 1.0
 */
class GetProductsListUseCase @Inject constructor(val productRepository: ProductRepository) {

    /**
     * Returns the sum of two integers.
     *
     * @return The [Product] found in this search.
     */
    suspend fun execute(): List<Product?> = productRepository.findProducts()

}