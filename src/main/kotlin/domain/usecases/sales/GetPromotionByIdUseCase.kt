package com.cessup.domain.usecases.sales

import com.cessup.domain.models.sales.Promotion
import com.cessup.domain.models.session.User
import com.cessup.domain.repositories.SalesRepository
import org.bson.types.ObjectId

/**
 * Get Prices of products in the system.
 *
 * This class give every data about prices .
 *
 * @constructor Receiver a [SalesRepository] object because use it to origin data.
 * @author
 *     Cessup
 * @since 1.0
 */
class GetPromotionByIdUseCase(val repository: SalesRepository){

    /**
     * Returns the sum of two integers.
     *
     * @return A [User] from the previously email
     */
    suspend fun execute(id: ObjectId): Promotion? = repository.getPromotionById(id)
}