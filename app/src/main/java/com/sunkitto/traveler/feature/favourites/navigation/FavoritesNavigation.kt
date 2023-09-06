package com.sunkitto.traveler.feature.favourites.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sunkitto.traveler.feature.favourites.FavouritesEvent
import com.sunkitto.traveler.feature.favourites.FavouritesScreen
import com.sunkitto.traveler.feature.favourites.FavouritesViewModel
import com.sunkitto.traveler.navigation.constants.Route

fun NavGraphBuilder.favouritesNavigation(
    onNavigateToEquipmentDetailed: (equipmentId: String) -> Unit,
) {
    composable(route = Route.FAVOURITES) {
        val viewModel = hiltViewModel<FavouritesViewModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()

        LaunchedEffect(key1 = Unit) {
            viewModel.onEvent(FavouritesEvent.LoadFavourites)
        }

        FavouritesScreen(
            uiState = state,
            onEquipmentCardClick = { equipmentId ->
                onNavigateToEquipmentDetailed(equipmentId)
            },
        )
    }
}