package com.sunkitto.traveler.feature.equipments.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.sunkitto.traveler.feature.equipments.EquipmentsEvent
import com.sunkitto.traveler.feature.equipments.EquipmentsScreen
import com.sunkitto.traveler.feature.equipments.EquipmentsViewModel
import com.sunkitto.traveler.navigation.constants.Graph
import com.sunkitto.traveler.navigation.constants.Route

private const val CATEGORY_ID_ARG = "categoryId"

fun NavGraphBuilder.equipmentsGraph(navController: NavController) {
    navigation(
        route = Graph.EQUIPMENTS_GRAPH,
        startDestination = Route.EQUIPMENTS
    ) {
        composable(route = Route.EQUIPMENTS) { navBackStackEntry ->

            val viewModel = hiltViewModel<EquipmentsViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()

            LaunchedEffect(key1 = Unit) {
                viewModel.onEvent(EquipmentsEvent.LoadEquipments(categoryId = 1))
            }

            EquipmentsScreen(
                uiState = state,
                categoryName = "",
                onEquipmentClick = { equipmentId ->

                },
                onBackClick = {
                    navController.popBackStack()
                },
                onSortClick = {
                    // BottomSheet
                },
            )
        }
        composable(route = Route.EQUIPMENTS_DETAILED) {

        }
    }
}