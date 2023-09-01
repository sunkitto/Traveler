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
import com.sunkitto.traveler.model.Equipment
import com.sunkitto.traveler.ui.designSystem.EmptyContent
import com.sunkitto.traveler.ui.designSystem.EquipmentCard
import com.sunkitto.traveler.ui.designSystem.LoadingContent
import com.sunkitto.traveler.ui.designSystem.ScreenTitleText
import com.sunkitto.traveler.ui.theme.TravelerTheme

@Composable
fun FavouritesScreen(
    uiState: FavouritesState,
    onEquipmentCardClick: (equipmentId: Int) -> Unit,
) {
    Column(Modifier.fillMaxSize()) {
        ScreenTitleText(
            modifier = Modifier.padding(horizontal = 25.dp, vertical = 40.dp),
            text = stringResource(id = R.string.favourites)
        )
        if(uiState.isLoading) {
            LoadingContent()
        }
        if(uiState.errorMessage != null) {
            EmptyContent(descriptionText = uiState.errorMessage)
        }
        if(uiState.equipments == null) {
            EmptyContent(
                stringResource(id = R.string.favorites_empty)
            )
        } else {
            FavouritesList(
                equipments = uiState.equipments,
                onEquipmentCardClick = onEquipmentCardClick
            )
        }
    }
}

@Composable
fun FavouritesList(
    equipments: List<Equipment>,
    onEquipmentCardClick: (equipmentId: Int) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 25.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            items = equipments,
            itemContent = { equipment ->
                EquipmentCard(
                    equipment = equipment,
                    onEquipmentClick = onEquipmentCardClick
                )
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FavouritesScreenPreview() {
    TravelerTheme {
        FavouritesScreen(
            uiState = FavouritesState(
                equipments = listOf(
                    Equipment(name = "Equipment 1"),
                    Equipment(name = "Equipment 2"),
                    Equipment(name = "Equipment 3"),
                    Equipment(name = "Equipment 4"),
                    Equipment(name = "Equipment 5"),
                )
            ),
            onEquipmentCardClick = {},
        )
    }
}