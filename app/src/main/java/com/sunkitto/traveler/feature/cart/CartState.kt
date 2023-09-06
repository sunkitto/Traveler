package com.sunkitto.traveler.feature.cart

import com.sunkitto.traveler.feature.cart.model.OrderedEquipment

data class CartState(
    val orderedEquipments: List<OrderedEquipment> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val totalPrice: Int = 0,
)