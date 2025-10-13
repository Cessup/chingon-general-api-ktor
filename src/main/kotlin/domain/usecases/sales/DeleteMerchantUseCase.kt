package com.cessup.domain.usecases.sales

import com.cessup.domain.repositories.SalesRepository
import org.bson.types.ObjectId

/**
 * Delete any merchant of the system.
 *
 * This class delete any merchant of the system.
 *
 * @constructor Receiver a [SalesRepository] object because use it to origin data.
 * @author
 *     Cessup
 * @since 1.0
 */
class DeleteMerchantUseCase(val repository: SalesRepository){

    /**
     * Returns the sum of two integers.
     *
     * @param id the id from user for get their information
     * @return A [Boolean] that is the result about this process
     */
    suspend fun execute(id: ObjectId): Boolean = repository.deleteMerchant(id)
}