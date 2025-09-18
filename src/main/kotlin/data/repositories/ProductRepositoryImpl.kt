package com.cessup.data.repositories

import com.cessup.data.models.others.ColorEntity
import com.cessup.data.models.others.DimensionsEntity
import com.cessup.data.models.products.ProductDetailsEntity
import com.cessup.data.models.products.ProductEntity
import com.cessup.domain.models.products.Product
import com.cessup.domain.models.products.ProductDetails
import com.cessup.domain.repositories.ProductRepository
import com.google.inject.Inject
import com.mongodb.client.model.Filters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.bson.types.ObjectId
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.id.toId
import org.litote.kmongo.newId

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
     * @param product the user information from the services is here
     * @return a user
     */
    override suspend fun insertProduct(product: Product): Boolean = withContext(Dispatchers.IO) {
        val productEntity = ProductEntity(
            newId(),
            product.serialNumber,
            product.category,
            product.subcategory,
            product.stock,
            product.name,
            product.img,
            product.rating,
            ObjectId(product.idBrand),
            ObjectId(product.idDetails)
        )

        try {
            products.insertOne(productEntity)
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
        val productEntity = ProductEntity(
            ObjectId(product.id).toId(),
            product.serialNumber,
            product.category,
            product.subcategory,
            product.stock,
            product.name,
            product.img,
            product.rating,
            ObjectId(product.idBrand),
            ObjectId(product.idDetails)
        )

        val updateResult = products.updateOne(
            ProductEntity::id eq productEntity.id,
            productEntity
        )

        updateResult.matchedCount > 0 && updateResult.modifiedCount > 0
    }

    private val productsDetails = database.getCollection< ProductDetailsEntity>("products_details")

    /**
     * The system can update the product details data to the product
     *
     * @param productDetails the email is a filter to search the user in database
     * @return a Boolean
     */
    override suspend fun updateProductDetails(productDetails: ProductDetails): Boolean = withContext(Dispatchers.IO) {
        val productDetailsEntity = ProductDetailsEntity(
            ObjectId(productDetails.id).toId(),
            productDetails.description,
            productDetails.version,
            DimensionsEntity(
                ObjectId(productDetails.dimensions.id).toId(),
                productDetails.dimensions.width,
                productDetails.dimensions.height,
                productDetails.dimensions.depth
            ),
            ColorEntity(
                    ObjectId(productDetails.color.id).toId(),
                productDetails.color.code,
                productDetails.color.name
            ),
            productDetails.tags
        )

        val updateResult = productsDetails.updateOne(
            ProductDetailsEntity::id eq productDetailsEntity.id,
            productDetailsEntity
        )

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
            ?.let { product->
                Product(
                    product.id.toString(),
                    product.serialNumber,
                    product.category,
                    product.subcategory,
                    product.stock,
                    product.name,
                    product.img,
                    product.rating,
                    product.idBrand.toString(),
                    product.idDetails.toString()
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
                it.first()?.let { product->
                    Product(
                        product.id.toString(),
                        product.serialNumber,
                        product.category,
                        product.subcategory,
                        product.stock,
                        product.name,
                        product.img,
                        product.rating,
                        product.idBrand.toString(),
                        product.idDetails.toString()
                    )
                }

            }
    }
}