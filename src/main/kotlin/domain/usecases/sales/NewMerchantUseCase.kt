package com.cessup.domain.usecases.sales

import com.cessup.domain.models.sales.Merchant
import com.cessup.domain.repositories.SalesRepository

/**
 * Assignment of Price for anything.
 *
 * This class make price for all things to sale
 *
 * @constructor Receiver a [SalesRepository]
 * @author
 *     Cessup
 * @since 1.0
 */
class NewMerchantUseCase (private val repository: SalesRepository) {
    /**
     * Returns new user.
     *
     * @param merchant this object got information about things for sale
     * @return a [Boolean] for the result of function
     */
    suspend fun execute(merchant : Merchant): Boolean {
        repository.findMerchantById(merchant.name)?.let {
            throw IllegalArgumentException("Merchant already in use")
        }

        return repository.insertMerchant(merchant)
    }

}