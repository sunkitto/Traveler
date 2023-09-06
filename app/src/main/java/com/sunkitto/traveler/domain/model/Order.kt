package com.sunkitto.traveler.domain.model

/**
 * Domain representation of the [Order].
 */
data class Order(
    val id: String,
    val count: Int,
    val days: Int,
    val price: Int,
    val equipmentId: String,
)