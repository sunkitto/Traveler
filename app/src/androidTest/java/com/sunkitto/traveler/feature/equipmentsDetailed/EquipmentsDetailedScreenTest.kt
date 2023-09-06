package com.sunkitto.traveler.feature.equipmentsDetailed

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
import com.sunkitto.traveler.feature.equipmentsDetailed.model.EquipmentUi
import com.sunkitto.traveler.ui.theme.TravelerTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.Currency

@OptIn(ExperimentalCoilApi::class)
class EquipmentsDetailedScreenTest {

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
    fun equipment_name_is_displayed() {
        composeTestRule.setContent {
            TravelerTheme {
                EquipmentsDetailedScreen(
                    state = EquipmentsDetailedState(testEquipmentUi),
                    onEquipmentFavorite = {},
                    onEquipmentDelete = {},
                    onBottomSheetEvent = {},
                    onBack = {},
                )
            }
        }

        composeTestRule
            .onNodeWithText("Equipment 1")
            .assertIsDisplayed()
    }

    @Test
    fun equipment_description_is_displayed() {
        composeTestRule.setContent {
            TravelerTheme {
                EquipmentsDetailedScreen(
                    state = EquipmentsDetailedState(testEquipmentUi),
                    onEquipmentFavorite = {},
                    onEquipmentDelete = {},
                    onBottomSheetEvent = {},
                    onBack = {},
                )
            }
        }

        composeTestRule
            .onNodeWithText("Some description")
            .assertIsDisplayed()
    }

    @Test
    fun equipment_image_is_displayed() {
        composeTestRule.setContent {
            TravelerTheme {
                EquipmentsDetailedScreen(
                    state = EquipmentsDetailedState(testEquipmentUi),
                    onEquipmentFavorite = {},
                    onEquipmentDelete = {},
                    onBottomSheetEvent = {},
                    onBack = {},
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
    fun equipment_cost_is_displayed() {
        composeTestRule.setContent {
            TravelerTheme {
                EquipmentsDetailedScreen(
                    state = EquipmentsDetailedState(testEquipmentUi),
                    onEquipmentFavorite = {},
                    onEquipmentDelete = {},
                    onBottomSheetEvent = {},
                    onBack = {},
                )
            }
        }

        val locale = composeTestRule.activity.applicationContext.resources
            .configuration.locales.get(0)
        val currency = Currency.getInstance(locale).currencyCode

        composeTestRule
            .onNodeWithText("20 $currency")
            .assertIsDisplayed()
    }

    @Test
    fun error_message_is_displayed() {
        composeTestRule.setContent {
            TravelerTheme {
                EquipmentsDetailedScreen(
                    state = EquipmentsDetailedState(errorMessage = "Some error"),
                    onEquipmentFavorite = {},
                    onEquipmentDelete = {},
                    onBottomSheetEvent = {},
                    onBack = {},
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
                EquipmentsDetailedScreen(
                    state = EquipmentsDetailedState(isLoading = true),
                    onEquipmentFavorite = {},
                    onEquipmentDelete = {},
                    onBottomSheetEvent = {},
                    onBack = {},
                )
            }
        }

        composeTestRule
            .onNodeWithTag(testTag = "circularProgressBar")
            .assertIsDisplayed()
    }
}

private val testEquipmentUi =
    EquipmentUi(
        id = "",
        name = "Equipment 1",
        image = "",
        description = "Some description",
        cost = 20,
        categoryId = "",
        favouriteId = null,
        isFavourite = false,
        orderId = null,
        isOrdered = false,
    )