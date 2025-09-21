package com.cessup.data.repositories

import com.cessup.data.models.sales.MerchantEntity
import com.cessup.data.models.sales.SaleEntity
import com.cessup.data.models.sales.PromotionEntity
import com.cessup.domain.models.sales.Merchant
import com.cessup.domain.models.sales.Sale
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
    private val saleCollection = database.getCollection<SaleEntity>("sales")

    /**
     * This function insert a new sale in the database
     *
     * @param sale the sale is the object with information for sale
     * @return a user
     */
    override suspend fun insertPrice(sale: Sale): Boolean = withContext(Dispatchers.IO) {
        val saleEntity = SaleEntity(
            newId(),
            sale.mount,
            sale.currency,
            MerchantEntity(
                sale.merchant.id.toId(),
                sale.merchant.name,
                sale.merchant.img
            )
        )
        try {
            saleCollection.insertOne(saleEntity)
            true
        } catch (_: Exception) {
            false
        }
    }
    /**
     * This function update a sale object in the database
     *
     * @param sale the sale is the object with information for sale
     * @return a Boolean this is the result
     */
    override suspend fun updatePrice(sale: Sale): Boolean = withContext(Dispatchers.IO) {
        val saleEntity = SaleEntity(
            newId(),
            sale.mount,
            sale.currency,
            MerchantEntity(
                sale.merchant.id.toId(),
                sale.merchant.name,
                sale.merchant.img
            )
        )
        val updateResult = saleCollection.replaceOne(
            SaleEntity::id eq saleEntity.id,
            saleEntity
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
    override suspend fun getPrices(): List<Sale> = saleCollection.find().toList().let {
        it.map { saleEntity ->
            Sale(
                saleEntity.id.toString(),
                saleEntity.mount,
                saleEntity.currency,
                Merchant(
                    saleEntity.merchant.id.toString(),
                    saleEntity.merchant.name,
                    saleEntity.merchant.img
                )
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
                promotion.merchant.name,
                promotion.merchant.img
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
                promotion.merchant.name,
                promotion.merchant.img
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
                it.merchant.name,
                it.merchant.img
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
                    promotionEntity.merchant.name,
                    promotionEntity.merchant.img
                )
            )
        }
    }

}



