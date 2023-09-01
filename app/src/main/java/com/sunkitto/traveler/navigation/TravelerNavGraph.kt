package com.sunkitto.traveler.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sunkitto.traveler.feature.auth.navigation.authGraph
import com.sunkitto.traveler.navigation.constants.Graph

@Composable
fun TravelerNavGraph(
    navController: NavHostController,
    startDestination: String,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        authGraph(navController = navController)
        composable(route = Graph.BOTTOM_NAV_GRAPH) {
            BottomNavScreen(
                onNavigationToAuthGraph = {
                    navController.popBackStack()
                    navController.navigate(route = Graph.AUTH_GRAPH)
                }
            )
        }
    }
}