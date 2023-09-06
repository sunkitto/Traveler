package com.sunkitto.traveler.feature.account

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.activity.ComponentActivity
import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertRangeInfoEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import coil.Coil
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.test.FakeImageLoaderEngine
import com.sunkitto.traveler.R
import com.sunkitto.traveler.domain.model.User
import com.sunkitto.traveler.ui.theme.TravelerTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoilApi::class)
class AccountScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Before
    fun before() {
        val engine = FakeImageLoaderEngine.Builder()
            .intercept(
                "https://gtolimited.com/wp-content/uploads/2018/06/products-7.jpg",
                ColorDrawable(Color.RED),
            )
            .intercept(
                { it is String && it.endsWith("products-7.jpg") },
                ColorDrawable(Color.GREEN),
            )
            .default(ColorDrawable(Color.BLUE))
            .build()
        val imageLoader = ImageLoader.Builder(composeTestRule.activity.applicationContext)
            .components { add(engine) }
            .build()
        Coil.setImageLoader(imageLoader)
    }

    @Test
    fun user_name_is_displayed() {
        composeTestRule.setContent {
            TravelerTheme {
                AccountScreen(
                    uiState = AccountState(user = testUser),
                    snackBarHostState = SnackbarHostState(),
                    onSignOutClick = {},
                )
            }
        }

        composeTestRule
            .onNodeWithText("Mike")
            .assertIsDisplayed()
    }

    @Test
    fun email_is_displayed() {
        composeTestRule.setContent {
            TravelerTheme {
                AccountScreen(
                    uiState = AccountState(user = testUser),
                    snackBarHostState = SnackbarHostState(),
                    onSignOutClick = {},
                )
            }
        }

        composeTestRule
            .onNodeWithText("example@gmail.com")
            .assertIsDisplayed()
    }

    @Test
    fun profile_picture_is_displayed() {
        composeTestRule.setContent {
            TravelerTheme {
                AccountScreen(
                    uiState = AccountState(user = testUser),
                    snackBarHostState = SnackbarHostState(),
                    onSignOutClick = {},
                )
            }
        }

        composeTestRule
            .onNodeWithContentDescription(
                composeTestRule.activity.getString(R.string.profile_picture),
            )
            .assertIsDisplayed()
    }

    @Test
    fun loading_is_displayed() {
        composeTestRule.setContent {
            TravelerTheme {
                AccountScreen(
                    uiState = AccountState(isLoading = true),
                    snackBarHostState = SnackbarHostState(),
                    onSignOutClick = {},
                )
            }
        }

        composeTestRule
            .onNodeWithTag(testTag = "accountLinearProgressBar")
            .assertIsDisplayed()
            .assertRangeInfoEquals(ProgressBarRangeInfo.Indeterminate)
    }
}

private val testUser =
    User(
        id = "",
        userName = "Mike",
        profilePictureUrl = "",
        email = "example@gmail.com",
    )