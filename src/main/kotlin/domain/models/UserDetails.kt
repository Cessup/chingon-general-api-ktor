package com.cessup.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class UserDetails(
    val id: String,
    val name: String,
    val phoneNumber: String,
    val address: String,
    val gender: String,
    val birthdate : Long
)