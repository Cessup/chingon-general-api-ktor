package com.cessup.data.repositories

import com.cessup.data.models.sales.MerchantEntity
import com.cessup.data.models.sales.PriceEntity
import com.cessup.data.models.sales.PromotionEntity
import com.cessup.domain.models.sales.Merchant
import com.cessup.domain.models.sales.Price
import com.cessup.domain.models.sales.Promotion
import com.cessup.domain.repositories.SalesRepository
import com.google.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.bson.types.ObjectId
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.newId
import org.litote.kmongo.toId

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
class SalesRepositoryImpl@Inject constructor(database: CoroutineDatabase) : SalesRepository {
    private val saleCollection = database.getCollection<PriceEntity>("sales")

    /**
     * This function insert a new price in the database
     *
     * @param price the price is the object with information for sale
     * @return a user
     */
    override suspend fun insertPrice(price: Price): Boolean = withContext(Dispatchers.IO) {
        val priceEntity = PriceEntity(
            newId(),
            price.mount,
            price.currency,
            MerchantEntity(
                price.merchant.id.toId(),
                price.merchant.name
            ),
            price.item
        )
        try {
            saleCollection.insertOne(priceEntity)
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
        val priceEntity = PriceEntity(
            newId(),
            price.mount,
            price.currency,
            MerchantEntity(
                price.merchant.id.toId(),
                price.merchant.name
            ),
            price.item
        )
        val updateResult = saleCollection.replaceOne(
            PriceEntity::id eq priceEntity.id,
            priceEntity
        )

        updateResult.matchedCount > 0 && updateResult.modifiedCount > 0
    }
    /**
     * This function delete a price object in the database
     *
     * @param id is the param to find the object to delete
     * @return a Boolean this is the result
     */
    override suspend fun deletePrice(id: ObjectId): Boolean = withContext(Dispatchers.IO) {
        val deleteResult = saleCollection.deleteOneById(id)
        deleteResult.deletedCount == 1L
    }
    /**
     * This function get a list of prices for sale
     *
     * @return a List of Prices
     */
    override suspend fun getPrices(): List<Price> = saleCollection.find().toList().let {
        it.map { priceEntities ->
            Price(
                priceEntities.id.toString(),
                priceEntities.mount,
                priceEntities.currency,
                Merchant(
                    priceEntities.merchant.id.toString(),
                    priceEntities.merchant.name
                ),
                priceEntities.item
            )
        }
    }

    private val promotionCollection = database.getCollection<PromotionEntity>("promotion")

    /**
     * This function insert a new promotion in the database
     *
     * @param promotion the promotion is the object with information for offers
     * @return a user
     */
    override suspend fun insertPromotion(promotion: Promotion): Boolean = withContext(Dispatchers.IO) {
        val promotionEntity = PromotionEntity(
            newId(),
            promotion.name,
            promotion.details,
            promotion.discount,
            promotion.expiration,
            MerchantEntity(
                promotion.merchant.id.toId(),
                promotion.merchant.name
            ),
        )
        try {
            promotionCollection.insertOne(promotionEntity)
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
        val promotionEntity = PromotionEntity(
            newId(),
            promotion.name,
            promotion.details,
            promotion.discount,
            promotion.expiration,
            MerchantEntity(
                promotion.merchant.id.toId(),
                promotion.merchant.name
            ),
        )

        val updateResult = promotionCollection.replaceOne(
            PromotionEntity::id eq promotionEntity.id,
            promotionEntity
        )

        updateResult.matchedCount > 0 && updateResult.modifiedCount > 0
    }
    /**
     * This function delete a promotion object in the database
     *
     * @param id is the param to find the object to delete
     * @return a Boolean this is the result
     */
    override suspend fun deletePromotion(id: ObjectId): Boolean = withContext(Dispatchers.IO) {
        val deleteResult = promotionCollection.deleteOneById(id)
        deleteResult.deletedCount == 1L
    }
    /**
     * This function get a promotion object
     *
     * @return a List of Prices
     */
    override suspend fun getPromotion(id: ObjectId): Promotion? = promotionCollection.findOneById(id)
    ?.let {
        Promotion(
            it.id.toString(),
            it.name,
            it.details,
            it.discount,
            it.expiration,
            Merchant(
                it.merchant.id.toString(),
                it.merchant.name
            )
        )
    }

    /**
     * This function get a list of promotion for offers
     *
     * @return a List of Prices
     */
    override suspend fun getPromotionList(): List<Promotion> = promotionCollection.find().toList().let {
        it.map { promotionEntity ->
            Promotion(
                promotionEntity.id.toString(),
                promotionEntity.name,
                promotionEntity.details,
                promotionEntity.discount,
                promotionEntity.expiration,
                Merchant(
                    promotionEntity.merchant.id.toString(),
                    promotionEntity.merchant.name
                )
            )
        }
    }

}



