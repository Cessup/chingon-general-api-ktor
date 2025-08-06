package com.cessup.domain.repositories

import com.cessup.data.models.products.ProductDetailsEntity
import com.cessup.data.models.products.ProductEntity
import com.cessup.domain.models.products.Product
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
interface ProductRepository {
    /**
     * This function insert a new product in the database
     *
     * @param productEntity the user information from the services is here
     * @return a user
     */
    suspend fun insertProduct(productEntity: ProductEntity): Product

    /**
     * This function update a product in the database
     *
     * @param productEntity the user information from the services is here
     * @return a user
     */
    suspend fun updateProduct(productEntity: ProductEntity): Boolean

    /**
     * The system can update the product details data to the product
     *
     * @param productDetailsEntity the email is a filter to search the user in database
     * @return a Boolean
     */
    suspend fun updateProductDetails(productDetailsEntity: ProductDetailsEntity): Boolean

    /**
     * This function delete a product in the database
     *
     * @param id the id is identification to search the object in database
     * @return a Boolean
     */
    suspend fun deleteProduct(id: ObjectId) : Boolean
    /**
     * Find a user in the database by id.
     *
     * @param id the id is identification to search the object in database
     * @return a user
     */
    suspend fun findProductById(id: ObjectId): Product?

    /**
     * Find a user in the database by id.
     *
     * @param serialNumber the serialNumber is identification to search the object in database
     * @return a user
     */
    suspend fun findProductBySerialNumber(serialNumber: String): Product?

}