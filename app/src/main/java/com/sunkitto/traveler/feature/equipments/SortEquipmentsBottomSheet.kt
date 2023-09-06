package com.sunkitto.traveler.feature.equipments

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sunkitto.traveler.R
import com.sunkitto.traveler.domain.model.SortType
import com.sunkitto.traveler.ui.designSystem.TravelerButton
import com.sunkitto.traveler.ui.theme.TravelerTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortEquipmentsBottomSheet(
    onSortEquipment: (sortType: SortType) -> Unit,
    onDismissRequest: () -> Unit,
) {
    val modalBottomSheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    ModalBottomSheet(
        sheetState = modalBottomSheetState,
        shape = MaterialTheme.shapes.small,
        onDismissRequest = {
            onDismissRequest()
        },
    ) {
        SortEquipmentsBottomSheetContent(
            onSortEquipment = onSortEquipment,
            onHide = {
                scope.launch {
                    modalBottomSheetState.hide()
                }
            },
        )
    }
}

@Composable
fun SortEquipmentsBottomSheetContent(
    onSortEquipment: (sortType: SortType) -> Unit,
    onHide: () -> Unit,
) {
    Column(modifier = Modifier.padding(vertical = 20.dp)) {
        TravelerButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 25.dp),
            onClick = {
                onHide()
                onSortEquipment(SortType.LOWEST_PRICE)
            },
            text = stringResource(id = R.string.cost_ascending),
        )
        TravelerButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 25.dp, end = 25.dp, top = 20.dp, bottom = 0.dp),
            onClick = {
                onHide()
                onSortEquipment(SortType.HIGHEST_PRICE)
            },
            text = stringResource(id = R.string.cost_descending),
        )
        TravelerButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 25.dp, end = 25.dp, top = 20.dp, bottom = 0.dp),
            onClick = {
                onHide()
                onSortEquipment(SortType.LOWEST_NAME)
            },
            text = stringResource(id = R.string.name_ascending),
        )
        TravelerButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 25.dp, end = 25.dp, top = 20.dp, bottom = 0.dp),
            onClick = {
                onHide()
                onSortEquipment(SortType.HIGHEST_NAME)
            },
            text = stringResource(id = R.string.name_descending),
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
fun SortEquipmentsBottomSheetPreview() {
    TravelerTheme {
        SortEquipmentsBottomSheetContent(
            onSortEquipment = {},
            onHide = {},
        )
    }
}