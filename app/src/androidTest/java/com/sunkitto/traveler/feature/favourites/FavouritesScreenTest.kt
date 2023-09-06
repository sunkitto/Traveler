package com.sunkitto.traveler.feature.favourites

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import coil.Coil
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.test.FakeImageLoaderEngine
import com.sunkitto.traveler.R
import com.sunkitto.traveler.domain.model.Equipment
import com.sunkitto.traveler.ui.theme.TravelerTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoilApi::class)
class FavouritesScreenTest {

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
    fun favourite_equipment_name_is_displayed() {
        composeTestRule.setContent {
            TravelerTheme {
                FavouritesScreen(
                    uiState = FavouritesState(equipments = listOf(testEquipment)),
                    onEquipmentCardClick = {},
                )
            }
        }

        composeTestRule
            .onNodeWithText("Favourite 1")
            .assertIsDisplayed()
    }

    @Test
    fun favourite_equipment_image_is_displayed() {
        composeTestRule.setContent {
            TravelerTheme {
                FavouritesScreen(
                    uiState = FavouritesState(equipments = listOf(testEquipment)),
                    onEquipmentCardClick = {},
                )
            }
        }

        composeTestRule
            .onNodeWithContentDescription(
                composeTestRule.activity.getString(R.string.equipment),
            )
            .assertIsDisplayed()
    }

    @Test
    fun error_message_is_displayed() {
        composeTestRule.setContent {
            TravelerTheme {
                FavouritesScreen(
                    uiState = FavouritesState(errorMessage = "Some error"),
                    onEquipmentCardClick = {},
                )
            }
        }

        composeTestRule
            .onNodeWithText("Some error")
            .assertIsDisplayed()
    }

    @Test
    fun loading_is_displayed() {
        composeTestRule.setContent {
            TravelerTheme {
                FavouritesScreen(
                    uiState = FavouritesState(isLoading = true),
                    onEquipmentCardClick = {},
                )
            }
        }

        composeTestRule
            .onNodeWithTag(testTag = "circularProgressBar")
            .assertIsDisplayed()
    }
}

private val testEquipment =
    Equipment(
        id = "",
        name = "Favourite 1",
        image = "",
        description = "",
        cost = 0,
        categoryId = "",
    )