package com.sunkitto.traveler

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.sunkitto.traveler.navigation.TravelerNavGraph
import com.sunkitto.traveler.navigation.constants.Graph
import com.sunkitto.traveler.ui.theme.TravelerTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Provider

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var firebaseAuth: Provider<FirebaseAuth>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TravelerTheme {
                if(firebaseAuth.get().currentUser != null) {
                    TravelerNavGraph(
                        navController = rememberNavController(),
                        startDestination = Graph.BOTTOM_NAV_GRAPH,
                    )
                } else {
                    TravelerNavGraph(
                        navController = rememberNavController(),
                        startDestination = Graph.AUTH_GRAPH,
                    )
                }
            }
        }
    }
}