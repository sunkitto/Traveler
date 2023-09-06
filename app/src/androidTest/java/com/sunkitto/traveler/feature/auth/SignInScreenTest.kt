package com.sunkitto.traveler.feature.auth

import androidx.activity.ComponentActivity
import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertRangeInfoEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.sunkitto.traveler.feature.auth.signIn.SignInScreen
import com.sunkitto.traveler.feature.auth.signIn.SignInState
import com.sunkitto.traveler.ui.theme.TravelerTheme
import org.junit.Rule
import org.junit.Test

class SignInScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun loading_is_displayed() {
        composeTestRule.setContent {
            TravelerTheme {
                SignInScreen(
                    state = SignInState(
                        isLoading = true,
                    ),
                    snackBarHostState = SnackbarHostState(),
                    onGoogleSignInClick = {},
                )
            }
        }

        composeTestRule
            .onNodeWithTag(testTag = "signInProgressBar")
            .assertIsDisplayed()
            .assertRangeInfoEquals(ProgressBarRangeInfo.Indeterminate)
    }
}