package com.cessup.domain.repositories

import com.cessup.domain.models.sales.Sale
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
    /**
     * This function insert a new sale in the database
     *
     * @param sale the sale is the object with information for sale
     * @return a user
     */
    suspend fun insertPrice(sale: Sale) : Boolean
    /**
     * This function update a sale object in the database
     *
     * @param sale the sale is the object with information for sale
     * @return a Boolean this is the result
     */
    suspend fun updatePrice(sale: Sale) : Boolean
    /**
     * This function delete a price object in the database
     *
     * @param id is the param to find the object to delete
     * @return a Boolean this is the result
     */
    suspend fun deletePrice(id: ObjectId) : Boolean
    /**
     * This function get a list of prices for sale
     *
     * @return a List of Prices
     */
    suspend fun getPrices() : List<Sale>
    /**
     * This function insert a new promotion in the database
     *
     * @param promotion the promotion is the object with information for offers
     * @return a user
     */
    suspend fun insertPromotion(promotion: Promotion) : Boolean
    /**
     * This function update a promotion object in the database
     *
     * @param promotion the price is the object with information for offers
     * @return a Boolean this is the result
     */
    suspend fun updatePromotion(promotion: Promotion) : Boolean
    /**
     * This function delete a promotion object in the database
     *
     * @param id is the param to find the object to delete
     * @return a Boolean this is the result
     */
    suspend fun deletePromotion(id: ObjectId) : Boolean
    /**
     * This function get a promotion object
     *
     * @return a List of Prices
     */
    suspend fun getPromotion(id: ObjectId) : Promotion?
    /**
     * This function get a list of promotion for offers
     *
     * @return a List of Prices
     */
    suspend fun getPromotionList() : List<Promotion?>
}