package com.sunkitto.traveler.feature.account.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sunkitto.traveler.feature.account.AccountEvent
import com.sunkitto.traveler.feature.account.AccountScreen
import com.sunkitto.traveler.feature.account.AccountViewModel
import com.sunkitto.traveler.navigation.constants.Route
import com.sunkitto.traveler.ui.designSystem.TravelerSnackBarVisuals
import kotlinx.coroutines.launch

fun NavGraphBuilder.accountNavigation(
    onNavigationToAuthGraph: () -> Unit,
) {
    composable(route = Route.ACCOUNT) {

        val viewModel = hiltViewModel<AccountViewModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()

        val snackBarHostState = remember { SnackbarHostState() }
        val scope = rememberCoroutineScope()

        LaunchedEffect(key1 = Unit) {
            viewModel.onEvent(AccountEvent.LoadUser)
        }

        LaunchedEffect(key1 = state.isSignedOut) {
            if(state.isSignedOut) {
                onNavigationToAuthGraph()
            }
        }
        LaunchedEffect(key1 = state.errorMessage) {
            val errorMessage = state.errorMessage
            if(errorMessage != null) {
                scope.launch {
                    snackBarHostState.currentSnackbarData?.dismiss()
                    snackBarHostState.showSnackbar(
                        TravelerSnackBarVisuals(errorMessage)
                    )
                }
            }
        }

        AccountScreen(
            state = state,
            snackBarHostState = snackBarHostState,
            onSignOutClick = {
                viewModel.onEvent(AccountEvent.SignOut)
            },
        )
    }
}