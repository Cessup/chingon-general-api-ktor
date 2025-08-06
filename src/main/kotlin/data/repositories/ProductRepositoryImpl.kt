package com.cessup.data.repositories

import com.cessup.data.models.products.ProductDetailsEntity
import com.cessup.data.models.products.ProductEntity
import com.cessup.domain.models.others.Color
import com.cessup.domain.models.others.Dimensions
import com.cessup.domain.models.others.Review
import com.cessup.domain.models.products.Product
import com.cessup.domain.models.products.ProductDetails
import com.cessup.domain.repositories.ProductRepository
import com.google.inject.Inject
import com.mongodb.client.model.Filters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.bson.types.ObjectId
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.div
import org.litote.kmongo.setValue

/**
 * Product Repository have every data about the products.
 *
 * This class is a interface with all functions about user information
 * There are actions that the user can perform
 *
 * @author
 *     Cessup
 * @since 1.0
 */
class ProductRepositoryImpl @Inject constructor(database: CoroutineDatabase) : ProductRepository {
    /**
     * This function insert a new user in the database
     */
    private val products = database.getCollection<ProductEntity>("products")


    /**
     * This function insert a new product in the database
     *
     * @param productEntity the user information from the services is here
     * @return a user
     */
    override suspend fun insertProduct(productEntity: ProductEntity): Product = withContext(Dispatchers.IO) {
        products.insertOne(productEntity)

        Product(productEntity.id.toString(),
            productEntity.serialNumber,
            productEntity.price,
            productEntity.currency,
            productEntity.category,
            productEntity.subcategory,
            productEntity.stock,
            productEntity.rating,
            ProductDetails(
                productEntity.details.id.toString(),
                productEntity.details.name,
                productEntity.details.description,
                productEntity.details.model,
                productEntity.details.version,
                productEntity.details.brand,
                Dimensions(
                    productEntity.details.dimensions.id.toString(),
                    productEntity.details.dimensions.width,
                    productEntity.details.dimensions.height,
                    productEntity.details.dimensions.depth
                ),
                Color(
                    productEntity.details.color.id.toString(),
                    productEntity.details.color.code,
                    productEntity.details.color.name
                ),
                productEntity.details.tags
            ),
            productEntity.reviews.map { reviewEntity ->
                Review(reviewEntity.id.toString(),reviewEntity.userId.toString(),reviewEntity.rating,reviewEntity.comment,reviewEntity.date)
            }
        )

    }

    /**
     * This function update a product in the database
     *
     * @param productEntity the user information from the services is here
     * @return a user
     */
    override suspend fun updateProduct(productEntity: ProductEntity): Boolean = withContext(Dispatchers.IO) {
        val filter = Filters.eq((ProductEntity::id).toString(), ObjectId(productEntity.id.toString()))
        val update = setValue(ProductEntity::id, productEntity.id)
        val updateResult = products.updateOne(filter,update)
        updateResult.matchedCount > 0 && updateResult.modifiedCount > 0
    }

    /**
     * The system can update the product details data to the product
     *
     * @param productDetailsEntity the email is a filter to search the user in database
     * @return a Boolean
     */
    override suspend fun updateProductDetails(productDetailsEntity: ProductDetailsEntity): Boolean = withContext(Dispatchers.IO) {
        val filter = Filters.eq((ProductEntity::details / ProductDetailsEntity::id).toString(), ObjectId(productDetailsEntity.id.toString()))
        val update = setValue(ProductEntity::details, productDetailsEntity)
        val updateResult = products.updateOne(filter,update)
        updateResult.matchedCount > 0 && updateResult.modifiedCount > 0
    }

    /**
     * This function delete a product in the database
     *
     * @param id the id is identification to search the object in database
     * @return a Boolean
     */
    override suspend fun deleteProduct(id: ObjectId): Boolean = withContext(Dispatchers.IO) {
        val deleteResult = products.deleteOneById(id)
        deleteResult.deletedCount == 1L
    }

    /**
     * Find a user in the database by id.
     *
     * @param id the id is identification to search the object in database
     * @return a user
     */
    override suspend fun findProductById(id: ObjectId): Product? =
        products.findOneById(id)
            ?.let {
                Product(it.id.toString(),
                    it.serialNumber,
                    it.price,
                    it.currency,
                    it.category,
                    it.subcategory,
                    it.stock,
                    it.rating,
                    ProductDetails(
                        it.details.id.toString(),
                        it.details.name,
                        it.details.description,
                        it.details.model,
                        it.details.version,
                        it.details.brand,
                        Dimensions(
                            it.details.dimensions.id.toString(),
                            it.details.dimensions.width,
                            it.details.dimensions.height,
                            it.details.dimensions.depth
                        ),
                        Color(
                            it.details.color.id.toString(),
                            it.details.color.code,
                            it.details.color.name
                        ),
                        it.details.tags
                    ),
                    it.reviews.map { reviewEntity ->
                        Review(reviewEntity.id.toString(), reviewEntity.userId.toString(),reviewEntity.rating,reviewEntity.comment,reviewEntity.date)
                    }
                )
            }

    /**
     * Find a user in the database by id.
     *
     * @param serialNumber the serialNumber is identification to search the object in database
     * @return a user
     */
    override suspend fun findProductBySerialNumber(serialNumber: String): Product?  {
    val filter = Filters.eq(ProductEntity::serialNumber.toString(), serialNumber)

       return  products.find(filter)
            .let {
                it.first()?.let {
                    Product(it.id.toString(),
                        it.serialNumber,
                        it.price,
                        it.currency,
                        it.category,
                        it.subcategory,
                        it.stock,
                        it.rating,
                        ProductDetails(
                            it.details.id.toString(),
                            it.details.name,
                            it.details.description,
                            it.details.model,
                            it.details.version,
                            it.details.brand,
                            Dimensions(
                                it.details.dimensions.id.toString(),
                                it.details.dimensions.width,
                                it.details.dimensions.height,
                                it.details.dimensions.depth
                            ),
                            Color(
                                it.details.color.id.toString(),
                                it.details.color.code,
                                it.details.color.name
                            ),
                            it.details.tags
                        ),
                        it.reviews.map { reviewEntity ->
                            Review(reviewEntity.id.toString(), reviewEntity.userId.toString(),reviewEntity.rating,reviewEntity.comment,reviewEntity.date)
                        }
                    )
                }

            }
    }
}