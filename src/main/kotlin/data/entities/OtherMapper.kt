package com.cessup.data.entities

import com.cessup.domain.models.others.Color
import com.cessup.domain.models.others.Dimensions
import com.cessup.domain.models.others.Review
import org.bson.Document

fun Color.toDocument(): Document = Document()
    .append("_id", id)
    .append("code", code)
    .append("name", name)

fun Document.toColor(): Color =
    Color(
        id = getObjectId("_id"),
        code = getString("email"),
        name = getString("phone")
    )

fun Dimensions.toDocument(): Document = Document()
    .append("_id", id)
    .append("width", width)
    .append("height", height)

fun Document.toDimensions(): Dimensions =
    Dimensions(
        id = getObjectId("_id"),
        width = getDouble("width"),
        height = getDouble("height"),
        depth = getDouble("depth")
    )


fun Review.toDocument(): Document = Document()
    .append("_id", id)
    .append("userId", id)
    .append("rating", rating)
    .append("comment", comment)
    .append("date", date)

fun Document.toReview(): Review =
    Review(
        id = getObjectId("_id"),
        userId = getObjectId("userId"),
        rating = getDouble("rating"),
        comment = getString("comment"),
        date = getLong("date")
    )
