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

class SalesRepositoryImpl@Inject constructor(database: CoroutineDatabase) : SalesRepository {
    private val saleCollection = database.getCollection<PriceEntity>("sales")

    override suspend fun assignPrice(price: Price): Boolean = withContext(Dispatchers.IO) {
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

    override suspend fun changePrice(price: Price): Boolean = withContext(Dispatchers.IO) {
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

    override suspend fun deletePrice(id: ObjectId): Boolean = withContext(Dispatchers.IO) {
        val deleteResult = saleCollection.deleteOneById(id)
        deleteResult.deletedCount == 1L
    }

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


    override suspend fun createPromotion(promotion: Promotion): Boolean = withContext(Dispatchers.IO) {
        val promotionEntity = PromotionEntity(
            newId(),
            promotion.name,
            promotion.details,
            promotion.discount,
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

    override suspend fun changePromotion(promotion: Promotion): Boolean = withContext(Dispatchers.IO) {
        val promotionEntity = PromotionEntity(
            newId(),
            promotion.name,
            promotion.details,
            promotion.discount,
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

    override suspend fun deletePromotion(id: ObjectId): Boolean = withContext(Dispatchers.IO) {
        val deleteResult = promotionCollection.deleteOneById(id)
        deleteResult.deletedCount == 1L
    }

    override suspend fun getPromotion(id: ObjectId): Promotion? = promotionCollection.findOneById(id)
    ?.let {
        Promotion(
            it.id.toString(),
            it.name,
            it.details,
            it.discount,
            Merchant(
                it.merchant.id.toString(),
                it.merchant.name
            )
        )
    }

    override suspend fun getPromotionList(): List<Promotion> = promotionCollection.find().toList().let {
        it.map { promotionEntity ->
            Promotion(
                promotionEntity.id.toString(),
                promotionEntity.name,
                promotionEntity.details,
                promotionEntity.discount,
                Merchant(
                    promotionEntity.merchant.id.toString(),
                    promotionEntity.merchant.name
                )
            )
        }
    }

}



