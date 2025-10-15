package com.cessup.data.repositories

import com.cessup.domain.models.orders.Address
import com.cessup.domain.models.orders.Order
import com.cessup.domain.repositories.OrdersRepository
import org.bson.types.ObjectId

class OrdersRepositoryImpl: OrdersRepository {
    override suspend fun insertOrder(order: Order): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun updateOrder(order: Order): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun updateStatus(order: Order): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun deleteOrder(order: Order): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getAllOrders(): List<Order> {
        TODO("Not yet implemented")
    }

    override suspend fun getOrdersByIdUser(id: ObjectId): List<Order?> {
        TODO("Not yet implemented")
    }

    override suspend fun getOrdersByAddress(address: Address): Order? {
        TODO("Not yet implemented")
    }
}