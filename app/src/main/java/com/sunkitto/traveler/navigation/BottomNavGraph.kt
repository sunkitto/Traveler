package com.sunkitto.traveler.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.sunkitto.traveler.feature.account.navigation.accountNavigation
import com.sunkitto.traveler.feature.cart.navigation.cartNavigation
import com.sunkitto.traveler.feature.categories.navigation.categoriesNavigation
import com.sunkitto.traveler.feature.categories.navigation.navigateToEquipments
import com.sunkitto.traveler.feature.equipments.navigation.equipmentsGraph
import com.sunkitto.traveler.feature.equipmentsDetailed.navigation.navigateToEquipmentsDetailed
import com.sunkitto.traveler.feature.favourites.navigation.favouritesNavigation
import com.sunkitto.traveler.feature.search.navigation.searchNavigation
import com.sunkitto.traveler.navigation.constants.Graph
import com.sunkitto.traveler.navigation.constants.Route

@Composable
fun BottomNavGraph(
    modifier: Modifier,
    navController: NavHostController,
    onNavigationToAuthGraph: () -> Unit,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        route = Graph.BOTTOM_NAV_GRAPH,
        startDestination = Route.CATEGORIES,
    ) {
        categoriesNavigation(
            onNavigateToEquipments = { categoryId, categoryName ->
                navController.navigateToEquipments(
                    categoryId = categoryId,
                    categoryName = categoryName,
                )
            },
            onNavigateToSearch = {
                navController.navigate(Route.SEARCH)
            },
        )
        cartNavigation(
            onNavigateToEquipmentDetailed = { equipmentId ->
                navController.navigateToEquipmentsDetailed(equipmentId)
            },
        )
        favouritesNavigation(
            onNavigateToEquipmentDetailed = { equipmentId ->
                navController.navigateToEquipmentsDetailed(equipmentId)
            },
        )
        accountNavigation(
            onNavigationToAuthGraph = {
                navController.popBackStack()
                onNavigationToAuthGraph()
            },
        )
        equipmentsGraph(navController = navController)
        searchNavigation(
            onNavigateToEquipmentDetailed = { equipmentId ->
                navController.navigateToEquipmentsDetailed(equipmentId)
            },
            onBackClick = {
                navController.popBackStack()
            },
        )
    }
}