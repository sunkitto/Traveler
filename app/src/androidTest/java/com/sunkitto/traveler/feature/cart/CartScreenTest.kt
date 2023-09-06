package com.sunkitto.traveler.feature.cart

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
import com.sunkitto.traveler.feature.cart.model.OrderedEquipment
import com.sunkitto.traveler.ui.theme.TravelerTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.Currency

@OptIn(ExperimentalCoilApi::class)
class CartScreenTest {

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
                CartScreen(
                    uiState = CartState(listOf(testOrderedEquipment)),
                    onOrderCardEvent = {},
                    onOrderCardClick = {},
                )
            }
        }

        composeTestRule
            .onNodeWithText("Equipment 1")
            .assertIsDisplayed()
    }

    @Test
    fun equipment_image_is_displayed() {
        composeTestRule.setContent {
            TravelerTheme {
                CartScreen(
                    uiState = CartState(listOf(testOrderedEquipment)),
                    onOrderCardEvent = {},
                    onOrderCardClick = {},
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
    fun equipment_count_is_displayed() {
        composeTestRule.setContent {
            TravelerTheme {
                CartScreen(
                    uiState = CartState(listOf(testOrderedEquipment)),
                    onOrderCardEvent = {},
                    onOrderCardClick = {},
                )
            }
        }

        composeTestRule
            .onNodeWithText("1")
            .assertIsDisplayed()
    }

    @Test
    fun rent_days_is_displayed() {
        composeTestRule.setContent {
            TravelerTheme {
                CartScreen(
                    uiState = CartState(listOf(testOrderedEquipment)),
                    onOrderCardEvent = {},
                    onOrderCardClick = {},
                )
            }
        }

        composeTestRule
            .onNodeWithText("3")
            .assertIsDisplayed()
    }

    @Test
    fun total_price_is_displayed() {
        composeTestRule.setContent {
            TravelerTheme {
                CartScreen(
                    uiState = CartState(
                        orderedEquipments = listOf(testOrderedEquipment),
                        totalPrice = 20,
                    ),
                    onOrderCardEvent = {},
                    onOrderCardClick = {},
                )
            }
        }

        val locale = composeTestRule.activity.applicationContext.resources
            .configuration.locales.get(0)
        val currency = Currency.getInstance(locale).currencyCode

        composeTestRule
            .onNodeWithText(
                composeTestRule.activity.getString(R.string.total) + " 20 " + currency,
            )
            .assertIsDisplayed()
    }

    @Test
    fun error_message_is_displayed() {
        composeTestRule.setContent {
            TravelerTheme {
                CartScreen(
                    uiState = CartState(errorMessage = "Some error"),
                    onOrderCardEvent = {},
                    onOrderCardClick = {},
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
                CartScreen(
                    uiState = CartState(isLoading = true),
                    onOrderCardEvent = {},
                    onOrderCardClick = {},
                )
            }
        }

        composeTestRule
            .onNodeWithTag(testTag = "circularProgressBar")
            .assertIsDisplayed()
    }
}

private val testOrderedEquipment =
    OrderedEquipment(
        id = "",
        name = "Equipment 1",
        image = "",
        description = "",
        cost = 10,
        orderId = "",
        count = 1,
        days = 3,
        price = 30,
    )