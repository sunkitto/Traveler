package com.sunkitto.traveler.feature.cart.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sunkitto.traveler.feature.cart.CartEvent
import com.sunkitto.traveler.feature.cart.CartScreen
import com.sunkitto.traveler.feature.cart.CartViewModel
import com.sunkitto.traveler.navigation.constants.Route

fun NavGraphBuilder.cartNavigation(
    onNavigateToEquipmentDetailed: (equipmentId: String) -> Unit,
) {
    composable(route = Route.CART) {
        val viewModel = hiltViewModel<CartViewModel>()
        val uiState by viewModel.state.collectAsStateWithLifecycle()

        LaunchedEffect(key1 = Unit) {
            viewModel.onEvent(CartEvent.LoadOrders)
        }

        CartScreen(
            uiState = uiState,
            onOrderCardEvent = { cartEvent ->
                viewModel.onEvent(cartEvent)
            },
            onOrderCardClick = { equipmentId ->
                onNavigateToEquipmentDetailed(equipmentId)
            },
        )
    }
}