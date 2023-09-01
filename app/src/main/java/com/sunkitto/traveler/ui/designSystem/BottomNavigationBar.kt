package com.sunkitto.traveler.ui.designSystem

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.sunkitto.traveler.navigation.BottomBarScreen

@Composable
fun BottomNavigationBar(navController: NavHostController) {

     val bottomNavigationScreens = listOf(
         BottomBarScreen.Categories,
         BottomBarScreen.Cart,
         BottomBarScreen.Favourites,
         BottomBarScreen.Account,
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val bottomBarDestination = bottomNavigationScreens.any { bottomBarScreen ->
        bottomBarScreen.route == currentDestination?.route
    }

    if(bottomBarDestination) {
        NavigationBar {
            bottomNavigationScreens.forEach { bottomBarScreen ->
                BottomNavigationItem(
                    bottomBarScreen = bottomBarScreen,
                    currentDestination = currentDestination,
                    navController = navController,
                )
            }
        }
    }
}

@Composable
fun RowScope.BottomNavigationItem(
    bottomBarScreen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController,
) {
    NavigationBarItem(
        label = {
            Text(text = stringResource(id = bottomBarScreen.name))
        },
        icon = {
            Icon(
                painter = painterResource(id = bottomBarScreen.icon),
                contentDescription = stringResource(id = bottomBarScreen.name),
            )
        },
        selected = currentDestination?.hierarchy?.any { navDestination ->
            navDestination.route == bottomBarScreen.route
        } == true,
        onClick = {
            navController.navigate(bottomBarScreen.route) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        },

    )
}