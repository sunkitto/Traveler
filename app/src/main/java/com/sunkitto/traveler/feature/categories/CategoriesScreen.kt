package com.sunkitto.traveler.feature.categories

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.sunkitto.traveler.model.Category
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
        categoryId: Int,
        categoryName: String,
    ) -> Unit,
    onSearchClick: () -> Unit,
) {
    Column(Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .padding(horizontal = 25.dp, vertical = 40.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ScreenTitleText(text = stringResource(id = R.string.categories))
            Surface(shape = MaterialTheme.shapes.medium) {
                TravelerIconButton(
                    contentDescription = stringResource(id = R.string.search),
                    icon = Icons.Rounded.Search,
                    onClick = {
                        onSearchClick()
                    }
                )
            }
        }
        if(uiState.isLoading) {
            LoadingContent()
        }
        if(uiState.errorMessage != null) {
            EmptyContent(descriptionText = uiState.errorMessage)
        }
        if(uiState.categories == null) {
            EmptyContent(descriptionText = stringResource(id = R.string.categories_empty))
        } else {
            CategoriesList(
                categories = uiState.categories
            ) { categoryId, categoryName ->
                onCategoryClick(categoryId, categoryName)
            }
        }
    }
}

@Composable
fun CategoriesList(
    categories: List<Category>,
    onCategoryClick: (
        categoryId: Int,
        categoryName: String,
    ) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(9.dp)
    ) {
        items(items = categories.chunked(2)) { rowItems ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                modifier = Modifier.padding(horizontal = 16.dp),
            ) {
                for (category in rowItems) {
                    CategoryCard(
                        category = category,
                        onCategoryClick = { categoryId, categoryName ->
                            onCategoryClick(categoryId, categoryName)
                        }
                    )
                }
            }
            Spacer(Modifier.height(16.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CategoriesScreenPreview() {
    TravelerTheme {
        CategoriesScreen(
            uiState = CategoriesState(
                categories = listOf(
                    Category(name = "Category 1"),
                    Category(name = "Category 2"),
                    Category(name = "Category 3"),
                    Category(name = "Category 4"),
                    Category(name = "Category 5"),
                )
            ),
            onCategoryClick = { _, _ -> },
            onSearchClick = {}
        )
    }
}