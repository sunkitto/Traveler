package com.sunkitto.traveler.feature.categories

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
import com.sunkitto.traveler.domain.model.Category
import com.sunkitto.traveler.ui.theme.TravelerTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoilApi::class)
class CategoriesScreenTest {

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
    fun category_name_is_displayed() {
        composeTestRule.setContent {
            TravelerTheme {
                CategoriesScreen(
                    uiState = CategoriesState(categories = listOf(testCategory)),
                    onCategoryClick = { _, _ -> },
                    onSearchClick = {},
                )
            }
        }

        composeTestRule
            .onNodeWithText("Category 1")
            .assertIsDisplayed()
    }

    @Test
    fun category_image_is_displayed() {
        composeTestRule.setContent {
            TravelerTheme {
                CategoriesScreen(
                    uiState = CategoriesState(categories = listOf(testCategory)),
                    onCategoryClick = { _, _ -> },
                    onSearchClick = {},
                )
            }
        }

        composeTestRule
            .onNodeWithContentDescription(
                composeTestRule.activity.getString(R.string.category),
            )
            .assertIsDisplayed()
    }

    @Test
    fun error_message_is_displayed() {
        composeTestRule.setContent {
            TravelerTheme {
                CategoriesScreen(
                    uiState = CategoriesState(errorMessage = "Some error"),
                    onCategoryClick = { _, _ -> },
                    onSearchClick = {},
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
                CategoriesScreen(
                    uiState = CategoriesState(isLoading = true),
                    onCategoryClick = { _, _ -> },
                    onSearchClick = {},
                )
            }
        }

        composeTestRule
            .onNodeWithTag(testTag = "circularProgressBar")
            .assertIsDisplayed()
    }
}

private val testCategory =
    Category(
        id = "",
        name = "Category 1",
        image = "",
    )