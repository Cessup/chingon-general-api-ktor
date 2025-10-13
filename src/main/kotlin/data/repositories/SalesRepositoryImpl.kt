package com.cessup.data.repositories

import com.cessup.data.entities.toDocument
import com.cessup.data.entities.toMerchant
import com.cessup.data.entities.toPrice
import com.cessup.data.entities.toPromotion
import com.cessup.domain.models.sales.Merchant
import com.cessup.domain.models.sales.Price
import com.cessup.domain.models.sales.Promotion
import com.cessup.domain.repositories.SalesRepository
import com.mongodb.client.model.Filters.eq
import com.mongodb.reactivestreams.client.MongoDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.withContext
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
class SalesRepositoryImpl(val database: MongoDatabase) : SalesRepository {
    private val priceCollection = database.getCollection("prices")

    /**
     * This function insert a new price in the database
     *
     * @param price the price is the object with information for sale
     * @return a user
     */
    override suspend fun insertPrice(price: Price): Boolean = withContext(Dispatchers.IO) {
        try {
            priceCollection.insertOne(price.toDocument())
            true
        } catch (_: Exception) {
            false
        }
    }
    /**
     * This function update a price object in the database
     *
     * @param price the price is the object with information for sale
     * @return a Boolean this is the result
     */
    override suspend fun updatePrice(price: Price): Boolean = withContext(Dispatchers.IO) {
        val updateResult = priceCollection.updateOne(
            eq("_id", price.id),
            price.toDocument()
        ).awaitFirstOrNull()
        updateResult?.matchedCount == 1L
    }
    /**
     * This function delete a price object in the database
     *
     * @param id is the param to find the object to delete
     * @return a Boolean this is the result
     */
    override suspend fun deletePrice(id: ObjectId): Boolean = withContext(Dispatchers.IO) {
        val deleteResult = priceCollection.deleteOne(eq("_id", id)).awaitFirstOrNull()
        deleteResult?.deletedCount == 1L
    }
    /**
     * This function find a price by id
     *
     * @return a Price
     */
    override suspend fun getPriceByIdMerchantAndIdProduct(idMerchant: ObjectId, idProduct: ObjectId): Price? = priceCollection.find(eq("merchant", idMerchant)).first().awaitFirstOrNull()?.toPrice()

    /**
     * This function give a list of prices for sale
     *
     * @return a List of Prices
     */
    override suspend fun getAllPrices(): List<Price> = priceCollection.find().asFlow().toList().map { it.toPrice() }

    /**
     * This function find a price by id
     *
     * @return a Price
     */
    override suspend fun getPricesByIdMerchant(idMerchant: ObjectId): List<Price?> = priceCollection.find(eq("merchant", idMerchant)).asFlow().toList().map { it.toPrice() }


    private val promotionCollection = database.getCollection("promotion")

    /**
     * This function insert a new promotion in the database
     *
     * @param promotion the promotion is the object with information for offers
     * @return a Boolean
     */
    override suspend fun insertPromotion(promotion: Promotion): Boolean = withContext(Dispatchers.IO) {
        try {
            promotionCollection.insertOne(promotion.toDocument())
            true
        } catch (_: Exception) {
            false
        }
    }
    /**
     * This function update a promotion object in the database
     *
     * @param promotion the price is the object with information for offers
     * @return a Boolean this is the result
     */
    override suspend fun updatePromotion(promotion: Promotion): Boolean = withContext(Dispatchers.IO) {
        val updateResult = promotionCollection.updateOne(
            eq("_id", promotion.id),
            promotion.toDocument()
        ).awaitFirstOrNull()
        updateResult?.matchedCount == 1L
    }
    /**
     * This function delete a promotion object in the database
     *
     * @param id is the param to find the object to delete
     * @return a Boolean this is the result
     */
    override suspend fun deletePromotion(id: ObjectId): Boolean = withContext(Dispatchers.IO) {
        val deleteResult = promotionCollection.deleteOne(eq("_id", id)).awaitFirstOrNull()
        deleteResult?.deletedCount == 1L
    }
    /**
     * This function find a promotion by id
     *
     * @return a Promotion
     */
    override suspend fun getPromotionById(id: ObjectId): Promotion? = promotionCollection.find(eq("_id", id)).first().awaitFirstOrNull()?.toPromotion()

    /**
     * This function find a list of merchant for offers
     *
     * @return a List of Promotions
     */
    override suspend fun getAllPromotions(): List<Promotion> = promotionCollection.find().asFlow().toList().map { it.toPromotion() }

    private val merchantCollection = database.getCollection("merchants")
    /**
     * This function insert a new merchant in the database
     *
     * @param merchant the promotion is the object with information for offers
     * @return a user
     */
    override suspend fun insertMerchant(merchant: Merchant): Boolean = withContext(Dispatchers.IO) {
        try {
            merchantCollection.insertOne(merchant.toDocument())
            true
        } catch (_: Exception) {
            false
        }
    }
    /**
     * This function update a merchant object in the database
     *
     * @param merchant the merchant is the object with information for offers
     * @return a Boolean this is the result
     */
    override suspend fun updateMerchant(merchant: Merchant): Boolean = withContext(Dispatchers.IO) {
        val updateResult = merchantCollection.updateOne(
            eq("_id", merchant.id),
            merchant.toDocument()
        ).awaitFirstOrNull()
        updateResult?.matchedCount == 1L
    }
    /**
     * This function delete a merchant object in the database
     *
     * @param id is the param to find the object to delete
     * @return a Boolean this is the result
     */
    override suspend fun deleteMerchant(id: ObjectId): Boolean = withContext(Dispatchers.IO) {
        val deleteResult = merchantCollection.deleteOne(eq("_id", id)).awaitFirstOrNull()
        deleteResult?.deletedCount == 1L
    }
    /**
     * This function find the merchant by id
     *
     * @return a Merchant
     */
    override suspend fun findMerchantById(name: String): Merchant? = merchantCollection.find(eq("name", name)).first().awaitFirstOrNull()?.toMerchant()
    /**
     * This function find a list of merchants
     *
     * @return a List of Merchant
     */
    override suspend fun getAllMerchants(): List<Merchant> = merchantCollection.find().asFlow().toList().map { it.toMerchant() }
}



