package com.cessup.domain.models.others

/**
 * Represents a Dimensions.
 *
 * @property id unique identifier
 * @property userId the id of the user from comment.
 * @property rating the rating is the value of product from user.
 * @property comment the comment is the opinion from user.
 * @property date the date is the date of made it.
 */
data class Review(
    val id: String,
    val userId: String,
    val rating: Double,
    val comment: String,
    val date: Long,
)