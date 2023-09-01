package com.sunkitto.traveler.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sunkitto.traveler.feature.account.navigation.accountNavigation
import com.sunkitto.traveler.feature.cart.CartScreen
import com.sunkitto.traveler.feature.cart.CartViewModel
import com.sunkitto.traveler.feature.categories.navigation.categoriesNavigation
import com.sunkitto.traveler.feature.equipments.navigation.equipmentsGraph
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
                navController.navigate(Route.EQUIPMENTS)
            },
            onNavigateToSearch = {
                navController.navigate(Route.SEARCH)
            }
        )
        composable(route = Route.CART) {

            val viewModel = hiltViewModel<CartViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()

            CartScreen()
        }
        favouritesNavigation(
            onNavigateToEquipmentDetailed = {
                navController.navigate(Route.EQUIPMENTS_DETAILED)
            }
        )
        accountNavigation(
            onNavigationToAuthGraph = {
                navController.popBackStack()
                onNavigationToAuthGraph()
            }
        )
        equipmentsGraph(navController = navController)
        searchNavigation(
            onNavigateToEquipmentDetailed = { equipmentId ->

            },
            onBackClick = {
                navController.popBackStack()
            },
        )
    }
}