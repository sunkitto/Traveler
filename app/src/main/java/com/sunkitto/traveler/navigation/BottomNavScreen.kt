package com.sunkitto.traveler.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.sunkitto.traveler.ui.designSystem.BottomNavigationBar

@Composable
fun BottomNavScreen(
    navController: NavHostController = rememberNavController(),
    onNavigationToAuthGraph: () -> Unit,
) {
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { innerPadding ->
        BottomNavGraph(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            onNavigationToAuthGraph = {
                onNavigationToAuthGraph()
            },
        )
    }
}