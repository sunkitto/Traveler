package com.sunkitto.traveler.feature.equipmentsDetailed

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sunkitto.traveler.R
import com.sunkitto.traveler.ui.designSystem.TravelerButton
import com.sunkitto.traveler.ui.designSystem.TravelerIconButton
import com.sunkitto.traveler.ui.theme.TravelerTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddToCartBottomSheet(
    state: EquipmentsDetailedState,
    onBottomSheetEvent: (EquipmentsDetailedEvent) -> Unit,
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
        AddToCartBottomSheetContent(
            state = state,
            onBottomSheetEvent = onBottomSheetEvent,
            onHide = {
                scope.launch {
                    modalBottomSheetState.hide()
                }
            },
        )
    }
}

@Composable
fun AddToCartBottomSheetContent(
    state: EquipmentsDetailedState,
    onHide: () -> Unit,
    onBottomSheetEvent: (EquipmentsDetailedEvent) -> Unit,
) {
    Column(modifier = Modifier.padding(vertical = 25.dp)) {
        Row(
            modifier = Modifier
                .padding(start = 25.dp, end = 25.dp, bottom = 25.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier,
                text = stringResource(id = R.string.quantity),
                style = MaterialTheme.typography.headlineSmall,
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                TravelerIconButton(
                    modifier = Modifier,
                    icon = Icons.Rounded.Remove,
                    contentDescription = stringResource(id = R.string.decrease_quantity),
                    onClick = {
                        onBottomSheetEvent(EquipmentsDetailedEvent.OnDecreaseEquipmentsCount)
                    },
                )
                Text(
                    modifier = Modifier.padding(start = 20.dp),
                    text = state.equipmentsCount.toString(),
                    style = MaterialTheme.typography.headlineSmall,
                )
                TravelerIconButton(
                    modifier = Modifier.padding(start = 20.dp),
                    icon = Icons.Rounded.Add,
                    contentDescription = stringResource(id = R.string.increase_quantity),
                    onClick = {
                        onBottomSheetEvent(EquipmentsDetailedEvent.OnIncreaseEquipmentsCount)
                    },
                )
            }
        }
        Row(
            modifier = Modifier
                .padding(start = 25.dp, end = 25.dp, bottom = 25.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier,
                text = stringResource(id = R.string.days),
                style = MaterialTheme.typography.headlineSmall,
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                TravelerIconButton(
                    modifier = Modifier,
                    contentDescription = stringResource(id = R.string.decrease),
                    icon = Icons.Rounded.Remove,
                    onClick = {
                        onBottomSheetEvent(EquipmentsDetailedEvent.OnDecreaseRentDays)
                    },
                )
                Text(
                    modifier = Modifier.padding(start = 20.dp),
                    text = state.rentDays.toString(),
                    style = MaterialTheme.typography.headlineSmall,
                )
                TravelerIconButton(
                    modifier = Modifier.padding(start = 20.dp),
                    contentDescription = stringResource(id = R.string.decrease),
                    icon = Icons.Rounded.Add,
                    onClick = {
                        onBottomSheetEvent(EquipmentsDetailedEvent.OnIncreaseRentDays)
                    },
                )
            }
        }
        TravelerButton(
            modifier = Modifier
                .padding(start = 25.dp, end = 25.dp)
                .fillMaxWidth(),
            onClick = {
                onHide()
                onBottomSheetEvent(EquipmentsDetailedEvent.OnAddToCart)
            },
            text = stringResource(id = R.string.add_to_cart),
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
fun AddToCartBottomSheetPreview() {
    TravelerTheme {
        Surface {
            AddToCartBottomSheetContent(
                state = EquipmentsDetailedState(),
                onBottomSheetEvent = {},
                onHide = {},
            )
        }
    }
}