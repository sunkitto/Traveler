package com.sunkitto.traveler.feature.equipmentsDetailed.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType.Companion.StringType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.sunkitto.traveler.feature.equipmentsDetailed.EquipmentsDetailedEvent
import com.sunkitto.traveler.feature.equipmentsDetailed.EquipmentsDetailedScreen
import com.sunkitto.traveler.feature.equipmentsDetailed.EquipmentsDetailedViewModel
import com.sunkitto.traveler.navigation.constants.Route

private const val EQUIPMENT_ID_ARG = "equipmentId"

fun NavController.navigateToEquipmentsDetailed(
    equipmentId: String,
    navOptions: NavOptions? = null,
) {
    this.navigate("equipments_detailed_route/$equipmentId", navOptions)
}

fun NavGraphBuilder.equipmentsDetailedNavigation(
    onBack: () -> Unit,
) {
    composable(
        route = Route.EQUIPMENTS_DETAILED,
        arguments = listOf(navArgument(EQUIPMENT_ID_ARG) { type = StringType }),
    ) {
        val viewModel = hiltViewModel<EquipmentsDetailedViewModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()

        LaunchedEffect(key1 = Unit) {
            viewModel.onEvent(EquipmentsDetailedEvent.LoadEquipment)
        }

        EquipmentsDetailedScreen(
            state = state,
            onEquipmentFavorite = {
                viewModel.onEvent(EquipmentsDetailedEvent.OnFavourite)
            },
            onEquipmentDelete = {
                viewModel.onEvent(EquipmentsDetailedEvent.OnRemoveFromCart)
            },
            onBottomSheetEvent = { bottomSheetEvent ->
                viewModel.onEvent(bottomSheetEvent)
            },
            onBack = {
                onBack()
            },
        )
    }
}