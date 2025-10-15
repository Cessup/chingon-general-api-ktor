package com.cessup.domain.repositories

import com.cessup.domain.models.orders.Address
import com.cessup.domain.models.orders.Order
import org.bson.types.ObjectId

/**
 * Orders Repository have every data about the orders.
 *
 * This class is a interface with all functions about orders
 * There are actions that get information about orders
 *
 * @author
 *     Cessup
 * @since 1.0
 */
interface OrdersRepository {
    /**
     * This function insert a new order in the database
     *
     * @param order the order contain all items to sales
     * @return result in boolean value
     */
    suspend fun insertOrder(order: Order) : Boolean
    /**
     * This function update a new order in the database
     *
     * @param order the order contain all items to sales
     * @return result in boolean value
     */
    suspend fun updateOrder(order: Order) : Boolean
    /**
     * This function update a new order in the database
     *
     * @param order the order contain all items to sales
     * @return result in boolean value
     */
    suspend fun updateStatus(order: Order) : Boolean
    /**
     * This function delete a new order in the database
     *
     * @param order the order contain all items to sales
     * @return result in boolean value
     */
    suspend fun deleteOrder(order: Order) : Boolean
    /**
     * This function obtains a list of orders from the database
     *
     * @return result a list of orders value
     */
    suspend fun getAllOrders(): List<Order>
    /**
     * This function obtains a list of orders from the database
     *
     * @return result a list of orders value
     */
    suspend fun getOrdersByIdUser(id: ObjectId): List<Order?>
    /**
     * This function obtains a list of orders from the database
     *
     * @return result an orders value
     */
    suspend fun getOrdersByAddress(address: Address): Order?
}