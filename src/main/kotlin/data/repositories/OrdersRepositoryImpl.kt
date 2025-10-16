package com.cessup.data.repositories

import com.cessup.data.entities.toDocument
import com.cessup.data.entities.toOrder
import com.cessup.domain.models.orders.Address
import com.cessup.domain.models.orders.Order
import com.cessup.domain.repositories.OrdersRepository
import com.mongodb.client.model.Filters.eq
import com.mongodb.reactivestreams.client.MongoDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.withContext
import org.bson.types.ObjectId

/**
 * Order Repository have every data about the orders.
 *
 * This class is a interface with all functions about order information
 * There are actions that the user can use to order
 *
 * @author
 *     Cessup
 * @since 1.0
 */
class OrdersRepositoryImpl(val database: MongoDatabase): OrdersRepository {

    val orderCollection = database.getCollection("orders")

    /**
     * This function insert a new order in the database
     *
     * @param order the order contain all items to sales
     * @return result in boolean value
     */
    override suspend fun insertOrder(order: Order): Boolean = withContext(Dispatchers.IO){
        try{
            orderCollection.insertOne(order.toDocument()).awaitFirstOrNull()
            true
        }catch (_: Exception){
            false
        }
    }
    /**
     * This function update a new order in the database
     *
     * @param order the order contain all items to sales
     * @return result in boolean value
     */
    override suspend fun updateOrder(order: Order): Boolean = withContext(Dispatchers.IO){
        val updateResult = orderCollection.updateOne(
            eq("_id",order.id),
            order.toDocument()
        ).awaitFirstOrNull()
        updateResult?.matchedCount == 1L
    }
    /**
     * This function update a new order in the database
     *
     * @param order the order contain all items to sales
     * @return result in boolean value
     */
    override suspend fun updateStatus(order: Order): Boolean = withContext(Dispatchers.IO){
        val updateResult = orderCollection.updateOne(
            eq("_id",order.id),
            order.toDocument()
        ).awaitFirstOrNull()
        updateResult?.matchedCount == 1L
    }
    /**
     * This function delete a order in the database
     *
     * @param order the order contain all items to sales
     * @return result in boolean value
     */
    override suspend fun deleteOrder(order: Order): Boolean = withContext(Dispatchers.IO){
        val deleteResult = orderCollection.deleteOne(eq("_id", order.id)).awaitFirstOrNull()
        deleteResult?.deletedCount == 1L
    }
    /**
     * This function obtains a list of orders from the database
     *
     * @return result a list of orders value
     */
    override suspend fun getAllOrders(): List<Order> = orderCollection.find().asFlow().toList().map { it.toOrder() }
    /**
     * This function obtains a list of orders from the database
     *
     * @return result a list of orders value
     */
    override suspend fun getOrdersByIdUser(id: ObjectId): List<Order?> =orderCollection.find(eq("_idUser", id)).asFlow().toList().map { it.toOrder() }
    /**
     * This function obtains a list of orders from the database by PostalCode
     *
     * @return result an orders value
     */
    override suspend fun getOrdersByPD(address: Address): Order? = orderCollection.find(eq("address", address.postalCode)).awaitFirstOrNull()?.toOrder()
}