package com.cessup.domain.repositories

import com.cessup.domain.models.sales.Merchant
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
    /**
     * This function insert a new price in the database
     *
     * @param price the price is the object with information for sale
     * @return a user
     */
    suspend fun insertPrice(price: Price) : Boolean
    /**
     * This function update a price object in the database
     *
     * @param price the price is the object with information for sale
     * @return a Boolean this is the result
     */
    suspend fun updatePrice(price: Price) : Boolean
    /**
     * This function delete a price object in the database
     *
     * @param id is the param to find the object to delete
     * @return a Boolean this is the result
     */
    suspend fun deletePrice(id: ObjectId) : Boolean
    /**
     * This function find a price by idMerchant and idProduct
     *
     * @return a Price
     */
    suspend fun getPriceByIdMerchantAndIdProduct(idMerchant: ObjectId, idProduct: ObjectId): Price?
    /**
     * This function give a list of prices for sale
     *
     * @return a List of Prices
     */
    suspend fun getAllPrices() : List<Price>
    /**
     * This function find a price by id
     *
     * @return a Price
     */
    suspend fun getPricesByIdMerchant(idMerchant: ObjectId): List<Price?>
    /**
     * This function insert a new promotion in the database
     *
     * @param promotion the promotion is the object with information for offers
     * @return a Boolean
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
     * This function find a promotion by id
     *
     * @return a Promotion
     */
    suspend fun getPromotionById(id: ObjectId) : Promotion?
    /**
     * This function find a list of merchant for offers
     *
     * @return a List of Promotions
     */
    suspend fun getAllPromotions(): List<Promotion>
    /**
     * This function insert a new merchant in the database
     *
     * @param merchant the promotion is the object with information for offers
     * @return a user
     */
    suspend fun insertMerchant(merchant: Merchant) : Boolean
    /**
     * This function update a merchant object in the database
     *
     * @param merchant the merchant is the object with information for offers
     * @return a Boolean this is the result
     */
    suspend fun updateMerchant(merchant: Merchant) : Boolean
    /**
     * This function delete a merchant object in the database
     *
     * @param id is the param to find the object to delete
     * @return a Boolean this is the result
     */
    suspend fun deleteMerchant(id: ObjectId) : Boolean
    /**
     * This function find the merchant by id
     *
     * @return a Merchant
     */
    suspend fun findMerchantById(name: String) : Merchant?
    /**
     * This function find a list of merchants
     *
     * @return a List of Merchant
     */
    suspend fun getAllMerchants(): List<Merchant>
}