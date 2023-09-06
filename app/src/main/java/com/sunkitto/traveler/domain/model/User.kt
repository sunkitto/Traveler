package com.sunkitto.traveler.domain.model

/**
 * Application [User] representation.
 */
data class User(
    val id: String,
    val userName: String,
    val profilePictureUrl: String,
    val email: String,
)