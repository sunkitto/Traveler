package com.sunkitto.traveler.feature.equipmentsDetailed.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sunkitto.traveler.feature.equipmentsDetailed.EquipmentsDetailedEvent
import com.sunkitto.traveler.feature.equipmentsDetailed.EquipmentsDetailedScreen
import com.sunkitto.traveler.feature.equipmentsDetailed.EquipmentsDetailedViewModel
import com.sunkitto.traveler.navigation.constants.Route

private const val EQUIPMENT_ID_ARG = "equipmentId"

fun NavGraphBuilder.equipmentsDetailedNavigation(
    onBack: () -> Unit,
) {
    composable(route = Route.EQUIPMENTS_DETAILED + "/$EQUIPMENT_ID_ARG") {
        val viewModel = hiltViewModel<EquipmentsDetailedViewModel>()
        val uiState by viewModel.state.collectAsStateWithLifecycle()
        val equipmentId = 1

        LaunchedEffect(key1 = Unit) {
            viewModel.onEvent(
                event = EquipmentsDetailedEvent.LoadEquipment(equipmentId = equipmentId)
            )
        }

        EquipmentsDetailedScreen(
            state = uiState,
            onEquipmentFavorite = {

            },
            onEquipmentDelete = {

            },
            onBack = {
                onBack()
            }
        )
    }
}