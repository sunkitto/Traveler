package com.sunkitto.traveler.feature.categories.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.sunkitto.traveler.feature.categories.CategoriesEvent
import com.sunkitto.traveler.feature.categories.CategoriesScreen
import com.sunkitto.traveler.feature.categories.CategoriesViewModel
import com.sunkitto.traveler.navigation.constants.Route

fun NavController.navigateToEquipments(
    categoryId: String,
    categoryName: String,
    navOptions: NavOptions? = null,
) {
    this.navigate("equipments_graph/$categoryId/$categoryName", navOptions)
}

fun NavGraphBuilder.categoriesNavigation(
    onNavigateToEquipments: (
        categoryId: String,
        categoryName: String,
    ) -> Unit,
    onNavigateToSearch: () -> Unit,
) {
    composable(route = Route.CATEGORIES) {
        val viewModel = hiltViewModel<CategoriesViewModel>()
        val uiState by viewModel.state.collectAsStateWithLifecycle()

        LaunchedEffect(key1 = Unit) {
            viewModel.onEvent(event = CategoriesEvent.LoadCategories)
        }
        CategoriesScreen(
            uiState = uiState,
            onCategoryClick = { categoryId, categoryName ->
                onNavigateToEquipments(
                    categoryId,
                    categoryName,
                )
            },
            onSearchClick = {
                onNavigateToSearch()
            },
        )
    }
}