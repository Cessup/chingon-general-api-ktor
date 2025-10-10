package com.cessup.data.repositories

import com.cessup.data.entities.toDocument
import com.cessup.data.entities.toProduct
import com.cessup.domain.models.products.Product
import com.cessup.domain.models.products.ProductDetails
import com.cessup.domain.repositories.ProductRepository
import com.google.inject.Inject
import com.mongodb.client.model.Filters.eq
import com.mongodb.reactivestreams.client.MongoDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.withContext
import org.bson.Document
import org.bson.types.ObjectId

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
class ProductRepositoryImpl @Inject constructor(database: MongoDatabase) : ProductRepository {
    /**
     * This function insert a new user in the database
     */
    private val products = database.getCollection("products")


    /**
     * This function insert a new product in the database
     *
     * @param product the user information from the services is here
     * @return a user
     */
    override suspend fun insertProduct(product: Product): Boolean = withContext(Dispatchers.IO) {
        try {
            products.insertOne(product.toDocument()).awaitFirstOrNull()
            true
        } catch (_: Exception) {
            false
        }
    }

    /**
     * This function update a product in the database
     *
     * @param product the user information from the services is here
     * @return a user
     */
    override suspend fun updateProduct(product: Product): Boolean = withContext(Dispatchers.IO) {
        val updateResult = products.updateOne(
            eq("_id", product.id),
            Document("\$set", product.toDocument())
        ).awaitFirstOrNull()
        updateResult?.matchedCount == 1L
    }

    private val productsDetails = database.getCollection("products_details")

    /**
     * The system can update the product details data to the product
     *
     * @param productDetails the email is a filter to search the user in database
     * @return a Boolean
     */
    override suspend fun updateProductDetails(productDetails: ProductDetails): Boolean = withContext(Dispatchers.IO) {
        val updateResult = productsDetails.updateOne(
            eq("_id", productDetails.id),
            productDetails.toDocument()
        ).awaitFirstOrNull()
        updateResult?.matchedCount == 1L
    }

    /**
     * This function delete a product in the database
     *
     * @param id the id is identification to search the object in database
     * @return a Boolean
     */
    override suspend fun deleteProduct(id: ObjectId): Boolean = withContext(Dispatchers.IO) {
        val deleteResult = products.deleteOne(eq("_id", id)).awaitFirstOrNull()
        deleteResult?.deletedCount == 1L
    }

    /**
     * Find a user in the database by id.
     *
     * @param id the id is identification to search the object in database
     * @return a user
     */
    override suspend fun findProductById(id: ObjectId): Product? =
        products.find(eq("_id", id)).first().awaitFirstOrNull()?.toProduct()


    /**
     * Find a user in the database by id.
     *
     * @param serialNumber the serialNumber is identification to search the object in database
     * @return a user
     */
    override suspend fun findProductBySerialNumber(serialNumber: String): Product?  =
        products.find(eq("serialnumber", serialNumber)).first().awaitFirstOrNull()?.toProduct()
}