package com.sunkitto.traveler.feature.equipments

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sunkitto.traveler.R
import com.sunkitto.traveler.model.Equipment
import com.sunkitto.traveler.ui.designSystem.EmptyContent
import com.sunkitto.traveler.ui.designSystem.EquipmentCard
import com.sunkitto.traveler.ui.designSystem.TravelerIconButton
import com.sunkitto.traveler.ui.theme.TravelerTheme

@Composable
fun EquipmentsScreen(
    uiState: EquipmentsState,
    categoryName: String,
    onEquipmentClick: (equipmentId: Int) -> Unit,
    onBackClick: () -> Unit,
    onSortClick: () -> Unit,
) {
    Column(Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .padding(horizontal = 25.dp, vertical = 40.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                TravelerIconButton(
                    modifier = Modifier.padding(end = 25.dp),
                    icon = Icons.Rounded.ArrowBack,
                    onClick = {
                        onBackClick()
                    },
                    contentDescription = stringResource(id = R.string.back)
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
                    onSortClick()
                },
                contentDescription = stringResource(id = R.string.sort_equipment)
            )
        }
        if(uiState.equipments == null) {
            EmptyContent(
                stringResource(id = R.string.type_search_query)
            )
        } else {
            EquipmentsList(
                equipments = uiState.equipments,
                onEquipmentClick = { equipmentId ->
                    onEquipmentClick(equipmentId)
                }
            )
        }

    }
}

@Composable
fun EquipmentsList(
    equipments: List<Equipment>,
    onEquipmentClick: (equipmentId: Int) -> Unit,
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
                    }
                )
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EquipmentsScreenPreview() {
    TravelerTheme {
        EquipmentsScreen(
            uiState = EquipmentsState(
                equipments = listOf(
                    Equipment(name = "Equipment 1"),
                    Equipment(name = "Equipment 2"),
                )
            ),
            categoryName = "Category 1",
            onEquipmentClick = {},
            onBackClick = {},
            onSortClick = {},
        )
    }
}