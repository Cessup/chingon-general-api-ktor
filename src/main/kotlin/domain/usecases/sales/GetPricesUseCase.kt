package com.cessup.domain.usecases.sales

import com.cessup.domain.models.sales.Price
import com.cessup.domain.models.session.User
import com.cessup.domain.repositories.SalesRepository


/**
 * Get Prices of products by Merchant in the system.
 *
 * This class give every data about prices .
 *
 * @constructor Receiver a [SalesRepository] object because use it to origin data.
 * @author
 *     Cessup
 * @since 1.0
 */
class GetPricesUseCase(val repository: SalesRepository){

    /**
     * Returns the sum of two integers.
     *
     * @return A [User] from the previously email
     */
    suspend fun execute(): List<Price?> = repository.getAllPrices()
}