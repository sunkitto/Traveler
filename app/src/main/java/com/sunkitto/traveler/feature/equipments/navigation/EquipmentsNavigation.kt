package com.sunkitto.traveler.feature.equipments.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.sunkitto.traveler.feature.equipments.EquipmentsEvent
import com.sunkitto.traveler.feature.equipments.EquipmentsScreen
import com.sunkitto.traveler.feature.equipments.EquipmentsViewModel
import com.sunkitto.traveler.feature.equipmentsDetailed.navigation.equipmentsDetailedNavigation
import com.sunkitto.traveler.feature.equipmentsDetailed.navigation.navigateToEquipmentsDetailed
import com.sunkitto.traveler.navigation.constants.Graph
import com.sunkitto.traveler.navigation.constants.Route

private const val CATEGORY_ID_ARG = "categoryId"
private const val CATEGORY_NAME_ARG = "categoryName"

fun NavGraphBuilder.equipmentsGraph(navController: NavController) {
    navigation(
        route = Graph.EQUIPMENTS_GRAPH,
        startDestination = Route.EQUIPMENTS,
        arguments = listOf(
            navArgument(CATEGORY_ID_ARG) { type = NavType.StringType },
            navArgument(CATEGORY_NAME_ARG) { type = NavType.StringType },
        ),
    ) {
        composable(
            route = Route.EQUIPMENTS,
            arguments = listOf(
                navArgument(CATEGORY_ID_ARG) { type = NavType.StringType },
                navArgument(CATEGORY_NAME_ARG) { type = NavType.StringType },
            ),
        ) {
            val viewModel = hiltViewModel<EquipmentsViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()

            LaunchedEffect(key1 = Unit) {
                viewModel.onEvent(EquipmentsEvent.LoadEquipments)
            }

            EquipmentsScreen(
                uiState = state,
                categoryName = state.categoryName,
                onEquipmentClick = { equipmentId ->
                    navController.navigateToEquipmentsDetailed(equipmentId)
                },
                onSort = { sortType ->
                    viewModel.onEvent(EquipmentsEvent.SortEquipment(sortType))
                },
                onBack = {
                    navController.popBackStack()
                },
            )
        }
        equipmentsDetailedNavigation(
            onBack = {
                navController.popBackStack()
            },
        )
    }
}