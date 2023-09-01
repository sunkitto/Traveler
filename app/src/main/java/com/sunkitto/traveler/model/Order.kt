package com.sunkitto.traveler.model

data class Order(
    val id: Int,
    val count: Int,
    val days: Int,
    val price: Int,
    val equipmentId: Int,
    val userId: String,
)