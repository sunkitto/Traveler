package com.sunkitto.traveler.feature.search.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sunkitto.traveler.feature.search.SearchScreen
import com.sunkitto.traveler.feature.search.SearchViewModel
import com.sunkitto.traveler.navigation.constants.Route

fun NavGraphBuilder.searchNavigation(
    onNavigateToEquipmentDetailed: (equipmentId: Int) -> Unit,
    onBackClick: () -> Unit,
) {
    composable(route = Route.SEARCH) {

        val viewModel = hiltViewModel<SearchViewModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()

        SearchScreen(
            uiState = state,
            onEquipmentClick = { equipmentId ->
                onNavigateToEquipmentDetailed(equipmentId)
            },
            onBackClick = {
                onBackClick()
            }
        )
    }
}