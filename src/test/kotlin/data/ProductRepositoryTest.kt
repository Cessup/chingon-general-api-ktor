package com.cessup.data

import com.cessup.data.repositories.ProductRepositoryImpl
import com.cessup.domain.models.others.Color
import com.cessup.domain.models.others.Dimensions
import com.cessup.domain.models.products.Brand
import com.cessup.domain.models.products.Product
import com.cessup.domain.models.products.ProductDetails
import com.mongodb.reactivestreams.client.MongoClients
import kotlinx.coroutines.*
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.test.runTest
import org.bson.types.ObjectId
import org.junit.jupiter.api.*
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProductRepositoryImplTest {

    private lateinit var repository: ProductRepositoryImpl

    @BeforeAll
    fun setup() {
        val client = MongoClients.create("mongodb://localhost:27017")
        val database = client.getDatabase("product_db")
        runBlocking {
            // Clean up collections before running tests
            database.getCollection("products").drop().awaitFirstOrNull()
            database.getCollection("products_details").drop().awaitFirstOrNull()
        }
        repository = ProductRepositoryImpl(database)
    }

    @Test
    fun `insertProduct should insert and return true`() = runTest {
        val product = createTestProduct()

        val result = repository.insertProduct(product)

        assertTrue(result)

        val found = repository.findProductById(product.id)
        Assertions.assertNotNull(found)
        assertEquals(product.serialNumber, found?.serialNumber)
    }

    @Test
    fun `updateProduct should modify existing product`() = runTest {
        val product = createTestProduct()
        repository.insertProduct(product)

        val updated = product.copy(name = "Updated Product")

        val result = repository.updateProduct(updated)

        assertTrue(result)

        val found = repository.findProductById(product.id)
        assertEquals("Updated Product", found?.name)
    }

    @Test
    fun `deleteProduct should remove a product`() = runTest {
        val product = createTestProduct()
        repository.insertProduct(product)

        val result = repository.deleteProduct(product.id)

        assertTrue(result)

        val found = repository.findProductById(product.id)
        Assertions.assertNull(found?.name)
    }
}

private fun createTestProduct(): Product {

    val productDetails = ProductDetails(
        id = ObjectId(),
        description = "Test product description",
        version = "1.0",
        dimensions = Dimensions(
            id = ObjectId(),
            width = 1.0,
            height = 1.0,
            depth = 1.0
        ),
        color = Color(
            id = ObjectId(),
            code = "000000",
            name = "Black"
        ),
        tags = listOf(),
    )

    val brand = Brand(
        id = ObjectId(),
        name = "FEMSA",
        description = "It is the most popular brand",
        img = "https://www.google.com/url?sa=i&url=https%3A%2F%2Fgraffica.info%2Fcual-es-la-historia-del-logo-de-coca-cola%2F&psig=AOvVaw2QCM8ALDQvy1cvZaLK7l4y&ust=1760169317015000&source=images&cd=vfe&opi=89978449&ved=0CBUQjRxqFwoTCLihk-mTmZADFQAAAAAdAAAAABAE",
    )


    return Product(
        id= ObjectId(),
        serialNumber="SN003",
        category="Food",
        subcategory="drink",
        stock=10,
        name="Coca cola",
        img="https://www.google.com/url?sa=i&url=https%3A%2F%2Fgraffica.info%2Fcual-es-la-historia-del-logo-de-coca-cola%2F&psig=AOvVaw2QCM8ALDQvy1cvZaLK7l4y&ust=1760169317015000&source=images&cd=vfe&opi=89978449&ved=0CBUQjRxqFwoTCLihk-mTmZADFQAAAAAdAAAAABAE",
        rating=0.0,
        idBrand=brand.id,
        idDetails= productDetails.id
    )

}
