package com.cessup.domain.usecases.products

import com.cessup.domain.repositories.ProductRepository
import com.google.inject.Inject
import org.bson.types.ObjectId


/**
 * Delete any product of the system.
 *
 * This class delete any product of the system.
 *
 * @constructor Receiver a [ProductRepository] object because use it to origin data.
 * @author
 *     Cessup
 * @since 1.0
 */
class DeleteProductUseCase @Inject constructor(private val productRepository: ProductRepository) {

    /**
     * Returns the sum of two integers.
     *
     * @param id the id from user for get their information
     * @return A [Boolean] that is the result about this process
     */
    suspend fun execute(id: String): Boolean = productRepository.deleteProduct(ObjectId(id))
}