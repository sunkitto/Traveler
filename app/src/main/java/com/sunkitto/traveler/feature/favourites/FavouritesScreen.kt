package com.sunkitto.traveler.feature.favourites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sunkitto.traveler.R
import com.sunkitto.traveler.domain.model.Equipment
import com.sunkitto.traveler.ui.designSystem.EmptyContent
import com.sunkitto.traveler.ui.designSystem.EquipmentCard
import com.sunkitto.traveler.ui.designSystem.LoadingContent
import com.sunkitto.traveler.ui.designSystem.ScreenTitleText
import com.sunkitto.traveler.ui.theme.TravelerTheme

@Composable
fun FavouritesScreen(
    uiState: FavouritesState,
    onEquipmentCardClick: (equipmentId: String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 25.dp),
    ) {
        ScreenTitleText(
            modifier = Modifier.padding(vertical = 35.dp),
            text = stringResource(id = R.string.favourites),
        )
        when {
            uiState.isLoading -> {
                LoadingContent()
            }
            uiState.equipments.isEmpty() && uiState.errorMessage == null -> {
                EmptyContent(
                    stringResource(id = R.string.favorites_empty),
                )
            }
            uiState.errorMessage != null -> {
                EmptyContent(descriptionText = uiState.errorMessage)
            }
            else -> {
                FavouritesList(
                    equipments = uiState.equipments,
                    onEquipmentCardClick = onEquipmentCardClick,
                )
            }
        }
    }
}

@Composable
fun FavouritesList(
    equipments: List<Equipment>,
    onEquipmentCardClick: (equipmentId: String) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(
            items = equipments,
            itemContent = { equipment ->
                EquipmentCard(
                    equipment = equipment,
                    onEquipmentClick = onEquipmentCardClick,
                )
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FavouritesScreenPreview() {
    TravelerTheme {
        FavouritesScreen(
            uiState = FavouritesState(
                equipments = List(10) {
                    Equipment(
                        id = "",
                        name = "Equipment ${it + 1}",
                        image = "",
                        description = "",
                        cost = 0,
                        categoryId = "",
                    )
                },
            ),
            onEquipmentCardClick = {},
        )
    }
}