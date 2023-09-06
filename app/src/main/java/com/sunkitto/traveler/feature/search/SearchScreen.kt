package com.sunkitto.traveler.feature.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sunkitto.traveler.R
import com.sunkitto.traveler.domain.model.Equipment
import com.sunkitto.traveler.ui.designSystem.EmptyContent
import com.sunkitto.traveler.ui.designSystem.EquipmentCard
import com.sunkitto.traveler.ui.designSystem.LoadingContent
import com.sunkitto.traveler.ui.designSystem.SearchTextField
import com.sunkitto.traveler.ui.designSystem.TravelerIconButton
import com.sunkitto.traveler.ui.theme.TravelerTheme

@Composable
fun SearchScreen(
    uiState: SearchState,
    onEquipmentClick: (equipmentId: String) -> Unit,
    onBackClick: () -> Unit,
    onTextChange: (query: String) -> Unit,
    onClear: () -> Unit,
) {
    Column(Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .padding(horizontal = 25.dp, vertical = 40.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TravelerIconButton(
                modifier = Modifier.padding(end = 25.dp),
                icon = Icons.Rounded.ArrowBack,
                onClick = {
                    onBackClick()
                },
                contentDescription = stringResource(id = R.string.back),
            )
            SearchTextField(
                modifier = Modifier,
                uiState = uiState,
                onTextChange = { query ->
                    onTextChange(query)
                },
                onClear = {
                    onClear()
                }
            )
        }
        when {
            uiState.isLoading -> {
                LoadingContent()
            }
            uiState.equipments.isEmpty() && uiState.errorMessage == null -> {
                EmptyContent(
                    stringResource(id = R.string.type_search_query),
                )
            }
            uiState.errorMessage != null -> {
                EmptyContent(descriptionText = uiState.errorMessage)
            }
            else -> {
                SearchList(
                    equipments = uiState.equipments,
                    onEquipmentClick = onEquipmentClick,
                )
            }
        }
    }
}

@Composable
fun SearchList(
    equipments: List<Equipment>,
    onEquipmentClick: (equipmentId: String) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 25.dp, end = 25.dp, top = 0.dp, bottom = 40.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(
            items = equipments,
            itemContent = {
                EquipmentCard(
                    equipment = it,
                    onEquipmentClick = { equipmentId ->
                        onEquipmentClick(equipmentId)
                    },
                )
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    TravelerTheme {
        SearchScreen(
            uiState = SearchState(
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
            onEquipmentClick = {},
            onBackClick = {},
            onTextChange = {},
            onClear = {},
        )
    }
}