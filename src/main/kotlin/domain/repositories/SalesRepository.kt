package com.cessup.domain.repositories

import com.cessup.domain.models.sales.Price
import com.cessup.domain.models.sales.Promotion
import org.bson.types.ObjectId

/**
 * Sales Repository have every data about the sales.
 *
 * This class is a interface with all functions about user information
 * There are actions that the user can perform
 *
 * @author
 *     Cessup
 * @since 1.0
 */
interface SalesRepository {
    suspend fun assignPrice(price: Price) : Boolean
    suspend fun changePrice(price: Price) : Boolean
    suspend fun deletePrice(id: ObjectId) : Boolean
    suspend fun getPrices() : List<Price>

    suspend fun createPromotion(promotion: Promotion) : Boolean
    suspend fun changePromotion(promotion: Promotion) : Boolean
    suspend fun deletePromotion(id: ObjectId) : Boolean
    suspend fun getPromotion(id: ObjectId) : Promotion?
    suspend fun getPromotionList() : List<Promotion>
}