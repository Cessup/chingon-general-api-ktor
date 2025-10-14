package com.cessup.data.entities

import com.cessup.domain.models.sales.Merchant
import com.cessup.domain.models.sales.Price
import com.cessup.domain.models.sales.Promotion
import org.bson.Document

fun Price.toDocument(): Document = Document()
    .append("_id", id)
    .append("mount", mount)
    .append("currency", currency)
    .append("merchant", merchant)
    .append("item", item)

fun Document.toPrice(): Price =
    Price(
        id = getObjectId("_id"),
        mount = getDouble("mount"),
        currency = getString("currency"),
        merchant = getObjectId("merchant"),
        item = getObjectId("item")
    )


fun Merchant.toDocument(): Document = Document()
    .append("_id", id)
    .append("name", name)

fun Document.toMerchant(): Merchant =
    Merchant(
        id = getObjectId("_id"),
        name = getString("name"),
    )

fun Promotion.toDocument(): Document = Document()
    .append("_id", id)
    .append("name", name)
    .append("details", details)
    .append("discount", discount)
    .append("expiration", expiration)
    .append("merchant", merchant)

fun Document.toPromotion(): Promotion =
    Promotion(
        id = getObjectId("_id"),
        name = getString("name"),
        details = getString("details"),
        discount = getInteger("discount"),
        expiration = getLong("expiration"),
        merchant = getObjectId("merchant")
    )
