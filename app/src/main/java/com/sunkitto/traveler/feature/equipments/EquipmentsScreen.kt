package com.sunkitto.traveler.feature.equipments

import android.content.res.Configuration
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
import androidx.compose.material.icons.rounded.Sort
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sunkitto.traveler.R
import com.sunkitto.traveler.domain.model.Equipment
import com.sunkitto.traveler.domain.model.SortType
import com.sunkitto.traveler.ui.designSystem.EmptyContent
import com.sunkitto.traveler.ui.designSystem.EquipmentCard
import com.sunkitto.traveler.ui.designSystem.LoadingContent
import com.sunkitto.traveler.ui.designSystem.TravelerIconButton
import com.sunkitto.traveler.ui.theme.TravelerTheme

@Composable
fun EquipmentsScreen(
    uiState: EquipmentsState,
    categoryName: String,
    onEquipmentClick: (equipmentId: String) -> Unit,
    onSort: (sortType: SortType) -> Unit,
    onBack: () -> Unit,
) {
    var showSortEquipmentsBottomSheet by remember { mutableStateOf(false) }

    if (showSortEquipmentsBottomSheet) {
        SortEquipmentsBottomSheet(
            onSortEquipment = { sortType ->
                onSort(sortType)
            },
            onDismissRequest = {
                showSortEquipmentsBottomSheet = false
            },
        )
    }

    Column(Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .padding(horizontal = 25.dp, vertical = 40.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                TravelerIconButton(
                    modifier = Modifier.padding(end = 25.dp),
                    icon = Icons.Rounded.ArrowBack,
                    onClick = {
                        onBack()
                    },
                    contentDescription = stringResource(id = R.string.back),
                )
                Text(
                    text = categoryName,
                    style = MaterialTheme.typography.headlineLarge,
                )
            }
            TravelerIconButton(
                modifier = Modifier,
                icon = Icons.Rounded.Sort,
                onClick = {
                    showSortEquipmentsBottomSheet = true
                },
                contentDescription = stringResource(id = R.string.sort_equipment),
            )
        }
        when {
            uiState.isLoading -> {
                LoadingContent()
            }
            uiState.equipments.isEmpty() && uiState.errorMessage == null -> {
                EmptyContent(
                    stringResource(id = R.string.no_equipments),
                )
            }
            uiState.errorMessage != null -> {
                EmptyContent(descriptionText = uiState.errorMessage)
            }
            else -> {
                EquipmentsList(
                    equipments = uiState.equipments,
                    onEquipmentClick = { equipmentId ->
                        onEquipmentClick(equipmentId)
                    },
                )
            }
        }
    }
}

@Composable
fun EquipmentsList(
    equipments: List<Equipment>,
    onEquipmentClick: (equipmentId: String) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 25.dp),
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
fun EquipmentsScreenPreview() {
    TravelerTheme {
        EquipmentsScreen(
            uiState = EquipmentsState(
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
                categoryName = "Category 1",
            ),
            categoryName = "Category 1",
            onEquipmentClick = {},
            onBack = {},
            onSort = {},
        )
    }
}