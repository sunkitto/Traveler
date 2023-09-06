package com.sunkitto.traveler.feature.categories

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sunkitto.traveler.R
import com.sunkitto.traveler.domain.model.Category
import com.sunkitto.traveler.ui.designSystem.CategoryCard
import com.sunkitto.traveler.ui.designSystem.EmptyContent
import com.sunkitto.traveler.ui.designSystem.LoadingContent
import com.sunkitto.traveler.ui.designSystem.ScreenTitleText
import com.sunkitto.traveler.ui.designSystem.TravelerIconButton
import com.sunkitto.traveler.ui.theme.TravelerTheme

@Composable
fun CategoriesScreen(
    uiState: CategoriesState,
    onCategoryClick: (
        categoryId: String,
        categoryName: String,
    ) -> Unit,
    onSearchClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 25.dp),
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 35.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ScreenTitleText(text = stringResource(id = R.string.categories))
            Surface(
                modifier = Modifier,
                shape = MaterialTheme.shapes.medium,
            ) {
                TravelerIconButton(
                    contentDescription = stringResource(id = R.string.search),
                    icon = Icons.Rounded.Search,
                    onClick = {
                        onSearchClick()
                    },
                )
            }
        }
        when {
            uiState.isLoading -> {
                LoadingContent()
            }
            uiState.categories.isEmpty() && uiState.errorMessage == null -> {
                EmptyContent(descriptionText = stringResource(id = R.string.categories_empty))
            }
            uiState.errorMessage != null -> {
                EmptyContent(descriptionText = uiState.errorMessage)
            }
            else -> {
                CategoriesList(
                    categories = uiState.categories,
                ) { categoryId, categoryName ->
                    onCategoryClick(categoryId, categoryName)
                }
            }
        }
    }
}

@Composable
fun CategoriesList(
    categories: List<Category>,
    onCategoryClick: (
        categoryId: String,
        categoryName: String,
    ) -> Unit,
) {
    LazyVerticalGrid(
        modifier = Modifier
            .padding(bottom = 25.dp)
            .fillMaxWidth(),
        columns = GridCells.Adaptive(minSize = 150.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(categories) { category ->
            CategoryCard(
                category = category,
                onCategoryClick = { categoryId, categoryName ->
                    onCategoryClick(categoryId, categoryName)
                },
            )
        }
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "Light",
    showBackground = true,
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark",
    showBackground = true,
)
@Composable
fun CategoriesScreenPreview() {
    TravelerTheme {
        Surface {
            CategoriesScreen(
                uiState = CategoriesState(
                    categories = List(14) {
                        Category(
                            id = "",
                            name = "Category ${it + 1}",
                            image = "",
                        )
                    },
                ),
                onCategoryClick = { _, _ -> },
                onSearchClick = {},
            )
        }
    }
}