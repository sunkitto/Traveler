package com.sunkitto.traveler.feature.auth.navigation

import android.app.Activity.RESULT_OK
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.sunkitto.traveler.feature.auth.sign_in.SignInEvent
import com.sunkitto.traveler.feature.auth.sign_in.SignInScreen
import com.sunkitto.traveler.feature.auth.sign_in.SignInViewModel
import com.sunkitto.traveler.navigation.constants.Graph
import com.sunkitto.traveler.navigation.constants.Route
import com.sunkitto.traveler.ui.designSystem.TravelerSnackBarVisuals
import kotlinx.coroutines.launch

fun NavGraphBuilder.authGraph(navController: NavController) {
    navigation(
        route = Graph.AUTH_GRAPH,
        startDestination = Route.SIGN_IN
    ) {
        composable(route = Route.SIGN_IN) {

            val viewModel = hiltViewModel<SignInViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()

            val snackBarHostState = remember { SnackbarHostState() }
            val scope = rememberCoroutineScope()

            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartIntentSenderForResult(),
                onResult = { result ->
                    if(result.resultCode == RESULT_OK) {
                        val data = result.data ?: return@rememberLauncherForActivityResult
                        viewModel.onEvent(SignInEvent.SignInWithGoogle(data))
                    }
                }
            )

            LaunchedEffect(key1 = state.intentSender) {
                val intentSender = state.intentSender
                if(intentSender != null) {
                    launcher.launch(
                        IntentSenderRequest.Builder(intentSender).build()
                    )
                }
            }
            LaunchedEffect(key1 = state.isSuccess) {
                if(state.isSuccess)
                    navController.navigate(Graph.BOTTOM_NAV_GRAPH)
            }
            LaunchedEffect(key1 = state.errorMessage) {
                val errorMessage = state.errorMessage
                if(errorMessage != null) {
                    scope.launch {
                        snackBarHostState.showSnackbar(
                            TravelerSnackBarVisuals(errorMessage)
                        )
                    }
                }
            }

            SignInScreen(
                state = state,
                snackBarHostState = snackBarHostState,
                onGoogleSignInClick = {
                    viewModel.onEvent(SignInEvent.SignInIntent)
                }
            )
        }
    }
}