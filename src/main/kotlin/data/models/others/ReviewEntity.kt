package com.cessup.data.models.others

import com.cessup.data.models.UserEntity
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id

/**
 * Represents a Dimensions.
 *
 * @property id unique identifier
 * @property userId the id of the user from comment.
 * @property rating the rating is the value of product from user.
 * @property comment the comment is the opinion from user.
 * @property date the date is the date of made it.
 */
data class ReviewEntity(
    @BsonId val id: Id<ReviewEntity>,
    val userId: Id<UserEntity>,
    val rating: Double,
    val comment: String,
    val date: Long,
)