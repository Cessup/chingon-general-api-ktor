package com.cessup.data.entities

import com.cessup.domain.models.orders.Address
import com.cessup.domain.models.orders.Order
import com.cessup.domain.models.orders.Status
import org.bson.Document

fun Address.toDocument(): Document = Document()
    .append("_id", id)
    .append("street", street)
    .append("numberInt", numberInt)
    .append("numberExt", numberExt)
    .append("city", city)
    .append("state", state)
    .append("country", country)
    .append("postalCode", postalCode)
    .append("zipCode", zipCode)

fun Document.toAddress(): Address =
    Address(
        id = getObjectId("_id"),
        street = getString("street"),
        numberInt = getString("numberInt"),
        numberExt = getString("numberExt"),
        city = getString("city"),
        state = getString("state"),
        country = getString("country"),
        postalCode = getString("postalCode"),
        zipCode = getString("zipCode")
    )


fun Status.toDocument(): Document = Document()
    .append("_id", id)
    .append("code", code)
    .append("name", name)

fun Document.toStatus(): Status =
    Status(
        id = getObjectId("_id"),
        code = getInteger("code"),
        name = getString("name")
    )

fun Order.toDocument(): Document = Document()
    .append("_id", id)
    .append("idUser", idUser)
    .append("createdAt", createdAt)
    .append("address", address.toDocument())
    .append("items", items)
    .append("total", total)
    .append("status", status)

    fun Document.toOrder(): Order =
        Order(
            id = getObjectId("_id"),
            idUser = getObjectId("idUser"),
            createdAt = getLong("createdAt"),
            address = (get("address") as Document).toAddress(),
            items = (get("items") as? List<Document> ?: emptyList()).map {
                it.toPrice()
            },
            total = getDouble("total"),
            status = (get("status") as Document).toStatus()
    )