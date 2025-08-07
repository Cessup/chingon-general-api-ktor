package com.cessup.domain.usecases.products

import com.cessup.domain.models.products.Product
import com.cessup.domain.repositories.ProductRepository
import com.cessup.domain.repositories.UserRepository
import com.google.inject.Inject

/**
 * Find of product in the system by SerialNumber.
 *
 * This class find a product.
 *
 * @constructor Receiver a [UserRepository] object because use it to origin data.
 * @author
 *     Cessup
 * @since 1.0
 */
class FindBySerialNumberUseCase @Inject constructor(val productRepository: ProductRepository) {

    /**
     * Returns the sum of two integers.
     *
     * @param serialNumber the serial number of product
     *
     * @return The [Product] found in this search.
     */
    suspend fun execute(serialNumber: String): Product? = productRepository.findProductBySerialNumber(serialNumber)

}