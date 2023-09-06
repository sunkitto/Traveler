package com.sunkitto.traveler.domain.model

/**
 * Domain representation of the [Equipment].
 */
data class Equipment(
    val id: String,
    val name: String,
    val image: String,
    val description: String,
    val cost: Int,
    val categoryId: String,
)