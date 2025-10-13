package com.cessup.domain.usecases.products

import com.cessup.domain.models.products.Product
import com.cessup.domain.repositories.ProductRepository
import com.cessup.domain.models.session.User

/**
 * Register of user in the system.
 *
 * This class make register of user information such as name, email, and age.
 *
 * @constructor Receiver a [ProductRepository] is an object because use it to origin data.
 * @author
 *     Cessup
 * @since 1.0
 */
class RegisterProductUseCase(val repository: ProductRepository)  {
    /**
     * Returns new product.
     *
     * @param Product this object got information about it.
     * @return A new [User] from the previously entered credentials
     */
    suspend fun execute(product: Product): Boolean {
        repository.findProductBySerialNumber(product.serialNumber)?.let { throw IllegalArgumentException("Serial Number already in use") }
        return repository.insertProduct(product)
    }
}