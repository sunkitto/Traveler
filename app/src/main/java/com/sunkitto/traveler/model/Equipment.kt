package com.sunkitto.traveler.model

data class Equipment(
    val id: Int = 0,
    val name: String = "",
    val image: String = "",
    val description: String = "",
    val cost: Int = 0,
    val categoryId: Int = 0,
)